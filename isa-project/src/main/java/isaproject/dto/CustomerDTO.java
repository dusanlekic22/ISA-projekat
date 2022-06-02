package isaproject.dto;

import java.util.Set;

import isaproject.model.Address;

public class CustomerDTO extends UserDTO {

	private String points;
	private String loyalityProgram;
	private Integer penalties;

	public CustomerDTO() {
	}

	public CustomerDTO(Long id, String username, String password, String firstName, String lastName, String email,
			Set<RoleDTO> roles, String phoneNumber, Address address,String points, String loyalityProgram) {
		super(id, username, password, firstName, lastName, email, roles, phoneNumber,
				address);
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

	public Integer getPenalties() {
		return penalties;
	}

	public void setPenalties(Integer penalties) {
		this.penalties = penalties;
	}

}