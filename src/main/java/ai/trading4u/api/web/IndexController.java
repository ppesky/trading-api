package ai.trading4u.api.web;

import java.time.Instant;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@GetMapping("/")
	public Map<String, Object> index() {
		return Map.of("timestamp", Instant.now().toEpochMilli());
	}

}
