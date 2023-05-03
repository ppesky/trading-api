package ai.withtrade.api.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.withtrade.api.web.entity.TradingviewOrderReq;

@RestController
public class TradingviewController {
	
	@GetMapping("/tv/webhook")
	public Object orderWebhook(TradingviewOrderReq tvOrder) {
		
		return null;
	}

	
}
