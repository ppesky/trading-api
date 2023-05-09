package ai.trading4u.api.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.trading4u.api.service.exchange.ExchangeService;
import ai.trading4u.api.web.entity.TradingviewOrderReq;

@RestController
public class TradingviewController {
	
	@Autowired ExchangeService exchangeService;
	
	@GetMapping("/tv/webhook")
	public Map<String, Object> orderWebhook(TradingviewOrderReq tvOrder) {
		exchangeService.saveRequest(tvOrder);
		return Map.of("result", "success");
	}

	
}
