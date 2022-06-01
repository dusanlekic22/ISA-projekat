package isaproject.service.impl.boat;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.boat.BoatDTO;
import isaproject.dto.boat.BoatOwnerDTO;
import isaproject.dto.boat.BoatQuickReservationDTO;
import isaproject.dto.boat.BoatReservationDTO;
import isaproject.mapper.UserMapper;
import isaproject.model.BusinessOwnerRegistrationRequest;
import isaproject.model.DateTimeSpan;
import isaproject.model.boat.BoatOwner;
import isaproject.repository.AddressRepository;
import isaproject.repository.BusinessOwnerRegistrationRequestRepository;
import isaproject.repository.boat.BoatOwnerRepository;
import isaproject.service.RoleService;
import isaproject.service.boat.BoatOwnerService;
import isaproject.service.boat.BoatQuickReservationService;
import isaproject.service.boat.BoatReservationService;
import isaproject.service.boat.BoatService;

@Service
public class BoatOwnerServiceImpl implements BoatOwnerService {

	private BoatOwnerRepository boatOwnerRepository;
	private RoleService roleService;
	private PasswordEncoder passwordEncoder;
	private AddressRepository addressRepository;
	private BusinessOwnerRegistrationRequestRepository registrationRequestRepository;
	private BoatReservationService boatReservationService;
	private BoatQuickReservationService boatQuickReservationService;
	private BoatService boatService;

	@Autowired
	public BoatOwnerServiceImpl(BoatOwnerRepository boatOwnerRepository, RoleService roleService,
			PasswordEncoder passwordEncoder, AddressRepository addressRepository,
			BusinessOwnerRegistrationRequestRepository registrationRequestRepository,
			BoatReservationService boatReservationService, BoatQuickReservationService boatQuickReservationService,
			BoatService boatService) {
		super();
		this.boatOwnerRepository = boatOwnerRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.addressRepository = addressRepository;
		this.registrationRequestRepository = registrationRequestRepository;
		this.boatReservationService = boatReservationService;
		this.boatQuickReservationService = boatQuickReservationService;
		this.boatService = boatService;
	}

	@Override
	public BoatOwner registerBoatOwner(BusinessOwnerDTO businessOwnerDTO) {
		addressRepository.save(businessOwnerDTO.getAddress());
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

	@Transactional
	@Override
	public BoatOwnerDTO updateUnavailableTerms(Long id, DateTimeSpan newDateSpan) {
		BoatOwner boatOwner = boatOwnerRepository.findById(id).get();
		for (BoatReservationDTO boatOwnerReservation : boatReservationService.findByBoatBoatOwnerId(id)) {
			if (newDateSpan.overlapsWith(boatOwnerReservation.getDuration()))
				return null;
		}
		for (BoatQuickReservationDTO boatOwnerQuickReservation : boatQuickReservationService.findByBoatOwnerId(id)) {
			if (newDateSpan.overlapsWith(boatOwnerQuickReservation.getDuration()))
				return null;
		}
		for (BoatDTO boat : boatService.findByBoatOwnerId(boatOwner.getId())) {
			for (DateTimeSpan dateTimeSpan : boat.getAvailableReservationDateSpan()) {
				if (newDateSpan.overlapsWith(dateTimeSpan))
					return null;
			}
		}

		boolean overlapped = false;
		Set<DateTimeSpan> unavailableDateSpans = new HashSet<>(boatOwner.getUnavailableReservationDateSpan());
		for (DateTimeSpan reservationSpan : unavailableDateSpans) {
			if (reservationSpan.overlapsWith(newDateSpan)) {
				boatOwner.getUnavailableReservationDateSpan().remove(reservationSpan);
				if (newDateSpan.getStartDate().compareTo(reservationSpan.getStartDate()) <= 0) {
					reservationSpan = new DateTimeSpan(newDateSpan.getStartDate(), reservationSpan.getEndDate());
				}
				if (newDateSpan.getEndDate().compareTo(reservationSpan.getEndDate()) >= 0) {
					reservationSpan = new DateTimeSpan(reservationSpan.getStartDate(), newDateSpan.getEndDate());
				}
				overlapped = true;
				boatOwner.getUnavailableReservationDateSpan().add(reservationSpan);
			}
		}
		if (!overlapped)
			boatOwner.getUnavailableReservationDateSpan().add(newDateSpan);

		return UserMapper.BoatOwnerToDTO(boatOwnerRepository.save(boatOwner));
	}

	@Override
	public BoatOwnerDTO findByUsername(String username) {
		BoatOwner boatOwner = boatOwnerRepository.findByUsername(username);
		return UserMapper.BoatOwnerToDTO(boatOwner);
	}
}
