package ai.trading4u.api.service.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TradeServiceTest {
	
	@Autowired TradeService tradeService;

	@Rollback
	@Test
	void test() {
		
		tradeService.removeOldData();
		
//		fail("Not yet implemented");
	}

}
