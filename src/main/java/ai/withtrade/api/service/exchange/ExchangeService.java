package ai.withtrade.api.service.exchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.withtrade.api.service.domain.TradeRepository;
import ai.withtrade.api.service.domain.entity.TradeData;
import ai.withtrade.api.web.entity.TradingviewOrderReq;

@Service
public class ExchangeService {
	
	@Autowired TradeRepository tradeRepository;
	@Autowired ObjectMapper objectMapper;
	
	public void saveRequest(TradingviewOrderReq tvOrder) {
		
		String paramJson;
		try {
			paramJson = objectMapper.writeValueAsString(tvOrder);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Json Parsing Error.");
		}
		
		TradeData data = new TradeData(tvOrder.getAuthKey(), tvOrder.getAlertName(), tvOrder.getAlertTime(), tvOrder.getOrderExchange().name(), paramJson);
		
		tradeRepository.save(data);
		
	}
	
	@Scheduled(fixedDelay = 1000, initialDelay = 3000) // fixedDelay : milliseconds 단위로, 이전 Task의 종료 시점으로부터 정의된 시간만큼 지난 후 Task를 실행한다.
	public void callBybitApi() {
		// 여기서 db 읽어서 계정별로 비동기 api 호출
		
	}

}
