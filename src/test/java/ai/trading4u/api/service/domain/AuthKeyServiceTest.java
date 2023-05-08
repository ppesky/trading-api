package ai.trading4u.api.service.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ai.trading4u.api.web.entity.AuthKey;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class AuthKeyServiceTest {
	
	@Autowired AuthKeyService authKeyService;
	
	/*
	 * test key : TQJRZTSQKYVVPSNQVW
	 * test secret : WGNANGPEOGUOUYYXJTMQEVDXQGLUXEWKVXEP
	 * 
	 * test authKeyStr : 3bY2KGQCJYaxoK1MC9xAZBe1wQxqvSukPqg1GC1uXaUrR13dndjM42ja77tCqd9GdvP2AhY1B3pS8rAmofKCQHZA
	 */

	@Test
	void test1() {
		String apiKey = "TQJRZTSQKYVVPSNQVW";
		String apiSec = "WGNANGPEOGUOUYYXJTMQEVDXQGLUXEWKVXEP";
		String authKeyStr = authKeyService.generateKey(new AuthKey("BYBIT", apiKey, apiSec));

		log.info(authKeyStr);

		AuthKey authKeyObj = authKeyService.resolveKey(authKeyStr);
		
		Assertions.assertEquals(apiKey, authKeyObj.getApiKey());
		Assertions.assertEquals(apiSec, authKeyObj.getApiSecret());
		
//		fail("Not yet implemented");
	}

}
