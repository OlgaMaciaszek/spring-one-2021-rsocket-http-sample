package rsocket.routing.sample.verificationservice;

import org.springframework.stereotype.Service;

/**
 * @author Olga Maciaszek-Sharma
 */
@Service
public class VerificationService {

	Verification verify(Customer customer) {
		if (customer == null) {
			throw new IllegalArgumentException("Customer cannot be null");
		}
		if (customer.getAge() >= 18) {
			return Verification.valid(customer.getUuid());
		}
		return Verification.fraud(customer.getUuid());
	}
}
