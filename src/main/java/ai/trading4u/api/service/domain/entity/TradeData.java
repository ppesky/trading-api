package ai.trading4u.api.service.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TradeData {
	
	public TradeData(String authKey, String eventName, String eventTime, 
			String reqExchange, 
			String orderSymbol, String orderMode, String orderAction, String orderSize, String tpPrice,
			String reqData) {
		this.authKey = authKey;
		this.eventName = eventName;
		this.eventTime = eventTime;
		this.reqExchange = reqExchange;
		this.orderSymbol = orderSymbol;
		this.orderMode = orderMode;
		this.orderAction = orderAction;
		this.orderSize = orderSize;
		this.tpPrice = tpPrice;
		this.reqData = reqData;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long tradeNum;
	
	String authKey;
	
	String eventName;
	
	String eventTime;
	
	String reqExchange;
	
	String orderSymbol;
	
	String orderMode;
	
	String orderAction;
	
	String orderSize;
	
	String tpPrice;
	
	String reqData;
	
	String resData;
	
	String createTime;
	
	String reqTime;
	
	String resTime;
	
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	
	public void setResponse(String resData, String resTime) {
		this.resData = resData;
		this.resTime = resTime;
	}

}
