package ai.trading4u.api.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ai.trading4u.api.service.domain.AuthKeyService;
import ai.trading4u.api.service.domain.TradeService;
import ai.trading4u.api.service.domain.entity.TradeData;
import ai.trading4u.api.service.exchange.ExchangeService;
import ai.trading4u.api.web.entity.AuthKey;
import ai.trading4u.api.web.entity.Crypto25TvaOrderReq;
import ai.trading4u.api.web.entity.TradingviewOrderReq;

@RestController
public class TradingviewController {
	
	@Autowired AuthKeyService authKeyService;
	@Autowired ExchangeService exchangeService;
	@Autowired TradeService tradeService;

	@PostMapping("/tv/webhook")
	public Map<String, Object> orderWebhook(@RequestBody TradingviewOrderReq tvOrder) {
		exchangeService.saveRequest(tvOrder);
		return Map.of("result", "success");
	}

	@PostMapping("/tv/authkey/generate")
	public AuthKey generate(@RequestBody AuthKey authKey) {
		String authKeyStr = authKeyService.generateKey(authKey);
		return new AuthKey(authKey.getExchangeName(), authKey.getApiKey(), authKey.getApiSecret(), authKeyStr);
	}
	
	@GetMapping("/tv/list/{authKey}")
	public List<TradeData> getTradeData(@PathVariable("authKey") String authKeyStr) {
		return tradeService.findTop999ByAuthKeyOrderByTradeNumDesc(authKeyStr);
	}
	
	
	/////// 여기 아래는 크립토25 지원 코드. ///////

	@PostMapping("/tva/webhook")
	public Map<String, Object> orderWebhookByTva(@RequestBody Crypto25TvaOrderReq tva) {
		String authKeyStr = authKeyService.getAuthKey(tva.getOrderExchange().name(), tva.getApiKey(), tva.getApiSecret());
		TradingviewOrderReq tvOrder = tva.toEntity();
		tvOrder.setAuthKey(authKeyStr);
		
		exchangeService.saveRequest(tvOrder);
		return Map.of("result", "success");
	}
	
}
