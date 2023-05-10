package ai.trading4u.api.service.domain.entity;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	public static TradeDataDto.TradeDataResDto fromEntity(TradeData tradeData) {
		TradeDataDto.TradeDataResDto dto = new TradeDataDto.TradeDataResDto();
		dto.setTradeNum(tradeData.getTradeNum());
		dto.setEventName(tradeData.getEventName());
		dto.setEventTime(tradeData.getEventTime());
		dto.setReqExchange(tradeData.getReqExchange());
		dto.setOrderSymbol(tradeData.getOrderSymbol());
		dto.setOrderMode(tradeData.getOrderMode());
		dto.setOrderName(tradeData.getOrderName());
		dto.setOrderAction(tradeData.getOrderAction());
		dto.setOrderSize(tradeData.getOrderSize());
		dto.setTpPrice(tradeData.getTpPrice());
		dto.setCreateTime(tradeData.getCreateTime());
		dto.setReqTime(tradeData.getReqTime());
		dto.setResTime(tradeData.getResTime());
		if(StringUtils.hasText(tradeData.getResData())) {
			try {
				
				ObjectMapper objectMapper = new ObjectMapper();
			    TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>() {};
			    Map<String, Object> map = objectMapper.readValue(tradeData.getResData(), typeReference);
				
//				Map<String, Object> map = new ObjectMapper().readValue(tradeData.getResData(), Map.class);

	    		dto.setRetCode((Integer) map.get("retCode"));
	    		dto.setRetMsg((String) map.get("retMsg"));
	    		
	        } catch (Exception e) {
	    		dto.setRetCode(-1);
	    		dto.setRetMsg("Parsing error.");
	        }
		}
		
		return dto;
	}
}
