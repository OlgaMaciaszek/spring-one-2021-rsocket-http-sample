package rsocket.routing.sample.verificationservice;

import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class VerificationServiceController {

	private final ObjectMapper objectMapper;
	private final VerificationService verificationService;

	public VerificationServiceController(ObjectMapper objectMapper, VerificationService verificationService) {
		this.objectMapper = objectMapper;
		this.verificationService = verificationService;
	}

	@MessageMapping("verify")
	public Mono<Verification> verify(byte[] customerBytes) {
		String customerJSON = new String(customerBytes, StandardCharsets.UTF_8);
		Customer customer = null;
		try {
			customer = objectMapper.readValue(customerJSON, Customer.class);
		}
		catch (JsonProcessingException exception) {
			log.error("Customer could not be deserialised", exception);
		}
		Verification verification = verificationService.verify(customer);
		log.info("Verification result for {}: {}", customer, verification
				.getVerificationResult());
		return Mono.just(verification);
	}

}
