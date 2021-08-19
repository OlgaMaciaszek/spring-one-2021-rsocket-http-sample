package rsocket.routing.sample.loanservice;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Olga Maciaszek-Sharma
 */
class Verification {

	private UUID customerUuid;
	private VerificationResult verificationResult;

	Verification() {
	}

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

	void setCustomerUuid(UUID customerUuid) {
		this.customerUuid = customerUuid;
	}

	void setVerificationResult(VerificationResult verificationResult) {
		this.verificationResult = verificationResult;
	}

	@Override
	public String toString() {
		return "{" +
				"\"customerUuid\":\"" + customerUuid +
				"\", \"verificationResult\":\"" + verificationResult +
				"\"}";
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
