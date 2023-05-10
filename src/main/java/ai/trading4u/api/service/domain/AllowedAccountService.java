package ai.trading4u.api.service.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ai.trading4u.api.service.domain.entity.AllowedAccount;

@Service
public class AllowedAccountService {
	
	@Autowired AllowedAccountRepository allowedAccountRepository;
	
	@Cacheable(cacheNames = "allowed_account")
	public boolean isAllowedAccount(String allowedType, String allowedName) {
		
		AllowedAccount allowedAccount = allowedAccountRepository.findByAllowedTypeAndAllowedName(allowedType, allowedName);
		
		return allowedAccount == null;
	}

}
