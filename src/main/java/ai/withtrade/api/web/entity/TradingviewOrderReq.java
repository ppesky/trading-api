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
"apikey":"-",
"secret":"-"
}

--------

"{
""order_id"":""L1"",
""order_size"": ""2466.091"",
""name"" : ""L5 vol long, 23m, vol:7377227"",
""position_mode"":""hedge"",
""side"": ""buy"",
""time"":""2023-05-03T07:40:00Z"",
""mode"" : ""tva"",
""exchange"" : ""BYBIT"",
""type"":""future"",
""ticker"": ""GPTUSDT.P"",
""testnet"":false,
""apikey"":""-"",
""secret"":""-""
}"


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

------
"{
""order_id"":""Long"",
""order_size"": ""368"",
""name"" : ""Dual, 18m"",
""position_mode"":""oneway"",
""side"": ""buy"",
""time"":""2023-05-03T07:48:00Z"",
""mode"" : ""tva"",
""exchange"" : ""BYBIT"",
""type"":""future"",
""ticker"": ""GPTUSDT.P"",
""testnet"":false,
""apikey"":""-"",
""secret"":""-""
}"

"{
""order_id"":""Take"",
""order_size"": ""368"",
""name"" : ""Dual, 18m"",
""position_mode"":""oneway"",
""side"": ""sell"",
""time"":""2023-05-03T08:06:00Z"",
""mode"" : ""tva"",
""exchange"" : ""BYBIT"",
""type"":""future"",
""ticker"": ""GPTUSDT.P"",
""testnet"":false,
""apikey"":""-"",
""secret"":""-""
}"


"{
""order_id"":""Short"",
""order_size"": ""448"",
""name"" : ""Dual, 18m"",
""position_mode"":""oneway"",
""side"": ""sell"",
""time"":""2023-05-03T03:54:00Z"",
""mode"" : ""tva"",
""exchange"" : ""BYBIT"",
""type"":""future"",
""ticker"": ""GPTUSDT.P"",
""testnet"":false,
""apikey"":""-"",
""secret"":""-""
}"



"{
""order_id"":""STake"",
""order_size"": ""1298"",
""name"" : ""Dual, 18m"",
""position_mode"":""oneway"",
""side"": ""buy"",
""time"":""2023-05-03T04:48:00Z"",
""mode"" : ""tva"",
""exchange"" : ""BYBIT"",
""type"":""future"",
""ticker"": ""GPTUSDT.P"",
""testnet"":false,
""apikey"":""-"",
""secret"":""-""
}"

	 */
	
	public enum OrderExchange {BYBIT}
	
	public enum OrderCategory {future}	// {spot, future}
	
	public enum OrderMode {oneway, hedge}

	public enum OrderAction {buy, sell}

	@JsonProperty(value = "auth_key")
	String authKey;

	@JsonProperty(value = "alert_name")
	String alertName;

	@JsonProperty(value = "alert_time")
	String alertTime;
	
	@JsonProperty(value = "order_id")
	String orderId;

	@JsonProperty(value = "order_exchange")
	OrderExchange orderExchange;

	@JsonProperty(value = "order_category")
	OrderCategory orderCategory;

	@JsonProperty(value = "order_mode")
	OrderMode orderMode;

	@JsonProperty(value = "order_size")
	String orderSize;

	@JsonProperty(value = "order_action")
	OrderAction orderAction;

	@JsonProperty(value = "order_symbol")
	String orderSymbol;

	@JsonIgnore
	AuthKey authKeyObj;
	
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
		this.authKeyObj = AuthKey.getAuthKey(authKey);
	}
}
