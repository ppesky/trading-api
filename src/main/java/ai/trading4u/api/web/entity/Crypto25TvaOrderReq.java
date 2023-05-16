package ai.trading4u.api.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import ai.trading4u.api.web.entity.TradingviewOrderReq.OrderAction;
import ai.trading4u.api.web.entity.TradingviewOrderReq.OrderCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Crypto25TvaOrderReq {
	
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
	
	@JsonProperty(value = "name")
	String alertName;

	@JsonProperty(value = "time")
	String alertTime;
	
	@JsonProperty(value = "order_id")
	String orderId;

	@JsonProperty(value = "exchange")
	String orderExchange;

	@JsonProperty(value = "type")
	OrderCategory orderCategory;

	@JsonProperty(value = "position_mode")
	String orderMode;

	@JsonProperty(value = "ticker")
	String orderSymbol;

	@JsonProperty(value = "side")
	OrderAction orderAction;

	@JsonProperty(value = "order_size")
	String orderSize;

	@JsonProperty(value = "take_price")
	String takePrice;
	
	@JsonProperty(value = "apikey")
	String apiKey;

	@JsonProperty(value = "secret")
	String apiSecret;
	
	public TradingviewOrderReq toEntity() {
		TradingviewOrderReq tvOrder = new TradingviewOrderReq();
		tvOrder.setAlertName(alertName);
		tvOrder.setAlertTime(alertTime);
		tvOrder.setOrderId(orderId);
//		tvOrder.setOrderExchange(orderExchange);
		tvOrder.setOrderCategory(orderCategory);
		tvOrder.setOrderMode(orderMode);
		tvOrder.setOrderSymbol(orderSymbol);
		tvOrder.setOrderAction(orderAction);
		tvOrder.setOrderSize(orderSize);
		tvOrder.setTakePrice(takePrice);
		
		return tvOrder;
	}
	
}
