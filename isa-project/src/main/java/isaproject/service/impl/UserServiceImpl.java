package isaproject.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.BusinessOwnerRegistrationRequestDTO;
import isaproject.mapper.RequestMapper;
import isaproject.model.BusinessOwnerRegistrationRequest;
import isaproject.model.Mail;
import isaproject.model.User;
import isaproject.repository.BusinessOwnerRegistrationRequestRepository;
import isaproject.repository.UserRepository;
import isaproject.service.SendMailService;
import isaproject.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private BusinessOwnerRegistrationRequestRepository requestRepository;
	private SendMailService mailService;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository,
			BusinessOwnerRegistrationRequestRepository requestRepository,
			SendMailService mailService) {
		super();
		this.userRepository = userRepository;
		this.requestRepository = requestRepository;
		this.mailService = mailService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		return user;
	}

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	@Transactional
	@Override
	public BusinessOwnerRegistrationRequest activateUser(BusinessOwnerRegistrationRequestDTO registrationRequestDTO) {
		BusinessOwnerRegistrationRequest request = requestRepository.getById(registrationRequestDTO.getId());
		if (request.getAccepted() != null) return request;
		request.setAccepted(true);
		User user = userRepository.findByEmail(request.getUserEmail());
		user.setEnabled(true);
		sendAcceptEmail(request.getUserEmail());
		return request;
	}
	
	private void sendAcceptEmail(String email) {
		User user = userRepository.findByEmail(email);
		String subject = "Your registration for the business owner";
		StringBuilder content = new StringBuilder("");
		content.append("Hi ").append(user.getFirstName()).append(",<br><br>")
			.append("You have been sent this mail because you are registred as a business owner position at our site.<br><br>")
			.append("<br><br>Thank you!<br>Your company name.");

		Mail mail = new Mail(email, subject, content.toString());
		mailService.sendMailHTML(mail);
	}
	
	@Transactional
	@Override
	public BusinessOwnerRegistrationRequest declineUser(BusinessOwnerRegistrationRequestDTO registrationRequestDTO) {
		BusinessOwnerRegistrationRequest request = requestRepository.getById(registrationRequestDTO.getId());
		if (request.getAccepted() != null) return request;
		request.setAccepted(false);
		request.setDeclineReason(registrationRequestDTO.getDeclineReason());
		sendDeclineEmail(request.getUserEmail(), request.getDeclineReason());
		return request;
	}
	
	private void sendDeclineEmail(String email, String declineReason) {
		User user = userRepository.findByEmail(email);
		String subject = "Your registration for the business owner";
		StringBuilder content = new StringBuilder("");
		content.append("Hi ").append(user.getFirstName()).append(",<br><br>")
			.append("Thank you so much for your applying a registration a business owner position. We appreciate you taking the time to visit our web site.<br><br>")
			.append(declineReason)
			.append("<br><br>Thank you!<br>Your company name.");

		Mail mail = new Mail(email, subject, content.toString());
		mailService.sendMailHTML(mail);
	}

	@Override
	public List<BusinessOwnerRegistrationRequestDTO> getAllRegistrationRequests() {
		List<BusinessOwnerRegistrationRequestDTO> requestDTOs = new ArrayList<BusinessOwnerRegistrationRequestDTO>();
		for (BusinessOwnerRegistrationRequest businessOwnerRegistrationRequest : requestRepository.findAll()) {
			requestDTOs.add(RequestMapper.BusinessOwnerRegistrationRequesToDTO(businessOwnerRegistrationRequest));
		}
		return requestDTOs;
	}
	
}
