package rsocket.routing.sample.verificationservice;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class VerificationServiceController {

	private final VerificationService verificationService;

	public VerificationServiceController(VerificationService verificationService) {
		this.verificationService = verificationService;
	}

	@MessageMapping("verify")
	public Mono<Verification> verify(Customer customer) {
		Verification verification = verificationService.verify(customer);
		log.info("Verification result for {}: {}", customer, verification
				.getVerificationResult());
		return Mono.just(verification);
	}

}
