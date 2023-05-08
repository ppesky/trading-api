package ai.trading4u.api.service.exchange.bybit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BybitResponse {

	private int retCode;
	
	private String retMsg;
	
	private String result;
	
	private String retExtInfo;
	
	private long time;
}
