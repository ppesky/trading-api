package ai.withtrade.api.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradingviewOrderReq {
	
	/*
{
"order_id":"{{strategy.order.id}}",
"order_size": "{{strategy.order.contracts}}",
"name" : "L5 vol long, 23m, vol:{{volume}}",
"position_mode":"hedge",
"side": "{{strategy.order.action}}",
"time":"{{time}}",
"mode" : "tva",
"exchange" : "{{exchange}}",
"type":"future",
"ticker": "{{ticker}}",
"testnet":false,
"apikey":"NOFMKGGLKYSKGODTVQ",
"secret":"QDYXSDZDGFPNUHIRFFDOQQXMBFZHZVZGXNUD"
}


"{
""order_id"":""Take"",
""order_size"": ""33.05"",
""name"" : ""4line long, 5m, vol:0"",
""position_mode"":""hedge"",
""side"": ""sell"",
""time"":""2023-02-27T08:15:00Z"",
""mode"" : ""tva"",
""exchange"" : ""BYBIT"",
""type"":""future"",
""ticker"": ""STXUSDT.P"",
""testnet"":false,
""apikey"":""---"",
""secret"":""---""
}"
	 */
	
	public enum OrderExchange {BYBIT}
	
	public enum OrderMode {oneway, hedge}

	@JsonProperty(value = "auth_key")
	String authKey;

	@JsonProperty(value = "alert_name")
	String alertName;

	@JsonProperty(value = "order_exchange")
	OrderExchange orderExchange;

	@JsonProperty(value = "order_mode")
	OrderMode orderMode;

//	@JsonProperty(value = "position_mode")
//	String positionMode;


	@JsonProperty(value = "order_name")
	String orderName;

	@JsonProperty(value = "order_size")
	String orderSize;

	@JsonProperty(value = "order_action")
	String orderAction;

	@JsonProperty(value = "order_symbol")
	String orderSymbol;

	@JsonProperty(value = "order_time")
	String orderTime;
	
	@JsonIgnore
	AuthKey authKeyObj;
	
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
		this.authKeyObj = AuthKey.getAuthKey(authKey);
	}
}
