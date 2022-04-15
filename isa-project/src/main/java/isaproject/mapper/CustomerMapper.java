package isaproject.mapper;

import isaproject.dto.CustomerDTO;
import isaproject.model.Customer;

public class CustomerMapper {

	public static Customer customerDTOtoCustomer(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		customer.setUsername(customerDTO.getUsername());
		customer.setPassword(customerDTO.getPassword());
		customer.setFirstName(customerDTO.getFirstName());
		customer.setLastName(customerDTO.getLastName());
		customer.setEmail(customerDTO.getEmail());
		customer.setEnabled(customerDTO.getEnabled());
		customer.setVerificationCode(customerDTO.getVerificationCode());
		customer.setRoles(customerDTO.getRoles());
		customer.setPhoneNumber(customerDTO.getPhoneNumber());
		customer.setAddress(customerDTO.getAddress());
		customer.setLastPasswordResetDate(customerDTO.getLastPasswordResetDate());
		customer.setPoints(customerDTO.getPoints());
		customer.setLoyalityProgram(customerDTO.getLoyalityProgram());
		return customer;
	}

	public static CustomerDTO customertoCustomerDTO(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setUsername(customer.getUsername());
		customerDTO.setPassword(customer.getPassword());
		customerDTO.setFirstName(customer.getFirstName());
		customerDTO.setLastName(customer.getLastName());
		customerDTO.setEmail(customer.getEmail());
		customerDTO.setEnabled(customer.getEnabled());
		customerDTO.setVerificationCode(customer.getVerificationCode());
		customerDTO.setRoles(customer.getRoles());
		customerDTO.setPhoneNumber(customer.getPhoneNumber());
		customerDTO.setAddress(customer.getAddress());
		customerDTO.setLastPasswordResetDate(customer.getLastPasswordResetDate());
		customerDTO.setPoints(customer.getPoints());
		customerDTO.setLoyalityProgram(customer.getLoyalityProgram());
		return customerDTO;
	}
}
