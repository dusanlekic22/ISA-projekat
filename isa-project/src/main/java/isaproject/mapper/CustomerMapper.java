package isaproject.mapper;

import isaproject.dto.CustomerDTO;
import isaproject.model.Customer;

public class CustomerMapper {

	public static Customer customerDTOtoCustomer(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		customer.setId(customerDTO.getId());
		customer.setUsername(customerDTO.getUsername());
		customer.setPassword(customerDTO.getPassword());
		customer.setFirstName(customerDTO.getFirstName());
		customer.setLastName(customerDTO.getLastName());
		customer.setEmail(customerDTO.getEmail());
		customer.setPhoneNumber(customerDTO.getPhoneNumber());
		customer.setAddress(customerDTO.getAddress());
		customer.setPoints(customerDTO.getPoints());
		customer.setLoyalityProgram(customerDTO.getLoyalityProgram());
		customer.setPenalties(customerDTO.getPenalties());
		return customer;
	}

	public static CustomerDTO customertoCustomerDTO(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setId(customer.getId());
		customerDTO.setUsername(customer.getUsername());
		customerDTO.setPassword(customer.getPassword());
		customerDTO.setFirstName(customer.getFirstName());
		customerDTO.setLastName(customer.getLastName());
		customerDTO.setEmail(customer.getEmail());
		customerDTO.setRoles(UserMapper.SetRoleToDTO(customer.getRoles()));
		customerDTO.setPhoneNumber(customer.getPhoneNumber());
		customerDTO.setAddress(customer.getAddress());
		customerDTO.setPoints(customer.getPoints());
		customerDTO.setLoyalityProgram(customer.getLoyalityProgram());
		customerDTO.setPenalties(customer.getPenalties());
		return customerDTO;
	}
}
