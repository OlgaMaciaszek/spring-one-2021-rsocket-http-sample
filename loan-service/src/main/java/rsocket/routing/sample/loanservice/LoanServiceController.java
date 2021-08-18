package rsocket.routing.sample.loanservice;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
public class LoanServiceController {

	private final VerificationServiceClient verificationServiceClient;

	public LoanServiceController(VerificationServiceClient verificationServiceClient) {
		this.verificationServiceClient = verificationServiceClient;
	}

	@PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Verification> verify(@RequestBody Customer customer) {
		log.info("Retrieving verification result for {}", customer);
		return verificationServiceClient.verify(customer);
	}

}
