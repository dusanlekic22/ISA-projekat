package isaproject.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.FishingQuickReservationDTO;
import isaproject.dto.FishingReservationDTO;
import isaproject.mapper.FishingQuickReservationMapper;
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
import isaproject.service.FishingQuickReservationService;

@Service
public class FishingQuickReservationServiceImpl implements FishingQuickReservationService {

	FishingQuickReservationRepository fishingQuickReservationRepository;
	FishingReservationRepository fishingReservationRepository;
	FishingCourseRepository fishingRepository;
	CustomerRepository customerRepository;
	CustomerService customerService;

	@Autowired
	public FishingQuickReservationServiceImpl(FishingQuickReservationRepository fishingQuickReservationRepository,
			FishingReservationRepository fishingReservationRepository, FishingCourseRepository fishingRepository,
			CustomerRepository customerRepository, CustomerService customerService) {
		super();
		this.fishingQuickReservationRepository = fishingQuickReservationRepository;
		this.fishingReservationRepository = fishingReservationRepository;
		this.fishingRepository = fishingRepository;
		this.customerRepository = customerRepository;
		this.customerService = customerService;
	}

	@Override
	public FishingQuickReservationDTO findById(Long id) {
		FishingQuickReservation fishingQuickReservation = fishingQuickReservationRepository.findById(id).orElse(null);
		return FishingQuickReservationMapper
				.FishingQuickReservationToFishingQuickReservationDTO(fishingQuickReservation);
	}

	@Override
	public Set<FishingQuickReservationDTO> findAll() {
		Set<FishingQuickReservation> fishings = new HashSet<>(fishingQuickReservationRepository.findAll());
		Set<FishingQuickReservationDTO> dtos = new HashSet<>();
		if (fishings.size() != 0) {
			FishingQuickReservationDTO dto;
			for (FishingQuickReservation p : fishings) {
				dto = FishingQuickReservationMapper.FishingQuickReservationToFishingQuickReservationDTO(p);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	private void reserveAvailableDateSpan(FishingQuickReservation fishingQuickReservation,
			DateTimeSpan availableDateSpan) {
		FishingCourse fishingCourse = fishingQuickReservation.getFishingCourse();
		DateTimeSpan duration = fishingQuickReservation.getDuration();
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

	@Transactional
	@Override
	public FishingQuickReservationDTO save(FishingQuickReservationDTO fishingQuickReservationDTO, String siteUrl) {
		FishingQuickReservation fishingQuickReservation = FishingQuickReservationMapper
				.FishingQuickReservationDTOToFishingQuickReservation(fishingQuickReservationDTO);
		fishingQuickReservation.setReserved(false);

		if (!fishingQuickReservation.getDuration().isDaysAfter(LocalDateTime.now(), 1)) {
			return null;
		}

		for (FishingQuickReservation q : fishingQuickReservationRepository
				.findByFishingCourseId(fishingQuickReservation.getFishingCourse().getId())) {
			if (q.getDuration().overlapsWith(fishingQuickReservation.getDuration())) {
				return null;
			}
		}

		for (FishingReservation q : fishingReservationRepository
				.findByFishingCourseId(fishingQuickReservation.getFishingCourse().getId())) {
			if (q.getDuration().overlapsWith(fishingQuickReservation.getDuration())) {
				return null;
			}
		}

		for (DateTimeSpan dateTimeSpan : fishingQuickReservation.getFishingCourse().getFishingTrainer()
				.getAvailableReservationDateSpan()) {
			if (fishingQuickReservation.getDuration().overlapsWith(dateTimeSpan)) {
				reserveAvailableDateSpan(fishingQuickReservation, dateTimeSpan);
				break;
			}
		}
		FishingQuickReservation fishingQuickReservationReturn = fishingQuickReservationRepository
				.save(fishingQuickReservation);
		for (Customer customer : fishingQuickReservation.getFishingCourse().getSubscribers()) {
			System.out.println(customer.getFirstName());
			customerService.sendNewQuickReservationEmail(customer, siteUrl, fishingQuickReservationReturn);
		}

		return FishingQuickReservationMapper
				.FishingQuickReservationToFishingQuickReservationDTO(fishingQuickReservationReturn);
	}

	@Override
	public Set<FishingQuickReservationDTO> findByFishingCourseId(Long id) {
		Set<FishingQuickReservation> fishingQuickReservations = new HashSet<>(
				fishingQuickReservationRepository.findByFishingCourseId(id));
		Set<FishingQuickReservationDTO> dtos = new HashSet<>();
		if (fishingQuickReservations.size() != 0) {

			FishingQuickReservationDTO dto;
			for (FishingQuickReservation p : fishingQuickReservations) {
				dto = FishingQuickReservationMapper.FishingQuickReservationToFishingQuickReservationDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

	@Transactional
	@Override
	public FishingQuickReservationDTO deleteById(Long id) {
		FishingQuickReservation fishingQuickReservation = fishingQuickReservationRepository.findById(id).get();
		freeReservedSpan(fishingQuickReservation);
		fishingQuickReservationRepository.deleteById(id);
		return FishingQuickReservationMapper
				.FishingQuickReservationToFishingQuickReservationDTO(fishingQuickReservation);
	}

	private void freeReservedSpan(FishingQuickReservation fishingQuickReservation) {
		FishingCourse fishingCourse = fishingQuickReservation.getFishingCourse();
		DateTimeSpan duration = fishingQuickReservation.getDuration();
		DateTimeSpan newAvailableDateSpan = new DateTimeSpan(duration);
		Set<DateTimeSpan> availableDateSpans = new HashSet<>(
				fishingCourse.getFishingTrainer().getAvailableReservationDateSpan());
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

	@Transactional
	@Override
	public FishingReservationDTO appointQuickReservation(Long reservationId, Long userId) {
		FishingQuickReservation fishingQuickReservation = fishingQuickReservationRepository.findById(reservationId)
				.get();
		if (!fishingQuickReservation.getDuration().isDaysAfter(LocalDateTime.now(), 1)) {
			return null;
		}
		fishingQuickReservation.setReserved(true);
		fishingQuickReservationRepository.save(fishingQuickReservation);
		FishingReservation fishingReservation = FishingQuickReservationMapper
				.FishingQuickReservationToFishingReservation(fishingQuickReservation);
		fishingReservation.setCustomer(customerRepository.findById(userId).get());
		FishingReservation fishingReservationReturn = fishingReservationRepository.save(fishingReservation);
		return FishingReservationMapper.FishingReservationToDTO(fishingReservationReturn);
	}

	@Override
	public Set<FishingQuickReservationDTO> findByIsReservedFalseAndFishingCourseId(Long id) {
		Set<FishingQuickReservation> fishingQuickReservations = new HashSet<>(
				fishingQuickReservationRepository.findByIsReservedFalseAndFishingCourseId(id));
		Set<FishingQuickReservationDTO> dtos = new HashSet<>();
		if (fishingQuickReservations.size() != 0) {

			FishingQuickReservationDTO dto;
			for (FishingQuickReservation p : fishingQuickReservations) {
				dto = FishingQuickReservationMapper.FishingQuickReservationToFishingQuickReservationDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

	@Override
	public Set<FishingQuickReservationDTO> findByIsReservedFalse() {
		Set<FishingQuickReservation> fishingQuickReservations = new HashSet<>(
				fishingQuickReservationRepository.findByIsReservedFalse());
		Set<FishingQuickReservationDTO> dtos = new HashSet<>();
		if (fishingQuickReservations.size() != 0) {

			FishingQuickReservationDTO dto;
			for (FishingQuickReservation p : fishingQuickReservations) {
				dto = FishingQuickReservationMapper.FishingQuickReservationToFishingQuickReservationDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

}
