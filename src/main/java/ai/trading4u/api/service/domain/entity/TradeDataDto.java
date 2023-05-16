package ai.trading4u.api.service.domain.entity;

import lombok.Getter;
import lombok.Setter;

public class TradeDataDto {

	public interface AuthKeyAndSymbol {
        String getAuthKey();
        String getOrderSymbol();
    }

	@Getter
	@Setter
	public static class TradeDataResDto {
		
		Long tradeNum;
		
		String eventName;
		
		String eventTime;
		
		String reqExchange;

		String orderSymbol;
		
		String orderMode;

		String orderName;
		
		String orderAction;
		
		String orderSize;
		
		String takePrice;
		
		int retCode;

		String retMsg;
		
		long createTime;
		
		long reqTime;
		
		long resTime;
	}

}
