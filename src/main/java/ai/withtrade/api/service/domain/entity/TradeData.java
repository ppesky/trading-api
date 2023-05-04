package ai.withtrade.api.service.domain.entity;

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
	
	public TradeData(String authKey, String eventName, String eventTime, String reqExchange, String reqData) {
		this.authKey = authKey;
		this.eventName = eventName;
		this.eventTime = eventTime;
		this.reqExchange = reqExchange;
		this.reqData = reqData;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long tradeNum;
	
	String authKey;
	
	String eventName;
	
	String eventTime;
	
	String reqExchange;
	
	String reqData;
	
	String resData;
	
	String createTime;
	
	String tradeReqTime;
	
	String tradeResTime;

}
