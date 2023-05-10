package ai.trading4u.api.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ai.trading4u.api.service.domain.AllowedAccountService;
import ai.trading4u.api.service.domain.AuthKeyService;
import ai.trading4u.api.service.domain.TradeService;
import ai.trading4u.api.service.domain.entity.TradeData;
import ai.trading4u.api.service.domain.entity.TradeDataDto;
import ai.trading4u.api.service.exchange.ExchangeService;
import ai.trading4u.api.web.entity.AuthKey;
import ai.trading4u.api.web.entity.Crypto25TvaOrderReq;
import ai.trading4u.api.web.entity.TradingviewOrderReq;

@RestController
public class TradingviewController {
	
	enum AllowedType {AUTH_KEY, EXCHANGE_KEY}
	
	@Autowired AllowedAccountService allowedAccountService;
	@Autowired AuthKeyService authKeyService;
	@Autowired ExchangeService exchangeService;
	@Autowired TradeService tradeService;

	@PostMapping("/tv4u/webhook")
	public Map<String, Object> orderWebhook(@RequestBody TradingviewOrderReq tvOrder) {
		boolean b = allowedAccountService.isAllowedAccount(AllowedType.AUTH_KEY.name(), tvOrder.getAuthKey());
		if(!b) {
			return Map.of("result", "Your key cannot be used.");
		}
		
		exchangeService.saveRequest(tvOrder);
		return Map.of("result", "success");
	}

	@PostMapping("/tv4u/authkey/generate")
	public AuthKey generate(@RequestBody AuthKey authKey) {
		String authKeyStr = authKeyService.generateKey(authKey);
		return new AuthKey(authKey.getExchangeName(), authKey.getApiKey(), authKey.getApiSecret(), authKeyStr);
	}
	
	@GetMapping("/tv4u/list/{authKey}")
	public List<TradeDataDto.TradeDataResDto> getTradeData(@PathVariable("authKey") String authKeyStr) {
		return tradeService.findTop999ByAuthKeyOrderByTradeNumDesc(authKeyStr)
				.stream()
				.map(TradeData::fromEntity)
				.collect(Collectors.toList());
	}

	@GetMapping("/check/{type}/{key}")
	public Map<String, Object> checkKey(
			@PathVariable("type") String allowedType,
			@PathVariable("key") String key
			) {
		boolean b = allowedAccountService.isAllowedAccount(allowedType.toUpperCase(), key);
		if(!b) {
			return Map.of("result", "Your key cannot be used.");
		}
		return Map.of("result", "Your key can be used.");
	}
	
	/////// 여기 아래는 크립토25 지원 코드. ///////

	@PostMapping("/tva/webhook")
	public Map<String, Object> orderWebhookByTva(@RequestBody Crypto25TvaOrderReq tva) {
		boolean b = allowedAccountService.isAllowedAccount(AllowedType.EXCHANGE_KEY.name(), tva.getApiKey());
		if(!b) {
			return Map.of("result", "Your key cannot be used.");
		}
		
		String authKeyStr = authKeyService.getAuthKeyStr(tva.getOrderExchange().name(), tva.getApiKey(), tva.getApiSecret());
		TradingviewOrderReq tvOrder = tva.toEntity();
		tvOrder.setAuthKey(authKeyStr);
		
		exchangeService.saveRequest(tvOrder);
		return Map.of("result", "success");
	}
	
}
