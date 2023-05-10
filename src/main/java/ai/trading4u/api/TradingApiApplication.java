package ai.trading4u.api;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class TradingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingApiApplication.class, args);
	}

	@PostConstruct
	public void settingStart() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
}
