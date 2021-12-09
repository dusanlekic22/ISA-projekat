package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import isaproject.model.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long>{

	
	public Customer save(Customer customer);
	

    public Customer findByVerificationCode(String code);
}
