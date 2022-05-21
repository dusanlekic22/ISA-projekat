package isaproject.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.CustomerDTO;
import isaproject.mapper.CustomerMapper;
import isaproject.model.Customer;
import isaproject.model.Mail;
import isaproject.model.User;
import isaproject.repository.CustomerRepository;
import isaproject.repository.UserRepository;
import isaproject.service.CustomerService;
import isaproject.service.RoleService;
import isaproject.service.SendMailService;
import net.bytebuddy.utility.RandomString;

@Service
public class CustomerServiceImpl implements CustomerService {

	private UserRepository repo;
	private CustomerRepository customerRepo;
	private PasswordEncoder passwordEncoder;
	private SendMailService service;
	private RoleService roleService;

	@Autowired
	public CustomerServiceImpl(UserRepository repo, CustomerRepository customerRepo, PasswordEncoder passwordEncoder,
			SendMailService service, RoleService roleService) {
		super();
		this.repo = repo;
		this.customerRepo = customerRepo;
		this.passwordEncoder = passwordEncoder;
		this.service = service;
		this.roleService = roleService;
	}

	@Override
	@Transactional
	public CustomerDTO getCustomer(long customerId) {
		return CustomerMapper.customertoCustomerDTO(customerRepo.getById(customerId));
	}

	public void register(CustomerDTO customerDTO, String siteURL)
			throws UnsupportedEncodingException, MessagingException {
		Customer customer = CustomerMapper.customerDTOtoCustomer(customerDTO);
		customer.setRoles(roleService.findByName("ROLE_CUSTOMER"));
		customer.setLoyalityProgram("Basic");
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		customer.setVerificationCode(RandomString.make(64));
		customer.setEnabled(false);
		customer.setPoints("0");

		repo.save(customer);

		sendVerificationEmail(customer, siteURL);
	}

	public void sendVerificationEmail(Customer user, String siteURL) throws MessagingException {
		String toAddress = user.getEmail();
		String subject = "Please verify your registration";
		String content = "Dear " + user.getFirstName() + ",<br>"
				+ "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "Your company name.";

		String verifyURL = siteURL + "/auth/verify?code=" + user.getVerificationCode();

		content = content.replace("[[URL]]", verifyURL);

		Mail mail = new Mail(toAddress, subject, content);

		service.sendMailHTML(mail);
	}

	public boolean verify(String verificationCode) {
		User user = repo.findByVerificationCode(verificationCode);

		if (user == null || user.isEnabled()) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setEnabled(true);
			repo.save(user);

			return true;
		}
	}

}
