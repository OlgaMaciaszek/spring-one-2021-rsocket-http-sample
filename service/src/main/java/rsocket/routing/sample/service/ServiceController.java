package rsocket.routing.sample.service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ServiceController {

	@MessageMapping("service-rr")
	public Mono<String> rr(String message) {
		logMessage(message);
		return Mono.just(reply(message));
	}

	private String reply(String message) {
		return message;
	}

	@MessageMapping("service-rc")
	public Flux<String> rc(Flux<String> messages) {
		return messages
				.doOnNext(this::logMessage)
				.map(this::reply);
	}

	@MessageMapping("service-rs")
	public Flux<String> rs(String message) {
		logMessage(message);
		return Flux.just(reply(message));
	}

	@MessageMapping("service-ff")
	public Mono<Void> ff(String message) {
		logMessage(message);
		return Mono.empty();
	}


	private void logMessage(String message) {
		log.info("Received message: " + message + " from HTTP client.");
	}

}
