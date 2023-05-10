package ai.trading4u.api.service.domain;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.trading4u.api.service.domain.entity.TradeData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TradeService {
	
	@Autowired TradeRepository tradeRepository;
	
	public List<TradeData> findTop999ByAuthKeyOrderByTradeNumDesc(String authKey) {
		return tradeRepository.findTop999ByAuthKeyOrderByTradeNumDesc(authKey);
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
