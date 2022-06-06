package isaproject.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.CustomerDTO;
import isaproject.dto.LoyaltySettingsDTO;
import isaproject.mapper.CustomerMapper;
import isaproject.model.Customer;
import isaproject.model.FishingQuickReservation;
import isaproject.model.FishingReservation;
import isaproject.model.LoyaltyProgram;
import isaproject.model.LoyaltyRank;
import isaproject.model.Mail;
import isaproject.model.User;
import isaproject.model.boat.BoatQuickReservation;
import isaproject.model.boat.BoatReservation;
import isaproject.model.cottage.CottageQuickReservation;
import isaproject.model.cottage.CottageReservation;
import isaproject.repository.CustomerRepository;
import isaproject.repository.UserRepository;
import isaproject.service.CustomerService;
import isaproject.service.LoyaltySettingsService;
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
	private LoyaltySettingsService loyaltySettingsService;

	@Autowired
	public CustomerServiceImpl(UserRepository repo, CustomerRepository customerRepo, PasswordEncoder passwordEncoder,
			SendMailService service, RoleService roleService, LoyaltySettingsService loyaltySettingsService) {
		super();
		this.repo = repo;
		this.customerRepo = customerRepo;
		this.passwordEncoder = passwordEncoder;
		this.service = service;
		this.roleService = roleService;
		this.loyaltySettingsService = loyaltySettingsService;
	}

	@Override
	@Transactional
	public CustomerDTO getCustomer(long customerId) {
		return CustomerMapper.customertoCustomerDTO(customerRepo.getById(customerId));
	}
	public CustomerDTO getCustomerByUsername(String username) {
		return CustomerMapper.customertoCustomerDTO(customerRepo.findByUsername(username));
	}

	public void register(CustomerDTO customerDTO, String siteURL)
			throws UnsupportedEncodingException, MessagingException {
		Customer customer = CustomerMapper.customerDTOtoCustomer(customerDTO);
		customer.setRoles(roleService.findByName("ROLE_CUSTOMER"));
		LoyaltyProgram loyaltyProgram = new LoyaltyProgram();
		loyaltyProgram.setLoyaltyRank(LoyaltyRank.Regular);
		loyaltyProgram.setPoints(0);
		customer.setLoyaltyProgram(loyaltyProgram);
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		customer.setVerificationCode(RandomString.make(64));
		customer.setEnabled(false);

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

	public void sendNewQuickReservationEmail(Customer user, String siteURL,
			CottageQuickReservation cottageQuickReservation) {
		String toAddress = user.getEmail();
		String subject = "New " + cottageQuickReservation.getCottage().getName() + " reservation available";
		String content = "Dear " + user.getFirstName() + ",<br>" + "New reservation is available in "
				+ cottageQuickReservation.getCottage().getName() + "<br>" + "from: "
				+ cottageQuickReservation.getDuration().getStartDate() + " to: "
				+ cottageQuickReservation.getDuration().getEndDate() + "<br>" + " with a discount price of: "
				+ cottageQuickReservation.getPrice() + " &#x20AC; .<br>"
				+ "Click here to reserve the appointment.<br> <h3><a href=\"[[URL]]\" target=\"_self\">RESERVE</a></h3>"
				+ "Thank you,<br>" + "Your company name.";

		String reserveURL = siteURL + "/cottageQuickReservation/appoint/" + cottageQuickReservation.getId() + "/user/"
				+ user.getId();
		content = content.replace("[[URL]]", reserveURL);
		Mail mail = new Mail(toAddress, subject, content);
		service.sendMailHTML(mail);
	}

	public void sendReservationConfirmationEmail(String siteURL, CottageReservation cottageReservation)
			throws MessagingException {
		Customer user = cottageReservation.getCustomer();
		String toAddress = user.getEmail();
		String subject = "New " + cottageReservation.getCottage().getName() + " reservation available";
		String content = "Dear " + user.getFirstName() + ",<br>" + "Your reservation in "
				+ cottageReservation.getCottage().getName() + "<br>" + "from: "
				+ cottageReservation.getDuration().getStartDate() + " to: "
				+ cottageReservation.getDuration().getEndDate() + "<br>" + " with a price of: "
				+ cottageReservation.getPrice() + " &#x20AC; needs confirmation.<br>"
				+ "Click here to confirm the appointment.<br> <h3><a href=\"[[URL]]\" target=\"_self\">CONFIRM</a></h3>"
				+ "Thank you,<br>" + "Your company name.";

		String reserveURL = siteURL + "/cottageReservation/confirm/" + cottageReservation.getId();
		content = content.replace("[[URL]]", reserveURL);
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

	@Override
	public void sendNewQuickReservationEmail(Customer user, String siteURL, BoatQuickReservation boatQuickReservation)
			throws UnsupportedEncodingException, MessagingException {
		String toAddress = user.getEmail();
		String subject = "New " + boatQuickReservation.getBoat().getName() + " reservation available";
		String content = "Dear " + user.getFirstName() + ",<br>" + "New reservation is available in "
				+ boatQuickReservation.getBoat().getName() + "<br>" + "from: "
				+ boatQuickReservation.getDuration().getStartDate() + " to: "
				+ boatQuickReservation.getDuration().getEndDate() + "<br>" + " with a discount price of: "
				+ boatQuickReservation.getPrice() + " &#x20AC; .<br>"
				+ "Click here to reserve the appointment.<br> <h3><a href=\"[[URL]]\" target=\"_self\">RESERVE</a></h3>"
				+ "Thank you,<br>" + "Your company name.";

		String reserveURL = siteURL + "/cottageQuickReservation/appoint/" + boatQuickReservation.getId() + "/user/"
				+ user.getId();
		content = content.replace("[[URL]]", reserveURL);
		Mail mail = new Mail(toAddress, subject, content);
		service.sendMailHTML(mail);
	}

	@Override
	public void sendReservationConfirmationEmail(String siteURL, BoatReservation boatReservation)
			throws UnsupportedEncodingException, MessagingException {
		Customer user = boatReservation.getCustomer();
		String toAddress = user.getEmail();
		String subject = "New " + boatReservation.getBoat().getName() + " reservation available";
		String content = "Dear " + user.getFirstName() + ",<br>" + "Your reservation in "
				+ boatReservation.getBoat().getName() + "<br>" + "from: " + boatReservation.getDuration().getStartDate()
				+ " to: " + boatReservation.getDuration().getEndDate() + "<br>" + " with a price of: "
				+ boatReservation.getPrice() + "&#x20AC; needs confirmation.<br>"
				+ "Click here to confirm the appointment.<br> <h3><a href=\"[[URL]]\" target=\"_self\">CONFIRM</a></h3>"
				+ "Thank you,<br>" + "Your company name.";

		String reserveURL = siteURL + "/cottageReservation/confirm/" + boatReservation.getId();
		content = content.replace("[[URL]]", reserveURL);
		Mail mail = new Mail(toAddress, subject, content);
		service.sendMailHTML(mail);
	}

	@Override
	public void sendNewQuickReservationEmail(Customer customer, String siteUrl,
			FishingQuickReservation fishingQuickReservationReturn) {
		String toAddress = customer.getEmail();
		String subject = "New " + fishingQuickReservationReturn.getFishingCourse().getName() + " reservation available";
		String content = "Dear " + customer.getFirstName() + ",<br>" + "New reservation is available in "
				+ fishingQuickReservationReturn.getFishingCourse().getName() + "<br>" + "from: "
				+ fishingQuickReservationReturn.getDuration().getStartDate() + " to: "
				+ fishingQuickReservationReturn.getDuration().getEndDate() + "<br>" + " with a discount price of: "
				+ fishingQuickReservationReturn.getPrice() + "	&#x20AC; .<br>"
				+ "Click here to reserve the appointment.<br> <h3><a href=\"[[URL]]\" target=\"_self\">RESERVE</a></h3>"
				+ "Thank you,<br>" + "Your company name.";

		String reserveURL = siteUrl + "/fishingQuickReservation/appoint/" + fishingQuickReservationReturn.getId()
				+ "/user/" + customer.getId();
		content = content.replace("[[URL]]", reserveURL);
		Mail mail = new Mail(toAddress, subject, content);
		service.sendMailHTML(mail);
	}

	@Override
	public void sendReservationConfirmationEmail(String siteUrl, FishingReservation fishingReservationReturn) {
		Customer user = fishingReservationReturn.getCustomer();
		String toAddress = user.getEmail();
		String subject = "New " + fishingReservationReturn.getFishingCourse().getName() + " reservation available";
		String content = "Dear " + user.getFirstName() + ",<br>" + "Your reservation in "
				+ fishingReservationReturn.getFishingCourse().getName() + "<br>" + "from: "
				+ fishingReservationReturn.getDuration().getStartDate() + " to: "
				+ fishingReservationReturn.getDuration().getEndDate() + "<br>" + " with a price of: "
				+ fishingReservationReturn.getPrice() + "&euro needs confirmation.<br>"
				+ "Click here to confirm the appointment.<br> <h3><a href=\"[[URL]]\" target=\"_self\">CONFIRM</a></h3>"
				+ "Thank you,<br>" + "Your company name.";

		String reserveURL = siteUrl + "/fishingReservation/confirm/" + fishingReservationReturn.getId();
		content = content.replace("[[URL]]", reserveURL);
		Mail mail = new Mail(toAddress, subject, content);
		service.sendMailHTML(mail);
	}

	@Override
	public void promoteLoyaltyCustomer(Customer customer) {
		LoyaltySettingsDTO loyaltySettings = loyaltySettingsService.getLoyaltySettings();
		LoyaltyProgram loyaltyProgram = customer.getLoyaltyProgram();
		loyaltyProgram.setPoints(loyaltyProgram.getPoints() + loyaltySettings.getCustomerScore());
		if (loyaltyProgram.getPoints() > loyaltySettings.getMinScoreRegular()) {
			loyaltyProgram.setLoyaltyRank(LoyaltyRank.Regular);
		}
		if (loyaltyProgram.getPoints() > loyaltySettings.getMinScoreSilver()) {
			loyaltyProgram.setLoyaltyRank(LoyaltyRank.Silver);
		}
		if (loyaltyProgram.getPoints() > loyaltySettings.getMinScoreGold()) {
			loyaltyProgram.setLoyaltyRank(LoyaltyRank.Gold);
		}
		customer.setLoyaltyProgram(loyaltyProgram);
		customerRepo.save(customer);
	}
}
