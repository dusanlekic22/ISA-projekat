package isaproject.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.CustomerDTO;
import isaproject.dto.FishingReservationDTO;
import isaproject.mapper.CustomerMapper;
import isaproject.mapper.FishingReservationMapper;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.model.FishingCourse;
import isaproject.model.FishingQuickReservation;
import isaproject.model.FishingReservation;
import isaproject.repository.CustomerRepository;
import isaproject.repository.FishingCourseRepository;
import isaproject.repository.FishingQuickReservationRepository;
import isaproject.repository.FishingReservationRepository;
import isaproject.service.CustomerService;
import isaproject.service.FishingReservationService;

@Service
public class FishingReservationServiceImpl implements FishingReservationService {

	FishingReservationRepository fishingReservationRepository;
	FishingQuickReservationRepository fishingQuickReservationRepository;
	FishingCourseRepository fishingRepository;
	CustomerService customerService;
	CustomerRepository customerRepository;

	@Autowired
	public FishingReservationServiceImpl(FishingReservationRepository fishingReservationRepository,
			FishingQuickReservationRepository fishingQuickReservationRepository,
			FishingCourseRepository fishingRepository, CustomerService customerService,
			CustomerRepository customerRepository) {
		super();
		this.fishingReservationRepository = fishingReservationRepository;
		this.fishingQuickReservationRepository = fishingQuickReservationRepository;
		this.fishingRepository = fishingRepository;
		this.customerService = customerService;
		this.customerRepository = customerRepository;
	}

	@Override
	public FishingReservationDTO findById(Long id) {
		FishingReservation fishingReservation = fishingReservationRepository.findById(id).orElse(null);
		return FishingReservationMapper.FishingReservationToDTO(fishingReservation);
	}

	@Override
	public Set<FishingReservationDTO> findAll() {
		Set<FishingReservation> fishings = new HashSet<>(fishingReservationRepository.findAll());
		Set<FishingReservationDTO> dtos = new HashSet<>();
		if (fishings.size() != 0) {
			FishingReservationDTO dto;
			for (FishingReservation p : fishings) {
				dto = FishingReservationMapper.FishingReservationToDTO(p);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	@Override
	public Set<CustomerDTO> findCustomersHasCurrentReservation() {
		Set<Customer> customers = new HashSet<>(customerRepository.findAll());
		Set<CustomerDTO> dtos = new HashSet<>();
		if (customers.size() != 0) {
			CustomerDTO dto;
			for (Customer p : customers) {
				for (FishingReservation fishingReservation : p.getFishingReservation()) {
					if (fishingReservation.getDuration().isBetween(LocalDateTime.now())) {
						dto = CustomerMapper.customertoCustomerDTO(p);
						dtos.add(dto);
						break;
					}
				}
			}
		}
		return dtos;
	}

	@Transactional
	@Override
	public FishingReservationDTO reserveCustomer(FishingReservationDTO fishingReservationDTO) {
		FishingReservation fishingReservation = FishingReservationMapper.DTOToFishingReservation(fishingReservationDTO);
		fishingReservation.setConfirmed(true);
		fishingReservation.setCustomer(customerRepository.findById(fishingReservationDTO.getCustomer().getId()).get());
		if (!fishingReservation.getDuration().isDaysAfter(LocalDateTime.now(), 1)) {
			return null;
		}

		for (FishingReservation q : fishingReservationRepository
				.findByFishingCourseId(fishingReservation.getFishingCourse().getId())) {
			if (q.getDuration().overlapsWith(fishingReservation.getDuration())) {
				return null;
			}
		}

		boolean overlaps = false;

		for (DateTimeSpan dateTimeSpan : fishingReservation.getFishingCourse().getFishingTrainer()
				.getAvailableReservationDateSpan()) {
			if (fishingReservation.getDuration().overlapsWith(dateTimeSpan)) {
				overlaps = true;
				reserveAvailableDateSpanForCustomer(fishingReservation, dateTimeSpan);
				break;
			}
		}

		if (!overlaps)
			return null;

		return FishingReservationMapper.FishingReservationToDTO(fishingReservationRepository.save(fishingReservation));
	}

	private boolean isCustomersReservationInAction(FishingReservation newReservation,
			FishingReservation existingReservation) {
		return (existingReservation.getCustomer().getId() == newReservation.getCustomer().getId())
				&& existingReservation.getDuration().isBetween(LocalDateTime.now());
	}

	private void reserveAvailableDateSpanForOwner(FishingReservation fishingReservation,
			DateTimeSpan availableDateSpan) {
		FishingCourse fishingCourse = fishingReservation.getFishingCourse();
		DateTimeSpan duration = fishingReservation.getDuration();
		Set<DateTimeSpan> availableReservation = fishingCourse.getFishingTrainer().getAvailableReservationDateSpan();
		availableReservation.remove(availableDateSpan);
		if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) <= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());
			availableReservation.add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) >= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(availableDateSpan.getStartDate(), duration.getStartDate());

			availableReservation.add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan1 = new DateTimeSpan(availableDateSpan.getStartDate(), duration.getStartDate());
			DateTimeSpan newDateSpan2 = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());

			availableReservation.add(newDateSpan1);
			availableReservation.add(newDateSpan2);
		}
		fishingRepository.save(fishingCourse);
	}

	private void reserveAvailableDateSpanForCustomer(FishingReservation fishingReservation,
			DateTimeSpan availableDateSpan) {
		FishingCourse fishingCourse = fishingReservation.getFishingCourse();
		DateTimeSpan duration = fishingReservation.getDuration();
		Set<DateTimeSpan> availableReservation = fishingCourse.getFishingTrainer().getAvailableReservationDateSpan();
		availableReservation.remove(availableDateSpan);
		if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) == 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());
			availableReservation.add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) == 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(availableDateSpan.getStartDate(), duration.getStartDate());

			availableReservation.add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan1 = new DateTimeSpan(availableDateSpan.getStartDate(), duration.getStartDate());
			DateTimeSpan newDateSpan2 = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());

			availableReservation.add(newDateSpan1);
			availableReservation.add(newDateSpan2);
		}
		fishingRepository.save(fishingCourse);
	}

	@Transactional
	@Override
	public FishingReservationDTO reserveFishingOwner(FishingReservationDTO fishingReservationDTO, String siteUrl) {
		FishingReservation fishingReservation = FishingReservationMapper.DTOToFishingReservation(fishingReservationDTO);
		fishingReservation.setConfirmed(false);
		fishingReservation.setCustomer(customerRepository.findById(fishingReservationDTO.getCustomer().getId()).get());
		if (!fishingReservation.getDuration().isDaysAfter(LocalDateTime.now(), 1)) {
			return null;
		}

		boolean inAction = false;
		for (FishingReservation q : fishingReservationRepository
				.findByFishingCourseId(fishingReservation.getFishingCourse().getId())) {

			if (q.getDuration().overlapsWith(fishingReservation.getDuration())) {
				return null;
			}

			if (isCustomersReservationInAction(fishingReservation, q)) {
				inAction = true;
			}
		}

		for (FishingQuickReservation q : fishingQuickReservationRepository
				.findByFishingCourseId(fishingReservation.getFishingCourse().getId())) {

			if (q.getDuration().overlapsWith(fishingReservation.getDuration())) {
				return null;
			}
		}

		if (!inAction) {
			return null;
		}

		for (DateTimeSpan dateTimeSpan : fishingReservation.getFishingCourse().getFishingTrainer()
				.getAvailableReservationDateSpan()) {
			if (fishingReservation.getDuration().overlapsWith(dateTimeSpan)) {
				reserveAvailableDateSpanForOwner(fishingReservation, dateTimeSpan);
				break;
			}
		}
		FishingReservation fishingReservationReturn = fishingReservationRepository.save(fishingReservation);

		customerService.sendReservationConfirmationEmail(siteUrl, fishingReservationReturn);

		return FishingReservationMapper.FishingReservationToDTO(fishingReservationReturn);
	}

	@Transactional
	@Override
	public Set<FishingReservationDTO> findByFishingCourseId(Long id) {
		Set<FishingReservation> fishingReservations = new HashSet<>(
				fishingReservationRepository.findByFishingCourseId(id));
		Set<FishingReservationDTO> dtos = new HashSet<>();
		if (fishingReservations.size() != 0) {

			FishingReservationDTO dto;
			for (FishingReservation p : fishingReservations) {
				dto = FishingReservationMapper.FishingReservationToDTO(p);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	@Transactional
	@Override
	public FishingReservationDTO deleteById(Long id) {
		FishingReservation fishingReservation = fishingReservationRepository.findById(id).get();
		freeReservedSpan(fishingReservation);
		fishingQuickReservationRepository.deleteById(id);
		return FishingReservationMapper.FishingReservationToDTO(fishingReservation);
	}

	private void freeReservedSpan(FishingReservation fishingReservation) {
		FishingCourse fishingCourse = fishingReservation.getFishingCourse();
		DateTimeSpan duration = fishingReservation.getDuration();
		DateTimeSpan newAvailableDateSpan = new DateTimeSpan(duration);
		Set<DateTimeSpan> availableDateSpans = new HashSet<>(fishingCourse.getFishingTrainer().getAvailableReservationDateSpan());
		Set<DateTimeSpan> availableReservation = fishingCourse.getFishingTrainer().getAvailableReservationDateSpan();
		boolean startChanged = false;
		boolean endChanged = false;
		for (DateTimeSpan dateTimeSpan : availableDateSpans) {
			if (newAvailableDateSpan.getStartDate().compareTo(dateTimeSpan.getEndDate()) == 0) {
				availableReservation.remove(dateTimeSpan);
				if (endChanged) {
					availableReservation.remove(newAvailableDateSpan);
					newAvailableDateSpan = new DateTimeSpan(dateTimeSpan.getStartDate(),
							newAvailableDateSpan.getEndDate());
				} else {
					newAvailableDateSpan = new DateTimeSpan(dateTimeSpan.getStartDate(), duration.getEndDate());
					startChanged = true;
				}
				availableReservation.add(newAvailableDateSpan);
			}
			if (newAvailableDateSpan.getEndDate().compareTo(dateTimeSpan.getStartDate()) == 0) {
				availableReservation.remove(dateTimeSpan);
				if (startChanged) {
					availableReservation.remove(newAvailableDateSpan);
					newAvailableDateSpan = new DateTimeSpan(newAvailableDateSpan.getStartDate(),
							dateTimeSpan.getEndDate());
				} else {
					newAvailableDateSpan = new DateTimeSpan(duration.getStartDate(), dateTimeSpan.getEndDate());
					endChanged = true;
				}
				availableReservation.add(newAvailableDateSpan);
			}
		}

		fishingRepository.save(fishingCourse);
	}

	@Override
	public Set<FishingReservationDTO> findAllPast() {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingReservationDTO : findAll()) {
			if (fishingReservationDTO.getDuration().passed())
				dtos.add(fishingReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<FishingReservationDTO> findAllActive() {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingReservationDTO : findAll()) {
			if (!fishingReservationDTO.getDuration().passed())
				dtos.add(fishingReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<FishingReservationDTO> findAllPastByFishingCourseId(Long id) {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingReservationDTO : findByFishingCourseId(id)) {
			if (fishingReservationDTO.getDuration().passed())
				dtos.add(fishingReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<FishingReservationDTO> findAllActiveByFishingCourseId(Long id) {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingReservationDTO : findByFishingCourseId(id)) {
			if (!fishingReservationDTO.getDuration().passed())
				dtos.add(fishingReservationDTO);
		}
		return dtos;
	}

	@Override
	public FishingReservationDTO confirmReservation(Long id) {
		FishingReservation fishingReservation = fishingReservationRepository.findById(id).get();
		fishingReservation.setConfirmed(true);
		return FishingReservationMapper.FishingReservationToDTO(fishingReservationRepository.save(fishingReservation));
	}

}
