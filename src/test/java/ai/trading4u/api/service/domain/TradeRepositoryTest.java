package ai.trading4u.api.service.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ai.trading4u.api.service.domain.entity.TradeData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class TradeRepositoryTest {
	
	@Autowired TradeRepository tradeRepository;

	@Test
	void test() {
		TradeData data1 = tradeRepository.findById(1L).orElseThrow(IllegalArgumentException::new);
		
		log.debug(data1.toString());
		log.debug(data1.getOrderSymbolForBybit());
		
//		System.out.println(data1.toString());
//		System.out.println(data1.getOrderSymbolForBybit());
		Assertions.assertTrue("GPTUSDT".equals(data1.getOrderSymbolForBybit()));
	}

}
