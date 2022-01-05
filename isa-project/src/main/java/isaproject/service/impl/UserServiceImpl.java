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
import isaproject.model.User;
import isaproject.repository.BusinessOwnerRegistrationRequestRepository;
import isaproject.repository.UserRepository;
import isaproject.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private BusinessOwnerRegistrationRequestRepository requestRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository,
			BusinessOwnerRegistrationRequestRepository requestRepository) {
		super();
		this.userRepository = userRepository;
		this.requestRepository = requestRepository;
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
		return request;
	}
	
	@Transactional
	@Override
	public BusinessOwnerRegistrationRequest declineUser(BusinessOwnerRegistrationRequestDTO registrationRequestDTO) {
		BusinessOwnerRegistrationRequest request = requestRepository.getById(registrationRequestDTO.getId());
		if (request.getAccepted() != null) return request;
		request.setAccepted(false);
		request.setDeclineReason(registrationRequestDTO.getDeclineReason());
		return request;
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
