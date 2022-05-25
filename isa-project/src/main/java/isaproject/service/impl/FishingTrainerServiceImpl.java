package isaproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.FishingTrainerDTO;
import isaproject.mapper.UserMapper;
import isaproject.model.BusinessOwnerRegistrationRequest;
import isaproject.model.FishingTrainer;
import isaproject.repository.AddressRepository;
import isaproject.repository.BusinessOwnerRegistrationRequestRepository;
import isaproject.repository.FishingTrainerRepository;
import isaproject.service.FishingTrainerService;
import isaproject.service.RoleService;

@Service
public class FishingTrainerServiceImpl implements FishingTrainerService {

	private FishingTrainerRepository fishingTrainerRepository;
	private RoleService roleService;
	private PasswordEncoder passwordEncoder;
	private AddressRepository addressRepository;
	private BusinessOwnerRegistrationRequestRepository registrationRequestRepository;

	@Autowired
	public FishingTrainerServiceImpl(FishingTrainerRepository fishingTrainerRepository, RoleService roleService,
			PasswordEncoder passwordEncoder, AddressRepository addressRepository,
			BusinessOwnerRegistrationRequestRepository registrationRequestRepository) {
		super();
		this.fishingTrainerRepository = fishingTrainerRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.addressRepository = addressRepository;
		this.registrationRequestRepository = registrationRequestRepository;
	}

	@Override
	public FishingTrainer registerFishingTrainer(BusinessOwnerDTO businessOwnerDTO) {
//		addressRepository.save(businessOwnerDTO.getAddress());
		FishingTrainer fishingTrainer = UserMapper.DTOToFishingTrainer(businessOwnerDTO);
		fishingTrainer.setRoles(roleService.findByName("ROLE_FISHING_TRAINER"));
		fishingTrainer.setPassword(passwordEncoder.encode(businessOwnerDTO.getPassword()));
		fishingTrainer.setEnabled(false);

		BusinessOwnerRegistrationRequest request = new BusinessOwnerRegistrationRequest();
		request.setAccepted(null);
		request.setRegistrationExplanation(businessOwnerDTO.getRegistrationExplanation());
		request.setUserEmail(fishingTrainer.getEmail());
		registrationRequestRepository.save(request);

		return this.fishingTrainerRepository.save(fishingTrainer);
	}

	@Override
	public FishingTrainerDTO findByUsername(String username) {
		FishingTrainer fishingTrainer = fishingTrainerRepository.findByUsername(username);
		return UserMapper.FishingTrainerToDTO(fishingTrainer);
	}

}
