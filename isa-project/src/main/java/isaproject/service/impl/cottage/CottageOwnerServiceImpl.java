package isaproject.service.impl.cottage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.mapper.UserMapper;
import isaproject.model.BusinessOwnerRegistrationRequest;
import isaproject.model.cottage.CottageOwner;
import isaproject.repository.AddressRepository;
import isaproject.repository.BusinessOwnerRegistrationRequestRepository;
import isaproject.repository.cottage.CottageOwnerRepository;
import isaproject.service.RoleService;
import isaproject.service.cottage.CottageOwnerService;

@Service
public class CottageOwnerServiceImpl implements CottageOwnerService{

	private CottageOwnerRepository cottageOwnerRepository;
	private RoleService roleService;
	private PasswordEncoder passwordEncoder;
	private AddressRepository addressRepository;
	private BusinessOwnerRegistrationRequestRepository registrationRequestRepository;

	@Autowired
	public CottageOwnerServiceImpl(CottageOwnerRepository cottageOwnerRepository, RoleService roleService,
			PasswordEncoder passwordEncoder, AddressRepository addressRepository,
			BusinessOwnerRegistrationRequestRepository registrationRequestRepository) {
		super();
		this.cottageOwnerRepository = cottageOwnerRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.addressRepository = addressRepository;
		this.registrationRequestRepository = registrationRequestRepository;
	}

	@Override
	public CottageOwner registerCottageOwner(BusinessOwnerDTO businessOwnerDTO) {
//		addressRepository.save(businessOwnerDTO.getAddress());
		CottageOwner cottageOwner = UserMapper.DTOToCottageOwner(businessOwnerDTO);
		cottageOwner.setRoles(roleService.findByName("ROLE_COTTAGE_OWNER"));
		cottageOwner.setPassword(passwordEncoder.encode(businessOwnerDTO.getPassword()));
		cottageOwner.setEnabled(false);

		BusinessOwnerRegistrationRequest request = new BusinessOwnerRegistrationRequest();
		request.setAccepted(null);
		request.setRegistrationExplanation(businessOwnerDTO.getRegistrationExplanation());
		request.setUserEmail(cottageOwner.getEmail());
		registrationRequestRepository.save(request);

		return this.cottageOwnerRepository.save(cottageOwner);
	}

}
