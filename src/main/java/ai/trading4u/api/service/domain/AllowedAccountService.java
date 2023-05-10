package ai.trading4u.api.service.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ai.trading4u.api.service.domain.entity.AllowedAccount;

@Service
public class AllowedAccountService {
	
	@Autowired AllowedAccountRepository allowedAccountRepository;
	
	@Cacheable(cacheNames = "allowed_account", unless="#result == false")
	public boolean isAllowedAccount(String allowedType, String allowedName) {
		
		// INSERT INTO allowed_account (allowed_type, allowed_name) VALUES('AUTH_KEY', '3bY2KGQCJYaxoK1MC9xAZBe1wQxqvSukPqg1GC1uXaUrR13dndjM42ja77tCqd9GdvP2AhY1B3pS8rAmofKCQHZA');
		// INSERT INTO allowed_account (allowed_type, allowed_name) VALUES('EXCHANGE_KEY', 'TQJRZTSQKYVVPSNQVW');
		
		AllowedAccount allowedAccount = allowedAccountRepository.findByAllowedTypeAndAllowedName(allowedType, allowedName);
		
		return allowedAccount != null;
	}

}
