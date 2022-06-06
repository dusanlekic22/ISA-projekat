package isaproject.service.impl.cottage;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.GradeDTO;
import isaproject.dto.cottage.CottageDTO;
import isaproject.dto.cottage.CottageOwnerDTO;
import isaproject.dto.cottage.CottageQuickReservationDTO;
import isaproject.dto.cottage.CottageReservationDTO;
import isaproject.mapper.GradeMapper;
import isaproject.mapper.UserMapper;
import isaproject.model.BusinessOwnerRegistrationRequest;
import isaproject.model.DateTimeSpan;
import isaproject.model.Grade;
import isaproject.model.LoyaltyProgram;
import isaproject.model.LoyaltyRank;
import isaproject.model.cottage.CottageOwner;
import isaproject.repository.AddressRepository;
import isaproject.repository.BusinessOwnerRegistrationRequestRepository;
import isaproject.repository.cottage.CottageOwnerRepository;
import isaproject.service.RoleService;
import isaproject.service.cottage.CottageOwnerService;
import isaproject.service.cottage.CottageQuickReservationService;
import isaproject.service.cottage.CottageReservationService;
import isaproject.service.cottage.CottageService;

@Service
public class CottageOwnerServiceImpl implements CottageOwnerService {

	private CottageOwnerRepository cottageOwnerRepository;
	private RoleService roleService;
	private PasswordEncoder passwordEncoder;
	private AddressRepository addressRepository;
	private BusinessOwnerRegistrationRequestRepository registrationRequestRepository;
	private CottageReservationService cottageReservationService;
	private CottageQuickReservationService cottageQuickReservationService;
	private CottageService cottageService;

	@Autowired
	public CottageOwnerServiceImpl(CottageOwnerRepository cottageOwnerRepository, RoleService roleService,
			PasswordEncoder passwordEncoder, AddressRepository addressRepository,
			BusinessOwnerRegistrationRequestRepository registrationRequestRepository,
			CottageReservationService cottageReservationService,
			CottageQuickReservationService cottageQuickReservationService, CottageService cottageService) {
		super();
		this.cottageOwnerRepository = cottageOwnerRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.addressRepository = addressRepository;
		this.registrationRequestRepository = registrationRequestRepository;
		this.cottageReservationService = cottageReservationService;
		this.cottageQuickReservationService = cottageQuickReservationService;
		this.cottageService = cottageService;
	}

	@Override
	public CottageOwner registerCottageOwner(BusinessOwnerDTO businessOwnerDTO) {
		addressRepository.save(businessOwnerDTO.getAddress());
		CottageOwner cottageOwner = UserMapper.DTOToCottageOwner(businessOwnerDTO);
		cottageOwner.setRoles(roleService.findByName("ROLE_COTTAGE_OWNER"));
		cottageOwner.setPassword(passwordEncoder.encode(businessOwnerDTO.getPassword()));
		cottageOwner.setEnabled(false);
		LoyaltyProgram loyaltyProgram = new LoyaltyProgram();
		loyaltyProgram.setLoyaltyRank(LoyaltyRank.Regular);
		loyaltyProgram.setPoints(0);
		cottageOwner.setLoyaltyProgram(loyaltyProgram);

		BusinessOwnerRegistrationRequest request = new BusinessOwnerRegistrationRequest();
		request.setAccepted(null);
		request.setRegistrationExplanation(businessOwnerDTO.getRegistrationExplanation());
		request.setUserEmail(cottageOwner.getEmail());
		registrationRequestRepository.save(request);

		return this.cottageOwnerRepository.save(cottageOwner);
	}

	@Transactional
	@Override
	public CottageOwnerDTO updateUnavailableTerms(Long id, DateTimeSpan newDateSpan) {
		CottageOwner cottageOwner = cottageOwnerRepository.findById(id).get();
		for (CottageReservationDTO cottageOwnerReservation : cottageReservationService
				.findByCottageCottageOwnerId(id)) {
			if (newDateSpan.overlapsWith(cottageOwnerReservation.getDuration()))
				return null;
		}
		for (CottageQuickReservationDTO cottageOwnerQuickReservation : cottageQuickReservationService
				.findByCottageOwnerId(id)) {
			if (newDateSpan.overlapsWith(cottageOwnerQuickReservation.getDuration()))
				return null;
		}
		for (CottageDTO cottage : cottageService.findByCottageOwnerId(cottageOwner.getId())) {
			for (DateTimeSpan dateTimeSpan : cottage.getAvailableReservationDateSpan()) {
				if (newDateSpan.overlapsWith(dateTimeSpan))
					return null;
			}
		}

		boolean overlapped = false;
		Set<DateTimeSpan> unavailableDateSpans = new HashSet<>(cottageOwner.getUnavailableReservationDateSpan());
		for (DateTimeSpan reservationSpan : unavailableDateSpans) {
			if (reservationSpan.overlapsWith(newDateSpan)) {
				cottageOwner.getUnavailableReservationDateSpan().remove(reservationSpan);
				if (newDateSpan.getStartDate().compareTo(reservationSpan.getStartDate()) <= 0) {
					reservationSpan = new DateTimeSpan(newDateSpan.getStartDate(), reservationSpan.getEndDate());
				}
				if (newDateSpan.getEndDate().compareTo(reservationSpan.getEndDate()) >= 0) {
					reservationSpan = new DateTimeSpan(reservationSpan.getStartDate(), newDateSpan.getEndDate());
				}
				overlapped = true;
				cottageOwner.getUnavailableReservationDateSpan().add(reservationSpan);
			}
		}
		if (!overlapped)
			cottageOwner.getUnavailableReservationDateSpan().add(newDateSpan);

		return UserMapper.CottageOwnerToDTO(cottageOwnerRepository.save(cottageOwner));
	}

	@Override
	public CottageOwnerDTO findByUsername(String username) {
		CottageOwner cottageOwner = cottageOwnerRepository.findByUsername(username);
		return UserMapper.CottageOwnerToDTO(cottageOwner);
	}
	
	@Override
	@Transactional
	public CottageOwnerDTO addGrade(GradeDTO gradeDTO,long cottageId) {
		gradeDTO.setIsAccepted(null);
		CottageOwner cottageOwner = cottageOwnerRepository.findById(cottageId).get();
		for(Grade grade: cottageOwner.getGrades()) {
			if(grade.getUser().getId() == gradeDTO.getUser().getId()) {
				cottageOwner.getGrades().remove(grade);
				break;
			}
		}
		cottageOwner.getGrades().add(GradeMapper.GradeDTOToGrade(gradeDTO));
		cottageOwnerRepository.save(cottageOwner);
		return UserMapper.CottageOwnerToDTO(cottageOwner);
	}

}
