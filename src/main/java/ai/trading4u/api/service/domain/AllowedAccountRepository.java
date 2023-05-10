package ai.trading4u.api.service.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.trading4u.api.service.domain.entity.AllowedAccount;

public interface AllowedAccountRepository extends JpaRepository<AllowedAccount, Long> {

	AllowedAccount findByAllowedTypeAndAllowedName(String allowedType, String allowedName);
	
}
