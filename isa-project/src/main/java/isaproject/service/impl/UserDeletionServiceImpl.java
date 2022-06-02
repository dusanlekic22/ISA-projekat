package isaproject.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.CreateUserDeletionRequestDTO;
import isaproject.dto.UserDeletionRequestDTO;
import isaproject.mapper.RequestMapper;
import isaproject.model.Mail;
import isaproject.model.User;
import isaproject.model.UserDeletionRequest;
import isaproject.repository.UserDeletionRequestRepository;
import isaproject.repository.UserRepository;
import isaproject.service.SendMailService;
import isaproject.service.UserDeletionService;

@Service
public class UserDeletionServiceImpl implements UserDeletionService {

	private UserDeletionRequestRepository requestRepository;
	private UserRepository userRepository;
	private SendMailService mailService;
	
	@Autowired
	public UserDeletionServiceImpl(UserDeletionRequestRepository requestRepository, UserRepository userRepository,
			SendMailService mailService) {
		super();
		this.requestRepository = requestRepository;
		this.userRepository = userRepository;
		this.mailService = mailService;
	}
	
	@Transactional
	@Override
	public UserDeletionRequest approve(UserDeletionRequestDTO userDeletionRequestDTO, String answer) {
		UserDeletionRequest request = requestRepository.getById(userDeletionRequestDTO.getId());
		if (request.getAccepted() != null) return request;
		request.setAccepted(true);
		User user = userRepository.findByEmail(request.getUserEmail());
//		TODO: user.setDeleted(true);  (mozda treba logicko ...)
		userRepository.deleteById(user.getId());
		sendAcceptEmail(request.getUserEmail(), answer);
		return request;
	}
	

	private void sendAcceptEmail(String email, String answer) {
		User user = userRepository.findByEmail(email);
		String subject = "Account deletion";
		StringBuilder content = new StringBuilder("");
		content.append("Hi ").append(user.getFirstName()).append(",<br><br>")
			.append("You have been sent this mail because your account is successfully deleted.<br><br>")
			.append(answer)
			.append("<br><br>Thank you!<br>Your company name.");

		Mail mail = new Mail(email, subject, content.toString());
		mailService.sendMailHTML(mail);
	}
	
	@Transactional
	@Override
	public UserDeletionRequest decline(UserDeletionRequestDTO userDeletionRequestDTO, String answer) {
		UserDeletionRequest request = requestRepository.getById(userDeletionRequestDTO.getId());
		if (request.getAccepted() != null) return request;
		request.setAccepted(false);
		sendDeclineEmail(request.getUserEmail(), answer);
		return request;
	}
	
	private void sendDeclineEmail(String email, String answer) {
		User user = userRepository.findByEmail(email);
		String subject = "Account deletion";
		StringBuilder content = new StringBuilder("");
		content.append("Hi ").append(user.getFirstName()).append(",<br><br>")
			.append("Your account is not deleted, request is declined by admin.<br><br>")
			.append(answer)
			.append("<br><br>Thank you!<br>Your company name.");

		Mail mail = new Mail(email, subject, content.toString());
		mailService.sendMailHTML(mail);
	}

	@Override
	public List<UserDeletionRequestDTO> getAllUserDeletionRequests() {
		List<UserDeletionRequestDTO> requestDTOs = new ArrayList<UserDeletionRequestDTO>();
		for (UserDeletionRequest userDeletionRequest : requestRepository.findAll()) {
			requestDTOs.add(RequestMapper.UserDeletionRequestToDTO(userDeletionRequest));
		}
		return requestDTOs;
	}
	
	@Override
	public UserDeletionRequest create(CreateUserDeletionRequestDTO deletionRequestDTO) {
		UserDeletionRequest deletionRequest = new UserDeletionRequest();
		deletionRequest.setAccepted(null);
		deletionRequest.setDeletionExplanation(deletionRequestDTO.getDeletionExplanation());
		deletionRequest.setUserEmail(deletionRequestDTO.getUserEmail());
		return requestRepository.save(deletionRequest);
	}
}
