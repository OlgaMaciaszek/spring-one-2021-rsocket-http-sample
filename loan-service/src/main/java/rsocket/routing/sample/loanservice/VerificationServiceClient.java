package rsocket.routing.sample.loanservice;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

import reactor.core.publisher.Mono;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Olga Maciaszek-Sharma
 */
@Component
public class VerificationServiceClient {

	private final WebClient webClient;
	private final DiscoveryClient discoveryClient;
	private final LoanServiceProperties properties;

	public VerificationServiceClient(WebClient webClient, DiscoveryClient discoveryClient,
			LoanServiceProperties properties) {
		this.webClient = webClient;
		this.discoveryClient = discoveryClient;
		this.properties = properties;
	}

	Mono<byte[]> verify(Customer customer) {
		List<ServiceInstance> instances = discoveryClient.getInstances("gateway");
		if (instances.size() < 1) {
			throw new IllegalArgumentException("No gateway instance found");
		}
		return buildRequest(customer, instances.get(0).getUri())
				.retrieve()
				.bodyToMono(byte[].class);
	}

	private WebClient.RequestHeadersSpec<?> buildRequest(Customer customer, URI instanceUri) {
		WebClient.RequestHeadersSpec request = webClient.post()
				.uri(UriComponentsBuilder.fromUri(instanceUri)
						.path("verify")
						.build().toUri())
				.body(Mono.just(customer.toString()
						.getBytes(StandardCharsets.UTF_8)), byte[].class);
		String instanceId = properties.getInstance();
		if (instanceId != null) {
			request.header("X-RSocket-Tags", "INSTANCE_NAME=" + instanceId);
		}
		return request;
	}
}
