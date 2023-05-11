package ai.trading4u.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class TradingApiApplicationTests {

	@Test
	void contextLoads() {
		String n = StringUtils.truncate("0123456789", 8);
		String h = StringUtils.truncate("영일이삼사오육칠팔구", 8);
		
		log.debug(n + " => " + n.getBytes().length);
		log.debug(h + " => " + h.getBytes().length);
		
		Assertions.assertTrue(n.length() == 5);
		Assertions.assertTrue(h.length() == 5);
	}

}
