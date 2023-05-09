package ai.trading4u.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class TradingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingApiApplication.class, args);
	}

}
