package ai.trading4u.api.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.trading4u.api.web.entity.TradingviewOrderReq;

@RestController
public class TradingviewController {
	
	@GetMapping("/tv/webhook")
	public Object orderWebhook(TradingviewOrderReq tvOrder) {
		
		return null;
	}

	
}
