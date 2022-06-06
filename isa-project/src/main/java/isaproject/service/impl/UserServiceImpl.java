package isaproject.service.impl;

import java.security.InvalidParameterException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.BusinessOwnerRegistrationRequestDTO;
import isaproject.dto.RoleDTO;
import isaproject.dto.UserDTO;
import isaproject.mapper.RequestMapper;
import isaproject.mapper.UserMapper;
import isaproject.model.Address;
import isaproject.model.BusinessOwnerRegistrationRequest;
import isaproject.model.FishingCourse;
import isaproject.model.FishingTrainer;
import isaproject.model.Mail;
import isaproject.model.RequestStatus;
import isaproject.model.Role;
import isaproject.model.User;
import isaproject.model.boat.Boat;
import isaproject.model.boat.BoatOwner;
import isaproject.model.cottage.Cottage;
import isaproject.model.cottage.CottageOwner;
import isaproject.repository.BusinessOwnerRegistrationRequestRepository;
import isaproject.repository.FishingCourseRepository;
import isaproject.repository.FishingTrainerRepository;
import isaproject.repository.UserRepository;
import isaproject.repository.boat.BoatOwnerRepository;
import isaproject.repository.boat.BoatRepository;
import isaproject.repository.cottage.CottageOwnerRepository;
import isaproject.repository.cottage.CottageRepository;
import isaproject.service.SendMailService;
import isaproject.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private BusinessOwnerRegistrationRequestRepository requestRepository;
	private SendMailService mailService;
	private PasswordEncoder passwordEncoder;
	private FishingCourseRepository courseRepository;
	private FishingTrainerRepository fishingTrainerRepository;
	private CottageRepository cottageRepository;
	private CottageOwnerRepository cottageOwnerRepository;
	private BoatRepository boatRepository;
	private BoatOwnerRepository boatOwnerRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, BusinessOwnerRegistrationRequestRepository requestRepository,
			SendMailService mailService, PasswordEncoder passwordEncoder, FishingCourseRepository courseRepository,
			FishingTrainerRepository fishingTrainerRepository, CottageRepository cottageRepository,
			CottageOwnerRepository cottageOwnerRepository, BoatRepository boatRepository,
			BoatOwnerRepository boatOwnerRepository) {
		super();
		this.userRepository = userRepository;
		this.requestRepository = requestRepository;
		this.mailService = mailService;
		this.passwordEncoder = passwordEncoder;
		this.courseRepository = courseRepository;
		this.fishingTrainerRepository = fishingTrainerRepository;
		this.cottageRepository = cottageRepository;
		this.cottageOwnerRepository = cottageOwnerRepository;
		this.boatRepository = boatRepository;
		this.boatOwnerRepository = boatOwnerRepository;
	}
	
	@Override
	public User findById(Long userId) {
		return userRepository.findById(userId).get();
	}

	@Override
	@Transactional
	public UserDTO updateUser(Long userId, UserDTO userDTO,Principal loggedInUser) {
		User oldUserInfo = this.userRepository.getById(userId);
		if(!loggedInUser.getName().equals(oldUserInfo.getUsername())) {
			throw new InvalidParameterException();
		}
		
		oldUserInfo.setFirstName(userDTO.getFirstName());
		oldUserInfo.setLastName(userDTO.getLastName());
		oldUserInfo.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		oldUserInfo.setPhoneNumber(userDTO.getPhoneNumber());
		Address userAddress = oldUserInfo.getAddress();
		if (userAddress ==null) {
			userAddress = new Address();
		}
		Address newAddress = userDTO.getAddress();
		if (newAddress != null) {
		userAddress.setStreet(newAddress.getStreet());
		userAddress.setCity(newAddress.getCity());
		userAddress.setCountry(newAddress.getCountry());
		userAddress.setLatitude(newAddress.getLatitude());
		userAddress.setlongitude(newAddress.getlongitude());
		}
		oldUserInfo.setAddress(userAddress);
		this.userRepository.save(oldUserInfo);
		return UserMapper.UserToDTO(oldUserInfo);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
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
		if (request.getAccepted() != RequestStatus.Waiting)
			return request;
		request.setAccepted(RequestStatus.Accepted);
		User user = userRepository.findByEmail(request.getUserEmail());
		user.setEnabled(true);
		sendAcceptEmail(request.getUserEmail());
		return request;
	}

	private void sendAcceptEmail(String email) {
		User user = userRepository.findByEmail(email);
		String subject = "Your registration for the business owner";
		StringBuilder content = new StringBuilder("");
		content.append("Hi ").append(user.getFirstName()).append(",<br><br>").append(
				"You have been sent this mail because you are registred as a business owner position at our site.<br><br>")
				.append("<br><br>Thank you!<br>Your company name.");

		Mail mail = new Mail(email, subject, content.toString());
		mailService.sendMailHTML(mail);
	}

	@Transactional
	@Override
	public BusinessOwnerRegistrationRequest declineUser(BusinessOwnerRegistrationRequestDTO registrationRequestDTO) {
		BusinessOwnerRegistrationRequest request = requestRepository.getById(registrationRequestDTO.getId());
		if (request.getAccepted() != RequestStatus.Waiting)
			return request;
		request.setAccepted(RequestStatus.Accepted);
		request.setDeclineReason(registrationRequestDTO.getDeclineReason());
		sendDeclineEmail(request.getUserEmail(), request.getDeclineReason());
		return request;
	}

	private void sendDeclineEmail(String email, String declineReason) {
		User user = userRepository.findByEmail(email);
		String subject = "Your registration for the business owner";
		StringBuilder content = new StringBuilder("");
		content.append("Hi ").append(user.getFirstName()).append(",<br><br>").append(
				"Thank you so much for your applying a registration a business owner position. We appreciate you taking the time to visit our web site.<br><br>")
				.append(declineReason).append("<br><br>Thank you!<br>Your company name.");

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

	@Override
	public boolean isUserAuthorized(Set<RoleDTO> roles, String username) {
		User user = userRepository.findByUsername(username);
		for (Role userRole : user.getRoles()) {
			for (RoleDTO requiredRole : roles) {
				if (userRole.getName().equals(requiredRole.getName()))
					return true;
			}
		}
		return false;
	}

	@Override
	public Set<UserDTO> findAll() {
		Set<User> users = new HashSet<User>(userRepository.findAll());
		Set<UserDTO> userDTOs = new HashSet<UserDTO>();
		for (User user : users) {
			userDTOs.add(UserMapper.UserToDTO(user));
		}
		return userDTOs;	}

	@Override
	@Transactional
	public UserDTO deleteById(Long userId) {
		User user = userRepository.findById(userId).get();
		user.setDeleted(true);
		user.setEnabled(false);
		deleteUserServices(user);
		return UserMapper.UserToDTO(user);
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
}
