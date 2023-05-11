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
		String n = StringUtils.truncate("0123456789", 5);
		String h = StringUtils.truncate("영일이삼사오육칠팔구", 5);

		log.debug(n + " => " + n.length());
		log.debug(h + " => " + h.length());
		
		log.debug(n + " bytes => " + n.getBytes().length);
		log.debug(h + " bytes => " + h.getBytes().length);
		
		Assertions.assertTrue(n.length() == 20);
		Assertions.assertTrue(h.length() == 20);
	}

}
