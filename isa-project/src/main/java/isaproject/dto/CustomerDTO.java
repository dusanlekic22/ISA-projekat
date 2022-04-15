package isaproject.dto;

import java.sql.Timestamp;
import java.util.Set;

import isaproject.model.Address;
import isaproject.model.Role;

public class CustomerDTO extends UserDTO {

	private String points;
	private String loyalityProgram;

	public CustomerDTO() {
	}

	public CustomerDTO(Long id, String username, String password, String firstName, String lastName, String email,
			Boolean enabled, String verificationCode, Set<Role> roles, String phoneNumber, Address address,
			Timestamp lastPasswordResetDate, String points, String loyalityProgram) {
		super(id, username, password, firstName, lastName, email, enabled, verificationCode, roles, phoneNumber,
				address, lastPasswordResetDate);
		this.points = points;
		this.loyalityProgram = loyalityProgram;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getLoyalityProgram() {
		return loyalityProgram;
	}

	public void setLoyalityProgram(String loyalityProgram) {
		this.loyalityProgram = loyalityProgram;
	}

}