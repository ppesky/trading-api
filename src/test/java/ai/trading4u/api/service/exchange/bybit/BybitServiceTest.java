package ai.trading4u.api.service.exchange.bybit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ai.trading4u.api.web.entity.AuthKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class BybitServiceTest {
	
	@Autowired BybitService bybitService;

	/*
	 * test key : TQJRZTSQKYVVPSNQVW
	 * test secret : WGNANGPEOGUOUYYXJTMQEVDXQGLUXEWKVXEP
	 * 
	 * test authKeyStr : 3bY2KGQCJYaxoK1MC9xAZBe1wQxqvSukPqg1GC1uXaUrR13dndjM42ja77tCqd9GdvP2AhY1B3pS8rAmofKCQHZA
	 * 
	 * "userID":57290472,"inviterID":6543753,"vipLevel":"No VIP","mktMakerLevel":"0","affiliateID":0,"rsaPublicKey":"","isMaster":false
	 */

	@Test
	void test1() {
		AuthKey authKeyObj = new AuthKey("BYBIT", "TQJRZTSQKYVVPSNQVW", "WGNANGPEOGUOUYYXJTMQEVDXQGLUXEWKVXEP");
		
		Integer inviterID = bybitService.getInviterID(authKeyObj);
		
		log.info("InviterID => {}", inviterID);
		
		Assertions.assertTrue(inviterID > 0);
	}
/*
//////// 마스터 계정일 경우 결과 
{
    "retCode": 0,
    "retMsg": "",
    "result": {
        "id": "16254125",
        "note": "TV-Auto",
        "apiKey": "------------",
        "readOnly": 0,
        "secret": "",
        "permissions": {
            "ContractTrade": [
                "Order"
            ],
            "Spot": [],
            "Wallet": [
                "AccountTransfer"
            ],
            "Options": [],
            "Derivatives": [
                "DerivativesTrade"
            ],
            "CopyTrading": [],
            "BlockTrade": [],
            "Exchange": [],
            "NFT": []
        },
        "ips": [
            "13.209.179.116",
            "13.124.146.81",
            "13.125.72.119",
            "115.68.168.183",
            "43.200.135.155",
            "220.118.126.193"
        ],
        "type": 1,
        "deadlineDay": -2,
        "expiredAt": "1970-01-01T00:00:00Z",
        "createdAt": "2023-02-01T02:17:08Z",
        "unified": 0,
        "uta": 0,
        "userID": 21997689,
        "inviterID": 6543753,
        "vipLevel": "No VIP",
        "mktMakerLevel": "0",
        "affiliateID": 30465,
        "rsaPublicKey": "",
        "isMaster": true
    },
    "retExtInfo": {},
    "time": 1684819559128
}
 */

/*
//////// 서브 어카운트 결과 
{
    "retCode": 0,
    "retMsg": "",
    "result": {
        "id": "19710668",
        "note": "TestAPI",
        "apiKey": "TQJRZTSQKYVVPSNQVW",
        "readOnly": 0,
        "secret": "",
        "permissions": {
            "ContractTrade": [
                "Order"
            ],
            "Spot": [],
            "Wallet": [
                "AccountTransfer"
            ],
            "Options": [],
            "Derivatives": [],
            "CopyTrading": [],
            "BlockTrade": [],
            "Exchange": [],
            "NFT": []
        },
        "ips": [
            "*"
        ],
        "type": 1,
        "deadlineDay": 77,
        "expiredAt": "2023-08-08T10:39:48Z",
        "createdAt": "2023-05-08T10:39:48Z",
        "unified": 0,
        "uta": 0,
        "userID": 57290472,
        "inviterID": 6543753,
        "vipLevel": "No VIP",
        "mktMakerLevel": "0",
        "affiliateID": 0,
        "rsaPublicKey": "",
        "isMaster": false
    },
    "retExtInfo": {},
    "time": 1684826069641
}
 */
}
