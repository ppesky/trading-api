package ai.trading4u.api.config;

import javax.net.ssl.SSLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

@Configuration
public class AppConfig {

	@Bean
	WebClient sslWebClient(ObjectMapper objectMapper) throws SSLException {
		SslContext sslContext = SslContextBuilder
	            .forClient()
	            .trustManager(InsecureTrustManagerFactory.INSTANCE)
	            .build();

		HttpClient httpClient = HttpClient.create().secure( t -> t.sslContext(sslContext) );

		ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
				.codecs(
						config -> {
							config.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
							config.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
						}
				)
				.build();

		return WebClient.builder()
				.exchangeStrategies(exchangeStrategies)
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.build();
	}

}
