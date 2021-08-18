package rsocket.routing.sample.loanservice;

import java.util.Objects;
import java.util.UUID;

import org.springframework.core.style.ToStringCreator;

/**
 * @author Olga Maciaszek-Sharma
 */
class Verification {

	private final UUID customerUuid;
	private final VerificationResult verificationResult;

	Verification(UUID customerUuid, VerificationResult verificationResult) {
		this.customerUuid = customerUuid;
		this.verificationResult = verificationResult;
	}

	static Verification fraud(UUID uuid) {
		return new Verification(uuid, VerificationResult.FRAUD);
	}

	static Verification valid(UUID uuid) {
		return new Verification(uuid, VerificationResult.VALID);
	}

	VerificationResult getVerificationResult() {
		return verificationResult;
	}

	@Override
	public String toString() {
		ToStringCreator creator = new ToStringCreator(this);
		creator.append("customerUuid", customerUuid);
		creator.append("verificationResult", verificationResult);
		return creator.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Verification)) return false;
		Verification that = (Verification) o;
		return Objects
				.equals(customerUuid, that.customerUuid) && verificationResult == that.verificationResult;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerUuid, verificationResult);
	}

	enum VerificationResult {
		FRAUD, VALID
	}
}