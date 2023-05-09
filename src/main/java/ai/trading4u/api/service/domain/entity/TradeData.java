package ai.trading4u.api.service.domain.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TradeData {
	
	public TradeData(String authKey, String eventName, String eventTime, 
			String reqExchange, 
			String orderSymbol, String orderMode, String orderName, String orderAction, String orderSize, String tpPrice,
			String reqData) {
		this.authKey = authKey;
		this.eventName = eventName;
		this.eventTime = eventTime;
		this.reqExchange = reqExchange;
		this.orderSymbol = orderSymbol;
		this.orderMode = orderMode;
		this.orderName = orderName;
		this.orderAction = orderAction;
		this.orderSize = orderSize;
		this.tpPrice = tpPrice;
		this.reqData = reqData;
		this.createTime = Instant.now().atZone(ZoneId.of("UTC")).toString();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tradeNum;
	
	String authKey;
	
	String eventName;
	
	String eventTime;
	
	String reqExchange;

	String orderSymbol;
	
	String orderMode;

	String orderName;
	
	String orderAction;
	
	String orderSize;
	
	String tpPrice;
	
	String reqData;
	
	String resData;
	
	String createTime;
	
	String reqTime;
	
	String resTime;
	
	public void resetReqTimeForCurrent() {
		this.reqTime = Instant.now().atZone(ZoneId.of("UTC")).toString();
	}
	
	public void setResponse(String resData) {
		this.resData = resData;
		this.resTime = Instant.now().atZone(ZoneId.of("UTC")).toString();
	}
	
	public String getOrderSymbolForBybit() {
		if(orderSymbol != null) {
			return orderSymbol.split("\\.", 2) [0];
		}
		return orderSymbol;
	}

}
