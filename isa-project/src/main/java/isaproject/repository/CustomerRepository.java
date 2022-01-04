package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.Customer;
import isaproject.model.User;


public interface CustomerRepository extends JpaRepository<Customer, Long>{

	
	public Customer save(Customer customer);
	

    public User findByVerificationCode(String code);
}
