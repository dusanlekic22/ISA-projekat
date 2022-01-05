package isaproject.dto;

import java.util.Set;

import isaproject.model.Address;
import isaproject.model.Role;

public class BusinessOwnerDTO {
	
	private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Role> roles;
    private String phoneNumber;
    private Address address;
	private String registrationExplanation;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getRegistrationExplanation() {
		return registrationExplanation;
	}

	public void setRegistrationExplanation(String registrationExplanation) {
		this.registrationExplanation = registrationExplanation;
	}
    
}
