package ai.withtrade.api.service.exchange.bybit;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import ai.withtrade.api.web.entity.TradingviewOrderReq;

@Service
public class BybitService {
	/*
	 * Testnet:
	 * 	https://api-testnet.bybit.com
	 * Mainnet (both endpoints are available):
	 * 	https://api.bybit.com
	 * 	https://api.bytick.com
	 */
	
	final String domain = "https://api-testnet.bybit.com";
	
	final String RECV_WINDOW = "5000";
	
	@Autowired WebClient sslWebClient;
	@Autowired ObjectMapper objMapper;
	
	public void validateReferral(TradingviewOrderReq tvOrder) {
		// 키 인증  - User > Get API Key Information (/v5/user/query-api)
		// https://bybit-exchange.github.io/docs/v5/user/apikey-info
		// inviterID	integer	Inviter ID (the UID of the account which invited this account to the platform)
		
		String res = 
				sslWebClient
				.get()
				.uri(domain + "/v5/user/query-api")
				.accept(MediaType.APPLICATION_JSON)
				.header("X-BAPI-API-KEY", tvOrder.getAuthKeyObj().getApiKey())
				.header("X-BAPI-SIGN", "")
				.header("X-BAPI-SIGN-TYPE", "2")
				.header("X-BAPI-TIMESTAMP", "")
				.header("X-BAPI-RECV-WINDOW", RECV_WINDOW)
				.retrieve()
				.bodyToMono(String.class)
				.block();
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
	
	/**
	 * https://bybit-exchange.github.io/docs/v5/order/create-order
	 * https://github.com/bybit-exchange/api-usage-examples/blob/master/V3_demo/api_demo/unified_margin/Encryption_HMAC.java
	 * @param tvOrder
	 */
	public void placeOrder(TradingviewOrderReq tvOrder) {
		// 포지션 open/close - Trade > Place Order (/v5/order/create) 
		// https://bybit-exchange.github.io/docs/v5/order/create-order
		
    	if(TradingviewOrderReq.OrderAction.buy != tvOrder.getOrderAction() || TradingviewOrderReq.OrderAction.sell != tvOrder.getOrderAction()) {
        	throw new RuntimeException("Invaild order_mode parameter.([buy, sell])");
    	}
    	
		String timestamp = Long.toString(ZonedDateTime.now().toInstant().toEpochMilli());

        Map<String, Object> map = new HashMap<>();
        map.put("category", "linear");
        map.put("symbol", tvOrder.getOrderSymbol());
        map.put("side", tvOrder.getOrderAction());
        map.put("orderType", "Market");
        map.put("qty", tvOrder.getOrderSize());
        map.put("reduceOnly", "true");
        

    	/*
positionIdx	false	integer	
Used to identify positions in different position modes. Under hedge-mode, this param is required

0: one-way mode
1: hedge-mode Buy side
2: hedge-mode Sell side
    	 */
        if(TradingviewOrderReq.OrderMode.hedge == tvOrder.getOrderMode()) {
        	if(TradingviewOrderReq.OrderAction.buy == tvOrder.getOrderAction()) {
            	map.put("positionIdx", Integer.valueOf(1));
        	} else if(TradingviewOrderReq.OrderAction.sell == tvOrder.getOrderAction()) {
            	map.put("positionIdx", Integer.valueOf(2));
        	}
        } else if(TradingviewOrderReq.OrderMode.oneway == tvOrder.getOrderMode()) {
        	map.put("positionIdx", Integer.valueOf(0));
        } else {
        	throw new RuntimeException("Invaild order_mode parameter.([hedge, oneway])");
        }
        

        String signature = genPostSign(tvOrder.getAuthKeyObj().getApiKey(), tvOrder.getAuthKeyObj().getApiSecret(), timestamp, map);
//        String jsonMap = JSON.toJSONString(map);

		String res = 
				sslWebClient
				.post()
				.uri(domain + "/v5/order/create")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.header("X-BAPI-API-KEY", tvOrder.getAuthKeyObj().getApiKey())
				.header("X-BAPI-SIGN", "")
				.header("X-BAPI-SIGN-TYPE", "2")
				.header("X-BAPI-TIMESTAMP", timestamp)
				.header("X-BAPI-RECV-WINDOW", RECV_WINDOW)
				.bodyValue(map)
				.retrieve()
				.bodyToMono(String.class)
				.block();
/*
{
    "retCode": 0,
    "retMsg": "OK",
    "result": {
        "orderId": "1321003749386327552",
        "orderLinkId": "spot-test-postonly"
    },
    "retExtInfo": {},
    "time": 1672211918471
}
 */
	}

	
	
	
    private String genPostSign(String apiKey, String apiSecret, String timestamp, Map<String, Object> params) {
    	try {
        	Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String paramJson = objMapper.writeValueAsString(params);
            String sb = timestamp + apiKey + RECV_WINDOW + paramJson;
            return bytesToHex(sha256_HMAC.doFinal(sb.getBytes()));
    	} catch(Exception e) {
    		throw new RuntimeException("");
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
    		throw new RuntimeException("");
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
