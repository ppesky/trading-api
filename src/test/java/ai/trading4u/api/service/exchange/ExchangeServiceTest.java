package ai.trading4u.api.service.exchange;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.trading4u.api.web.entity.TradingviewOrderReq;

@SpringBootTest
class ExchangeServiceTest {

	/*
	 * test key : TQJRZTSQKYVVPSNQVW
	 * test secret : WGNANGPEOGUOUYYXJTMQEVDXQGLUXEWKVXEP
	 * 
	 * test authKeyStr : 3bY2KGQCJYaxoK1MC9xAZBe1wQxqvSukPqg1GC1uXaUrR13dndjM42ja77tCqd9GdvP2AhY1B3pS8rAmofKCQHZA
	 */

	@Autowired ObjectMapper objectMapper;
	
	@Autowired ExchangeService exchangeService;
	
	String onewayLongBuy = """
			{
				"auth_key" : "3bY2KGQCJYaxoK1MC9xAZBe1wQxqvSukPqg1GC1uXaUrR13dndjM42ja77tCqd9GdvP2AhY1B3pS8rAmofKCQHZA",
				"alert_name" : "test1",
				"alert_time" : "2023-05-03T08:06:00Z",
				"order_id" : "Long",
				"order_exchange" : "BYBIT",
				"order_category" : "future",
				"order_mode" : "oneway",
				"order_symbol" : "IDUSDT.P",
				"order_action" : "buy",
				"order_size" : "1"
				,"tp_price" : "0.5"
			}
			""";

	String onewayLongClose = """
			{
				"auth_key" : "3bY2KGQCJYaxoK1MC9xAZBe1wQxqvSukPqg1GC1uXaUrR13dndjM42ja77tCqd9GdvP2AhY1B3pS8rAmofKCQHZA",
				"alert_name" : "test1",
				"alert_time" : "2023-05-03T08:06:00Z",
				"order_id" : "LongTake",
				"order_exchange" : "BYBIT",
				"order_category" : "future",
				"order_mode" : "oneway",
				"order_symbol" : "IDUSDT.P",
				"order_action" : "sell",
				"order_size" : "1"
			}
			""";

	String onewayShortSell = """
			{
				"auth_key" : "3bY2KGQCJYaxoK1MC9xAZBe1wQxqvSukPqg1GC1uXaUrR13dndjM42ja77tCqd9GdvP2AhY1B3pS8rAmofKCQHZA",
				"alert_name" : "test1",
				"alert_time" : "2023-05-03T08:06:00Z",
				"order_id" : "Short",
				"order_exchange" : "BYBIT",
				"order_category" : "future",
				"order_mode" : "oneway",
				"order_symbol" : "IDUSDT.P",
				"order_action" : "sell",
				"order_size" : "1"
				,"tp_price" : "0.4"
			}
			""";

	String onewayShortClose = """
			{
				"auth_key" : "3bY2KGQCJYaxoK1MC9xAZBe1wQxqvSukPqg1GC1uXaUrR13dndjM42ja77tCqd9GdvP2AhY1B3pS8rAmofKCQHZA",
				"alert_name" : "test1",
				"alert_time" : "2023-05-03T08:06:00Z",
				"order_id" : "ShortTake",
				"order_exchange" : "BYBIT",
				"order_category" : "future",
				"order_mode" : "oneway",
				"order_symbol" : "IDUSDT.P",
				"order_action" : "buy",
				"order_size" : "1"
			}
			""";

//	,"tp_price" : ""
	
	@Disabled
	@Test
	void test1() throws JsonMappingException, JsonProcessingException {
		
		TradingviewOrderReq tvOrderLongBuy = objectMapper.readValue(onewayLongBuy, TradingviewOrderReq.class);
		TradingviewOrderReq tvOrderLongClose = objectMapper.readValue(onewayLongClose, TradingviewOrderReq.class);
		TradingviewOrderReq tvOrderShortSell = objectMapper.readValue(onewayShortSell, TradingviewOrderReq.class);
		TradingviewOrderReq tvOrderShortClose = objectMapper.readValue(onewayShortClose, TradingviewOrderReq.class);

		for(int i=0; i<1; i++) {
			exchangeService.saveRequest(tvOrderLongBuy);
			exchangeService.saveRequest(tvOrderLongClose);
			exchangeService.saveRequest(tvOrderShortSell);
			exchangeService.saveRequest(tvOrderShortClose);
		}
	}
	
	
	
	String hedgeLongBuy = """
			{
				"auth_key" : "3bY2KGQCJYaxoK1MC9xAZBe1wQxqvSukPqg1GC1uXaUrR13dndjM42ja77tCqd9GdvP2AhY1B3pS8rAmofKCQHZA",
				"alert_name" : "test1",
				"alert_time" : "2023-05-03T08:06:00Z",
				"order_id" : "Long",
				"order_exchange" : "BYBIT",
				"order_category" : "future",
				"order_mode" : "hedge",
				"order_symbol" : "1000PEPEUSDT.P",
				"order_action" : "buy",
				"order_size" : "101"
				,"tp_price" : "1;0.002"
			}
			""";

	String hedgeLongClose = """
			{
				"auth_key" : "3bY2KGQCJYaxoK1MC9xAZBe1wQxqvSukPqg1GC1uXaUrR13dndjM42ja77tCqd9GdvP2AhY1B3pS8rAmofKCQHZA",
				"alert_name" : "test1",
				"alert_time" : "2023-05-03T08:06:00Z",
				"order_id" : "LongTake",
				"order_exchange" : "BYBIT",
				"order_category" : "future",
				"order_mode" : "hedge",
				"order_symbol" : "1000PEPEUSDT.P",
				"order_action" : "sell",
				"order_size" : "101"
			}
			""";

	String hedgeShortSell = """
			{
				"auth_key" : "3bY2KGQCJYaxoK1MC9xAZBe1wQxqvSukPqg1GC1uXaUrR13dndjM42ja77tCqd9GdvP2AhY1B3pS8rAmofKCQHZA",
				"alert_name" : "test1",
				"alert_time" : "2023-05-03T08:06:00Z",
				"order_id" : "Short",
				"order_exchange" : "BYBIT",
				"order_category" : "future",
				"order_mode" : "hedge",
				"order_symbol" : "1000PEPEUSDT.P",
				"order_action" : "sell",
				"order_size" : "101"
				,"tp_price" : "1;0.001"
			}
			""";

	String hedgeShortClose = """
			{
				"auth_key" : "3bY2KGQCJYaxoK1MC9xAZBe1wQxqvSukPqg1GC1uXaUrR13dndjM42ja77tCqd9GdvP2AhY1B3pS8rAmofKCQHZA",
				"alert_name" : "test1",
				"alert_time" : "2023-05-03T08:06:00Z",
				"order_id" : "ShortTake",
				"order_exchange" : "BYBIT",
				"order_category" : "future",
				"order_mode" : "hedge",
				"order_symbol" : "1000PEPEUSDT.P",
				"order_action" : "buy",
				"order_size" : "101"
			}
			""";


	@Disabled
	@Test
	void test2() throws JsonMappingException, JsonProcessingException {
		
		TradingviewOrderReq tvOrderLongBuy = objectMapper.readValue(hedgeLongBuy, TradingviewOrderReq.class);
		TradingviewOrderReq tvOrderLongClose = objectMapper.readValue(hedgeLongClose, TradingviewOrderReq.class);
		TradingviewOrderReq tvOrderShortSell = objectMapper.readValue(hedgeShortSell, TradingviewOrderReq.class);
		TradingviewOrderReq tvOrderShortClose = objectMapper.readValue(hedgeShortClose, TradingviewOrderReq.class);

		for(int i=0; i<1; i++) {
			exchangeService.saveRequest(tvOrderLongBuy);
			exchangeService.saveRequest(tvOrderShortSell);
			exchangeService.saveRequest(tvOrderLongClose);
			exchangeService.saveRequest(tvOrderShortClose);

//			tvOrderLongBuy.setTpPrice("");
//			exchangeService.saveRequest(tvOrderLongBuy);
//			exchangeService.saveRequest(tvOrderLongBuy);
//			tvOrderShortSell.setTpPrice("");
//			exchangeService.saveRequest(tvOrderShortSell);
//			exchangeService.saveRequest(tvOrderShortSell);
		}
		
	}

	@Disabled
	@Test
	void test3() {
		exchangeService.scheduledCallBybitApi();
	}

}
