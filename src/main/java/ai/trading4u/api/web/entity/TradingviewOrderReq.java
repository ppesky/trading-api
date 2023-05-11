package ai.trading4u.api.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradingviewOrderReq {
	
	public enum OrderCategory {future}	// {spot, future}
	
	public enum OrderAction {buy, sell}

	@JsonProperty(value = "auth_key")
	String authKey;

	@JsonProperty(value = "alert_name")
	String alertName;

	@JsonProperty(value = "alert_time")
	String alertTime;
	
	@JsonProperty(value = "order_id")
	String orderId;

	@JsonProperty(value = "order_category")
	OrderCategory orderCategory;

	@JsonProperty(value = "order_mode")
	String orderMode;

	@JsonProperty(value = "order_symbol")
	String orderSymbol;

	@JsonProperty(value = "order_action")
	OrderAction orderAction;

	@JsonProperty(value = "order_size")
	String orderSize;

	@JsonProperty(value = "tp_price")
	String tpPrice;
	
}
