package ai.trading4u.api.service.exchange.bybit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ai.trading4u.api.service.exchange.bybit.BybitScheduledService;

@SpringBootTest
class BybitScheduledServiceTest {

	@Autowired BybitScheduledService bybitScheduledService;
	
	@Disabled
	@Test
	void test3() {
		bybitScheduledService.scheduledCallBybitApi();
	}

}
