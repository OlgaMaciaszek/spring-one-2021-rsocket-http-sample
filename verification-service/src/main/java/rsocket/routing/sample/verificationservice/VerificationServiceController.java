package rsocket.routing.sample.verificationservice;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class VerificationServiceController {

	private final JacksonJsonParser parser = new JacksonJsonParser();
	private final VerificationService verificationService;

	public VerificationServiceController(VerificationService verificationService) {
		this.verificationService = verificationService;
	}

	@MessageMapping("verify")
	public Mono<byte[]> verify(byte[] customerBytes) {
		Customer customer = parseCustomer(customerBytes);
		Verification verification = verificationService.verify(customer);
		log.info("Verification result for {}: {}", customer, verification
				.getVerificationResult());
		return Mono.just(verification.toString().getBytes(StandardCharsets.UTF_8));
	}

	private Customer parseCustomer(byte[] customerBytes) {
		String customerJSON = new String(customerBytes, StandardCharsets.UTF_8);
		Map<String, Object> customerMap = parser.parseMap(customerJSON);
		return new Customer(UUID
				.fromString((String) customerMap.get("uuid")),
				LocalDate.parse((String) customerMap.get("dateOfBirth")),
				(String) customerMap.get("name"), (String) customerMap.get("surname"));
	}

}
