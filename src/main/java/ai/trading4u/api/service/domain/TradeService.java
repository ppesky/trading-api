package ai.trading4u.api.service.domain;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.trading4u.api.service.domain.entity.TradeData;
import ai.trading4u.api.web.entity.TradingviewOrderReq;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TradeService {

	@Autowired ObjectMapper objectMapper;

	@Autowired TradeRepository tradeRepository;
	
	public List<TradeData> findTop999ByAuthKeyOrderByTradeNumDesc(String authKey) {
		return tradeRepository.findTop999ByAuthKeyOrderByTradeNumDesc(authKey);
	}

	public void saveRequest(ExchangeName exchangeName, TradingviewOrderReq tvOrder) {
		
		String paramJson;
		try {
			paramJson = objectMapper.writeValueAsString(tvOrder);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Json Parsing Error.");
		}
		
		TradeData data = new TradeData(
				tvOrder.getAuthKey(), 
				tvOrder.getAlertName(), 
				tvOrder.getAlertTime(), 
				exchangeName.name(), 
				tvOrder.getOrderSymbol(), 
				tvOrder.getOrderMode(),
				tvOrder.getOrderId(),
				tvOrder.getOrderAction().name(),
				tvOrder.getOrderSize(),
				tvOrder.getTakePrice(),
				paramJson);
		
		tradeRepository.save(data);
		
	}

	@Transactional
	@Scheduled(cron = "0 0 0 * * *")
	public void removeOldData() {
		String expiryCreateTime = Instant.now()
				.minus(3, ChronoUnit.DAYS)
//				.minus(12, ChronoUnit.HOURS)
				.atZone(ZoneId.of("UTC"))
				.toString();
		log.info(expiryCreateTime);
		tradeRepository.removeByCreateTimeBefore(expiryCreateTime);;
	}
}
