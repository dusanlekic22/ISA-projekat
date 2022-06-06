package isaproject.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.CreateUserDeletionRequestDTO;
import isaproject.dto.UserDeletionRequestDTO;
import isaproject.mapper.RequestMapper;
import isaproject.model.FishingCourse;
import isaproject.model.FishingTrainer;
import isaproject.model.Mail;
import isaproject.model.RequestStatus;
import isaproject.model.Role;
import isaproject.model.User;
import isaproject.model.UserDeletionRequest;
import isaproject.model.boat.Boat;
import isaproject.model.boat.BoatOwner;
import isaproject.model.cottage.Cottage;
import isaproject.model.cottage.CottageOwner;
import isaproject.repository.FishingCourseRepository;
import isaproject.repository.FishingTrainerRepository;
import isaproject.repository.UserDeletionRequestRepository;
import isaproject.repository.UserRepository;
import isaproject.repository.boat.BoatOwnerRepository;
import isaproject.repository.boat.BoatRepository;
import isaproject.repository.cottage.CottageOwnerRepository;
import isaproject.repository.cottage.CottageRepository;
import isaproject.service.SendMailService;
import isaproject.service.UserDeletionService;

@Service
public class UserDeletionServiceImpl implements UserDeletionService {

	private UserDeletionRequestRepository requestRepository;
	private UserRepository userRepository;
	private SendMailService mailService;
	private FishingCourseRepository courseRepository;
	private FishingTrainerRepository fishingTrainerRepository;
	private CottageRepository cottageRepository;
	private CottageOwnerRepository cottageOwnerRepository;
	private BoatRepository boatRepository;
	private BoatOwnerRepository boatOwnerRepository;
	
	@Autowired
	public UserDeletionServiceImpl(UserDeletionRequestRepository requestRepository, UserRepository userRepository,
			SendMailService mailService, FishingCourseRepository courseRepository,
			FishingTrainerRepository fishingTrainerRepository, CottageRepository cottageRepository,
			CottageOwnerRepository cottageOwnerRepository, BoatRepository boatRepository,
			BoatOwnerRepository boatOwnerRepository) {
		super();
		this.requestRepository = requestRepository;
		this.userRepository = userRepository;
		this.mailService = mailService;
		this.courseRepository = courseRepository;
		this.fishingTrainerRepository = fishingTrainerRepository;
		this.cottageRepository = cottageRepository;
		this.cottageOwnerRepository = cottageOwnerRepository;
		this.boatRepository = boatRepository;
		this.boatOwnerRepository = boatOwnerRepository;
	}
	
	@Transactional
	@Override
	public UserDeletionRequest approve(UserDeletionRequestDTO userDeletionRequestDTO, String answer) {
		UserDeletionRequest request = requestRepository.getById(userDeletionRequestDTO.getId());
		if (request.getAccepted() != RequestStatus.Waiting) return request;
		request.setAccepted(RequestStatus.Accepted);
		User user = userRepository.findByEmail(request.getUserEmail());
		user.setDeleted(true);
		user.setEnabled(false);
		deleteUserServices(user);
		sendAcceptEmail(request.getUserEmail(), answer);
		return request;
	}
	

	private void deleteUserServices(User user) {
		List<Role> role = new ArrayList<Role>(user.getRoles());
		if (role.get(0).getName().equals("ROLE_FISHING_TRAINER")) {
			FishingTrainer fishingTrainer = fishingTrainerRepository.findById(user.getId()).get();
			Set<FishingCourse> courses = fishingTrainer.getFishingCourse();
			for (FishingCourse fishingCourse : courses) {
				fishingCourse.setDeleted(true);
			}
		} else if (role.get(0).getName().equals("ROLE_COTTAGE_OWNER")) {
			CottageOwner cottageOwner = cottageOwnerRepository.findById(user.getId()).get();
			Set<Cottage> cottages = cottageOwner.getCottage();
			for (Cottage cottage : cottages) {
				cottage.setDeleted(true);
			}
		} else if (role.get(0).getName().equals("BOAT_OWNER")) {
			BoatOwner boatOwner = boatOwnerRepository.findById(user.getId()).get();
			Set<Boat> boats = boatOwner.getBoat();
			for (Boat boat : boats) {
				boat.setDeleted(true);
			}
		}
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
		if (request.getAccepted() != RequestStatus.Waiting) return request;
		request.setAccepted(RequestStatus.Accepted);
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
		deletionRequest.setAccepted(RequestStatus.Waiting);
		deletionRequest.setDeletionExplanation(deletionRequestDTO.getDeletionExplanation());
		deletionRequest.setUserEmail(deletionRequestDTO.getUserEmail());
		return requestRepository.save(deletionRequest);
	}
}
