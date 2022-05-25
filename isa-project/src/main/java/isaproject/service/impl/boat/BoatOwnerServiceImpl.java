package isaproject.service.impl.boat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.mapper.UserMapper;
import isaproject.model.BusinessOwnerRegistrationRequest;
import isaproject.model.boat.BoatOwner;
import isaproject.repository.AddressRepository;
import isaproject.repository.BusinessOwnerRegistrationRequestRepository;
import isaproject.repository.boat.BoatOwnerRepository;
import isaproject.service.RoleService;
import isaproject.service.boat.BoatOwnerService;

@Service
public class BoatOwnerServiceImpl implements BoatOwnerService{

	private BoatOwnerRepository boatOwnerRepository;
	private RoleService roleService;
	private PasswordEncoder passwordEncoder;
	private AddressRepository addressRepository;
	private BusinessOwnerRegistrationRequestRepository registrationRequestRepository;

	@Autowired
	public BoatOwnerServiceImpl(BoatOwnerRepository boatOwnerRepository, RoleService roleService,
			PasswordEncoder passwordEncoder, AddressRepository addressRepository,
			BusinessOwnerRegistrationRequestRepository registrationRequestRepository) {
		super();
		this.boatOwnerRepository = boatOwnerRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.addressRepository = addressRepository;
		this.registrationRequestRepository = registrationRequestRepository;
	}

	@Override
	public BoatOwner registerBoatOwner(BusinessOwnerDTO businessOwnerDTO) {
//		addressRepository.save(businessOwnerDTO.getAddress());
		BoatOwner boatOwner = UserMapper.DTOToBoatOwner(businessOwnerDTO);
		boatOwner.setRoles(roleService.findByName("ROLE_COTTAGE_OWNER"));
		boatOwner.setPassword(passwordEncoder.encode(businessOwnerDTO.getPassword()));
		boatOwner.setEnabled(false);

		BusinessOwnerRegistrationRequest request = new BusinessOwnerRegistrationRequest();
		request.setAccepted(null);
		request.setRegistrationExplanation(businessOwnerDTO.getRegistrationExplanation());
		request.setUserEmail(boatOwner.getEmail());
		registrationRequestRepository.save(request);

		return this.boatOwnerRepository.save(boatOwner);
	}

}
