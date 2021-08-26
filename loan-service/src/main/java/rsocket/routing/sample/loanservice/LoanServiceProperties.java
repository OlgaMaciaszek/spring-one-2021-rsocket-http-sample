package rsocket.routing.sample.loanservice;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Olga Maciaszek-Sharma
 */
@ConfigurationProperties(prefix = "rsocket.routing.sample.loanservice")
public class LoanServiceProperties {

	private String instance;

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}
}
