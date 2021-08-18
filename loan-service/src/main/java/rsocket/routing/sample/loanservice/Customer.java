package rsocket.routing.sample.loanservice;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.UUID;

import org.springframework.core.style.ToStringCreator;

/**
 * @author Olga Maciaszek-Sharma
 */
class Customer {

	private final UUID uuid;
	private final LocalDate dateOfBirth;
	private String name;
	private String surname;

	Customer(UUID uuid, LocalDate dateOfBirth, String name, String surname) {
		this.uuid = uuid;
		this.dateOfBirth = dateOfBirth;
		this.name = name;
		this.surname = surname;
	}

	void setName(String name) {
		this.name = name;
	}

	void setSurname(String surname) {
		this.surname = surname;
	}

	int getAge() {
		if (dateOfBirth == null) {
			throw new IllegalArgumentException("Date of birth cannot be null.");
		}
		return Period.between(dateOfBirth, LocalDate.now()).getYears();
	}

	UUID getUuid() {
		return uuid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Customer)) return false;
		Customer customer = (Customer) o;
		return Objects.equals(uuid, customer.uuid) && Objects
				.equals(name, customer.name) && Objects
				.equals(surname, customer.surname);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid, name, surname);
	}

	@Override
	public String toString() {
		ToStringCreator creator = new ToStringCreator(this);
		creator.append("uuid", uuid);
		creator.append("name", name);
		creator.append("surname", surname);
		return creator.toString();
	}
}
