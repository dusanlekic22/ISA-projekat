package isaproject.schedul;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import isaproject.model.Customer;
import isaproject.repository.CustomerRepository;

@Component
public class PenaltyRemovalScheduler {

	private CustomerRepository customerRepository;

	@Autowired
	public PenaltyRemovalScheduler(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

//	@Scheduled(cron = "@monthly")
//	@Scheduled(cron = "0 * * * * *") // minut
	@Scheduled(cron = "0 0 0 1 * *")
	public void removePenalty() {
		Set<Customer>customers = new HashSet<Customer>(customerRepository.findAll());
		for (Customer customer : customers) {
			customer.setPenalties(0);
			customerRepository.save(customer);
		}
		System.out.println("ALO");
	}
	
}
