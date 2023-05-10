package ai.trading4u.api.service.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AllowedAccount {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long allowedNum;

	String allowedType;
	
	String allowedName;
	
}
