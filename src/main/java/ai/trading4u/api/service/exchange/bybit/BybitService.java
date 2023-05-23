package ai.trading4u.api.service.exchange.bybit;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.trading4u.api.service.domain.ExchangeName;
import ai.trading4u.api.service.domain.TradeRepository;
import ai.trading4u.api.service.domain.entity.TradeData;
import ai.trading4u.api.web.entity.AuthKey;
import ai.trading4u.api.web.entity.TradingviewOrderReq;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BybitService {
	/*
	 * Testnet:
	 * 	https://api-testnet.bybit.com
	 * Mainnet (both endpoints are available):
	 * 	https://api.bybit.com
	 * 	https://api.bytick.com
	 */

	/*
	 * https://bybit-exchange.github.io/docs/v5/order/create-order
	 * https://github.com/bybit-exchange/api-usage-examples/blob/master/V3_demo/api_demo/unified_margin/Encryption_HMAC.java
	 */
	
	final String domain = "https://api.bybit.com";
	
	final String RECV_WINDOW = "5000";
	
	@Autowired WebClient sslWebClient;
	@Autowired ObjectMapper objMapper;
	
	@Autowired TradeRepository tradeRepository;
	
	public boolean validateReferral(AuthKey authKeyObj) {
		// 키 인증  - User > Get API Key Information (/v5/user/query-api)
		// https://bybit-exchange.github.io/docs/v5/user/apikey-info
		// inviterID	integer	Inviter ID (the UID of the account which invited this account to the platform)
		
		String timestamp = Long.toString(ZonedDateTime.now().toInstant().toEpochMilli());
		
		String signature = genGetSign(authKeyObj.getApiKey(), authKeyObj.getApiSecret(), timestamp, Map.of());
		
		String res = 
				sslWebClient
				.get()
				.uri(domain + "/v5/user/query-api")
				.accept(MediaType.APPLICATION_JSON)
				.header("X-BAPI-API-KEY", authKeyObj.getApiKey())
				.header("X-BAPI-SIGN", signature)
				.header("X-BAPI-TIMESTAMP", timestamp)
				.header("X-BAPI-RECV-WINDOW", RECV_WINDOW)
				.retrieve()
				.bodyToMono(String.class)
				.block();

		log.debug(res);
		
//		if(res.getRetCode() == 0) {
//			
////			res.getResult() 에서 초대자코드 확인.
//			
//			return true;
//		}
		return false;
		
		/*
{
    "retCode": 0,
    "retMsg": "",
    "result": {
        "id": "13770661",
        "note": "XXXXXX",
        "apiKey": "XXXXXX",
        "readOnly": 0,
        "secret": "",
        "permissions": {
            "ContractTrade": [
                "Order",
                "Position"
            ],
            "Spot": [
                "SpotTrade"
            ],
            "Wallet": [
                "AccountTransfer",
                "SubMemberTransfer"
            ],
            "Options": [
                "OptionsTrade"
            ],
            "Derivatives": [
                "DerivativesTrade"
            ],
            "CopyTrading": [
                "CopyTrading"
            ],
            "BlockTrade": [],
            "Exchange": [
                "ExchangeHistory"
            ],
            "NFT": [
                "NFTQueryProductList"
            ]
        },
        "ips": [
            "*"
        ],
        "type": 1,
        "deadlineDay": 83,
        "expiredAt": "2023-05-15T03:21:05Z",
        "createdAt": "2022-10-16T02:24:40Z",
        "unified": 0,
        "uta": 0,
        "userID": 24600000,
        "inviterID": 0,
        "vipLevel": "No VIP",
        "mktMakerLevel": "0",
        "affiliateID": 0,
        "rsaPublicKey": "",
        "isMaster": false
    },
    "retExtInfo": {},
    "time": 1676891757649
}
		 */
	}

	@Async("bybitExecutor")
	@Transactional
	public CompletableFuture<String> requestBybit(AuthKey authKeyObj, String orderSymbol) {
		// 2건만 처리. 다른 쓰레드가 기다리면 안되므로~
		List<TradeData> dataList = tradeRepository.findTop2ByReqExchangeAndAuthKeyAndOrderSymbolAndReqTimeIsNullOrderByTradeNumAsc(
				ExchangeName.BYBIT.name(), authKeyObj.getAuthKeyStr(), orderSymbol);
		
//		https://bybit-exchange.github.io/docs/v5/rate-limit#api-rate-limit-table
		String nums = "";
		for(TradeData data : dataList) {
			nums += data.getTradeNum() + " ";
			placeOrder(authKeyObj, data);
		}
		
		return CompletableFuture.completedFuture(nums);
	}
	
	@Transactional
	public void placeOrder(AuthKey authKeyObj, TradeData data) {
		log.debug(data.getTradeNum() + " start.");
		
		// 포지션 open/close - Trade > Place Order (/v5/order/create) 
		// https://bybit-exchange.github.io/docs/v5/order/create-order
		
//    	if(TradingviewOrderReq.OrderAction.buy != TradingviewOrderReq.OrderAction.valueOf(data.getOrderAction()) 
//    			|| TradingviewOrderReq.OrderAction.sell != TradingviewOrderReq.OrderAction.valueOf(data.getOrderAction())) {
//        	throw new RuntimeException("Invaild order_mode parameter.([buy, sell])");
//    	}

        TradeData data1 = tradeRepository.findById(data.getTradeNum()).orElseThrow(IllegalArgumentException::new);
		String res = "";
        try {
			String timestamp = Long.toString(ZonedDateTime.now().toInstant().toEpochMilli());
	
	        data1.resetReqTimeForCurrent();
	
	        Map<String, Object> map = new HashMap<>();
	        map.put("category", "linear");
	        map.put("symbol", data.getOrderSymbolForBybit());
	        map.put("side", StringUtils.capitalize(data.getOrderAction()));
	        map.put("orderType", "Market");
	        map.put("qty", data.getOrderSize());
	        
	    	/*
	positionIdx	false	integer	
	Used to identify positions in different position modes. Under hedge-mode, this param is required
	
	0: one-way mode
	1: hedge-mode Buy side
	2: hedge-mode Sell side
	    	 */
	        
	        boolean isClose = false;
	        if(data.getOrderName().toUpperCase().contains("TAKE")
	        		|| data.getOrderName().toUpperCase().contains("EXIT")
	        		|| data.getOrderName().toUpperCase().contains("CLOSE")
	        		|| data.getOrderName().toUpperCase().contains("STOP")
	        		|| data.getOrderName().toUpperCase().contains("XSL")
	        		|| data.getOrderName().toUpperCase().contains("XSS")
	        		) {
	        	isClose = true;
	        }
	        
	        
	        if("hedge".equals(data.getOrderMode())) {
	        	if(TradingviewOrderReq.OrderAction.buy == TradingviewOrderReq.OrderAction.valueOf(data.getOrderAction())) {
	            	if(!isClose) {
	            		map.put("positionIdx", Integer.valueOf(1));
	            	} else {
	            		map.put("positionIdx", Integer.valueOf(2));
	            	}
	        	} else if(TradingviewOrderReq.OrderAction.sell == TradingviewOrderReq.OrderAction.valueOf(data.getOrderAction())) {
	            	if(!isClose) {
	            		map.put("positionIdx", Integer.valueOf(2));
	            	} else {
	            		map.put("positionIdx", Integer.valueOf(1));
	            	}
	        	}
	        } else if("oneway".equals(data.getOrderMode())) {
	        	map.put("positionIdx", Integer.valueOf(0));
	        } else {
	        	throw new RuntimeException("Invaild order_mode parameter.([hedge, oneway])");
	        }
	        
	        if(isClose) {
	        	map.put("reduceOnly", true);
	        	map.put("closeOnTrigger", true);
	        } else {
	            if(StringUtils.hasText(data.getTakePrice())) {
	            	map.put("takeProfit", data.getTakePrice());
	            }
	        }
	
	        String signature = genPostSign(authKeyObj.getApiKey(), authKeyObj.getApiSecret(), timestamp, map);
	//        String jsonMap = JSON.toJSONString(map);
	
	        res = sslWebClient
	        		.post()
    				.uri(domain + "/v5/order/create")
    				.contentType(MediaType.APPLICATION_JSON)
    				.accept(MediaType.APPLICATION_JSON)
    				.header("X-BAPI-API-KEY", authKeyObj.getApiKey())
    				.header("X-BAPI-SIGN", signature)
//    				.header("X-BAPI-SIGN-TYPE", "2")
    				.header("X-BAPI-TIMESTAMP", timestamp)
    				.header("X-BAPI-RECV-WINDOW", RECV_WINDOW)
    				.bodyValue(map)
    				.retrieve()
    				.bodyToMono(String.class)
    				.block();
        } catch (Exception e) {
        	data1.setResponse("{\"retCode\": -1, \"retMsg\":\"" + StringUtils.truncate(e.getMessage(), 100) + "\"}");
		} finally {
			data1.setResponse(StringUtils.truncate(res, 950));
        }
	}

	
	// 아래 코드는 bybit 에서 제공하는 코드임.
	
    private String genPostSign(String apiKey, String apiSecret, String timestamp, Map<String, Object> params) {
    	try {
        	Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String paramJson = objMapper.writeValueAsString(params);
            String sb = timestamp + apiKey + RECV_WINDOW + paramJson;
            return bytesToHex(sha256_HMAC.doFinal(sb.getBytes()));
    	} catch(Exception e) {
    		log.error(e.getMessage());
    		throw new RuntimeException("post signature generate error.");
    	}
    }

	private String genGetSign(String apiKey, String apiSecret, String timestamp, Map<String, Object> params) {
    	try {
            StringBuilder sb = genQueryStr(params);
            String queryStr = timestamp + apiKey + RECV_WINDOW + sb;

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            return bytesToHex(sha256_HMAC.doFinal(queryStr.getBytes()));
    	} catch(Exception e) {
    		log.error(e.getMessage());
    		throw new RuntimeException("get signature generate error.");
    	}
    }

    /**
     * To convert bytes to hex
     * @param hash
     * @return hex string
     */
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private StringBuilder genQueryStr(Map<String, Object> map) {
    	if(map == null || map.size() == 0) {
    		return new StringBuilder();
    	}
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            String key = iter.next();
            sb.append(key)
                    .append("=")
                    .append(map.get(key))
                    .append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb;
    }
}
