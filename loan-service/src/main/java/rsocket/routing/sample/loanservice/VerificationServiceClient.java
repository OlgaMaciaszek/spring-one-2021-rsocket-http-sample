package rsocket.routing.sample.loanservice;

import java.net.URI;

import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Olga Maciaszek-Sharma
 */
@Component
public class VerificationServiceClient {

	private final WebClient webClient;

	public VerificationServiceClient(WebClient webClient) {
		this.webClient = webClient;
	}

	Mono<Verification> verify(Customer customer) {
		return webClient.post()
				.uri(URI.create("http://localhost:9080/verification-service/verify"))
				.body(Mono.just(customer), Customer.class)
				.retrieve()
				.bodyToMono(Verification.class);
	}
}
