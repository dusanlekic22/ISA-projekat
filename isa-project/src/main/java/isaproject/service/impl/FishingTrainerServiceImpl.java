package isaproject.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.FishingQuickReservationDTO;
import isaproject.dto.FishingReservationDTO;
import isaproject.dto.FishingTrainerDTO;
import isaproject.mapper.UserMapper;
import isaproject.model.BusinessOwnerRegistrationRequest;
import isaproject.model.DateTimeSpan;
import isaproject.model.FishingTrainer;
import isaproject.repository.AddressRepository;
import isaproject.repository.BusinessOwnerRegistrationRequestRepository;
import isaproject.repository.FishingTrainerRepository;
import isaproject.service.FishingQuickReservationService;
import isaproject.service.FishingReservationService;
import isaproject.service.FishingTrainerService;
import isaproject.service.RoleService;

@Service
public class FishingTrainerServiceImpl implements FishingTrainerService {

	private FishingTrainerRepository fishingTrainerRepository;
	private RoleService roleService;
	private PasswordEncoder passwordEncoder;
	private AddressRepository addressRepository;
	private BusinessOwnerRegistrationRequestRepository registrationRequestRepository;
	private FishingReservationService fishingReservationService;
	private FishingQuickReservationService fishingQuickReservationService;

	@Autowired
	public FishingTrainerServiceImpl(FishingTrainerRepository fishingTrainerRepository, RoleService roleService,
			PasswordEncoder passwordEncoder, AddressRepository addressRepository,
			BusinessOwnerRegistrationRequestRepository registrationRequestRepository,
			FishingReservationService fishingReservationService,
			FishingQuickReservationService fishingQuickReservationService) {
		super();
		this.fishingTrainerRepository = fishingTrainerRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.addressRepository = addressRepository;
		this.registrationRequestRepository = registrationRequestRepository;
		this.fishingReservationService = fishingReservationService;
		this.fishingQuickReservationService = fishingQuickReservationService;
	}

	@Override
	public FishingTrainer registerFishingTrainer(BusinessOwnerDTO businessOwnerDTO) {
		addressRepository.save(businessOwnerDTO.getAddress());
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

	@Transactional
	@Override
	public FishingTrainerDTO updateAvailableTerms(Long id, DateTimeSpan dateTimeSpan) {
		FishingTrainer fishingTrainer = fishingTrainerRepository.findById(id).get();

		for (FishingReservationDTO fishingReservation : fishingReservationService
				.findByFishingCourseFishingTrainerId(id)) {
			if (dateTimeSpan.overlapsWith(fishingReservation.getDuration()))
				return null;
		}
		for (FishingQuickReservationDTO fishingQuickReservation : fishingQuickReservationService
				.findByFishingCourseFishingTrainerId(id)) {
			if (dateTimeSpan.overlapsWith(fishingQuickReservation.getDuration()))
				return null;
		}

		boolean overlapped = false;
		Set<DateTimeSpan> availaDateSpans = new HashSet<>(fishingTrainer.getAvailableReservationDateSpan());
		for (DateTimeSpan reservationSpan : availaDateSpans) {
			if (reservationSpan.overlapsWith(dateTimeSpan)) {
				fishingTrainer.getAvailableReservationDateSpan().remove(reservationSpan);
				if (dateTimeSpan.getStartDate().compareTo(reservationSpan.getStartDate()) <= 0) {
					reservationSpan = new DateTimeSpan(dateTimeSpan.getStartDate(), reservationSpan.getEndDate());
				}
				if (dateTimeSpan.getEndDate().compareTo(reservationSpan.getEndDate()) >= 0) {
					reservationSpan = new DateTimeSpan(reservationSpan.getStartDate(), dateTimeSpan.getEndDate());
				}
				overlapped = true;
				fishingTrainer.getAvailableReservationDateSpan().add(reservationSpan);
			}
		}
		if (!overlapped)
			fishingTrainer.getAvailableReservationDateSpan().add(dateTimeSpan);

		return UserMapper.FishingTrainerToDTO(fishingTrainerRepository.save(fishingTrainer));
	}

}
