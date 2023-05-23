package ai.trading4u.api.service.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ai.trading4u.api.service.domain.entity.AllowedAccount;
import ai.trading4u.api.service.domain.entity.AllowedType;
import ai.trading4u.api.service.exchange.bybit.BybitService;
import ai.trading4u.api.web.entity.AuthKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AllowedAccountService {

	@Autowired AllowedAccountRepository allowedAccountRepository;
	
	@Autowired AuthKeyService authKeyService;
	@Autowired BybitService bybitService;
	
	@Cacheable(cacheNames = "allowed_account", unless="#result == false")
	public boolean isAllowedAccount(AllowedType allowedType, String allowedName) {
		
		// INSERT INTO allowed_account (allowed_type, allowed_name) VALUES('AUTH_KEY', '3bY2KGQCJYaxoK1MC9xAZBe1wQxqvSukPqg1GC1uXaUrR13dndjM42ja77tCqd9GdvP2AhY1B3pS8rAmofKCQHZA');
		// INSERT INTO allowed_account (allowed_type, allowed_name) VALUES('EXCHANGE_KEY', 'TQJRZTSQKYVVPSNQVW');
		
		AllowedAccount allowedAccount = allowedAccountRepository.findByAllowedTypeAndAllowedName(allowedType.name(), allowedName);
		
		return allowedAccount != null;
	}

	@Cacheable(cacheNames = "allowed_account", unless="#result == false")
	public boolean isAllowedByBybitReferral(String authKey) {
		log.debug("call {} {}", "isAllowedByBybitReferral", authKey);
		AuthKey authKeyObj = authKeyService.resolveKey(authKey);
		
		int inviterID = bybitService.getInviterID(authKeyObj);
		
		AllowedAccount allowedAccount = allowedAccountRepository.findByAllowedTypeAndAllowedName(
				AllowedType.BYBIT_InviterID.name(), String.valueOf(inviterID));
		
		return allowedAccount != null;
	}

}
