package isaproject.dto;

import java.util.Set;

import isaproject.model.Address;
import isaproject.model.LoyaltyProgram;

public class CustomerDTO extends UserDTO {

	private LoyaltyProgram loyaltyProgram;
	private Integer penalties;

	public CustomerDTO() {
	}

	public CustomerDTO(Long id, String username, String password, String firstName, String lastName, String email,
			Set<RoleDTO> roles, String phoneNumber, Address address, LoyaltyProgram loyaltyProgram) {
		super(id, username, password, firstName, lastName, email, roles, phoneNumber,
				address);
		this.loyaltyProgram = loyaltyProgram;
	}

	public LoyaltyProgram getLoyaltyProgram() {
		return loyaltyProgram;
	}

	public void setLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
		this.loyaltyProgram = loyaltyProgram;
	}

	public Integer getPenalties() {
		return penalties;
	}

	public void setPenalties(Integer penalties) {
		this.penalties = penalties;
	}

}