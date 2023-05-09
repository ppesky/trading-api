package ai.trading4u.api.service.domain.entity;

public interface TradeDataDto {

	interface AuthKeyAndSymbol {
        String getAuthKey();
        String getOrderSymbol();
    }
	

//	@Getter
//	@NoArgsConstructor(access = AccessLevel.PROTECTED)
//	public static class AuthKeyAndSymbol {
//		String authKey;
//		String orderSymbol;
//	}

}
