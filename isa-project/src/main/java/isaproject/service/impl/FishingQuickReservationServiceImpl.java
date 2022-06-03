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
import isaproject.model.FishingTrainer;
import isaproject.repository.CustomerRepository;
import isaproject.repository.FishingQuickReservationRepository;
import isaproject.repository.FishingReservationRepository;
import isaproject.repository.FishingTrainerRepository;
import isaproject.service.CustomerService;
import isaproject.service.FishingQuickReservationService;

@Service
public class FishingQuickReservationServiceImpl implements FishingQuickReservationService {

	FishingQuickReservationRepository fishingQuickReservationRepository;
	FishingReservationRepository fishingReservationRepository;
	CustomerRepository customerRepository;
	CustomerService customerService;
	FishingTrainerRepository fishingTrainerRepository;

	@Autowired
	public FishingQuickReservationServiceImpl(FishingQuickReservationRepository fishingCourseQuickReservationRepository,
			FishingReservationRepository fishingCourseReservationRepository, CustomerRepository customerRepository,
			CustomerService customerService, FishingTrainerRepository fishingTrainerRepository) {
		super();
		this.fishingQuickReservationRepository = fishingCourseQuickReservationRepository;
		this.fishingReservationRepository = fishingCourseReservationRepository;
		this.customerRepository = customerRepository;
		this.customerService = customerService;
		this.fishingTrainerRepository = fishingTrainerRepository;
	}

	@Override
	public FishingQuickReservationDTO findById(Long id) {
		FishingQuickReservation fishingCourseQuickReservation = fishingQuickReservationRepository.findById(id)
				.orElse(null);
		return FishingQuickReservationMapper.FishingQuickReservationToDTO(fishingCourseQuickReservation);
	}

	@Override
	public Set<FishingQuickReservationDTO> findAll() {
		Set<FishingQuickReservation> fishingCourses = new HashSet<>(fishingQuickReservationRepository.findAll());
		Set<FishingQuickReservationDTO> dtos = new HashSet<>();
		for (FishingQuickReservation qr : fishingCourses) {
			dtos.add(FishingQuickReservationMapper.FishingQuickReservationToDTO(qr));
		}
		return dtos;
	}

	private void reserveAvailableDateSpan(FishingQuickReservation fishingCourseQuickReservation,
			DateTimeSpan availableDateSpan) {
		FishingCourse fishingCourse = fishingCourseQuickReservation.getFishingCourse();
		FishingTrainer fishingTrainer = fishingTrainerRepository
				.findByUsername(fishingCourse.getFishingTrainer().getUsername());
		DateTimeSpan duration = fishingCourseQuickReservation.getDuration();
		fishingTrainer.getAvailableReservationDateSpan().remove(availableDateSpan);
		if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) <= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());
			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) >= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(availableDateSpan.getStartDate(), duration.getStartDate());
			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan1 = new DateTimeSpan(availableDateSpan.getStartDate(), duration.getStartDate());
			DateTimeSpan newDateSpan2 = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());
			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan1);
			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan2);
		}
		fishingTrainerRepository.save(fishingTrainer);
	}

	@Transactional
	@Override
	public FishingQuickReservationDTO save(FishingQuickReservationDTO fishingCourseQuickReservationDTO,
			String siteUrl) {
		FishingQuickReservation fishingCourseQuickReservation = FishingQuickReservationMapper
				.DTOToFishingQuickReservation(fishingCourseQuickReservationDTO);
		fishingCourseQuickReservation.setReserved(false);

		if (!fishingCourseQuickReservation.getDuration().isHoursBefore(LocalDateTime.now(), 1)) {
			return null;
		}

		for (FishingQuickReservation q : fishingQuickReservationRepository.findByFishingCourse_FishingTrainer_Id(
				fishingCourseQuickReservation.getFishingCourse().getFishingTrainer().getId())) {
			if (q.getDuration().overlapsWith(fishingCourseQuickReservation.getDuration())) {
				return null;
			}
		}

		for (FishingReservation q : fishingReservationRepository.findByFishingCourse_FishingTrainer_Id(
				fishingCourseQuickReservation.getFishingCourse().getFishingTrainer().getId())) {
			if (q.getDuration().overlapsWith(fishingCourseQuickReservation.getDuration())) {
				return null;
			}
		}

		FishingTrainer fishingTrainer = fishingTrainerRepository
				.findByUsername(fishingCourseQuickReservation.getFishingCourse().getFishingTrainer().getUsername());
		for (DateTimeSpan dateTimeSpan : fishingTrainer.getAvailableReservationDateSpan()) {
			if (fishingCourseQuickReservation.getDuration().overlapsWith(dateTimeSpan)) {
				reserveAvailableDateSpan(fishingCourseQuickReservation, dateTimeSpan);
				break;
			}
		}

		FishingQuickReservation fishingCourseQuickReservationReturn = fishingQuickReservationRepository
				.save(fishingCourseQuickReservation);
		for (Customer customer : fishingCourseQuickReservation.getFishingCourse().getSubscribers()) {
			customerService.sendNewQuickReservationEmail(customer, siteUrl, fishingCourseQuickReservationReturn);
		}

		return FishingQuickReservationMapper.FishingQuickReservationToDTO(fishingCourseQuickReservationReturn);
	}

	@Override
	public Set<FishingQuickReservationDTO> findByFishingCourseFishingTrainerId(Long id) {
		Set<FishingQuickReservation> fishingCourseQuickReservations = new HashSet<>(
				fishingQuickReservationRepository.findByFishingCourse_FishingTrainer_Id(id));
		Set<FishingQuickReservationDTO> dtos = new HashSet<>();
		if (fishingCourseQuickReservations.size() != 0) {

			FishingQuickReservationDTO dto;
			for (FishingQuickReservation p : fishingCourseQuickReservations) {
				dto = FishingQuickReservationMapper.FishingQuickReservationToDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

	@Transactional
	@Override
	public FishingQuickReservationDTO deleteById(Long id) {
		FishingQuickReservation fishingCourseQuickReservation = fishingQuickReservationRepository.findById(id).get();
		freeReservedSpan(fishingCourseQuickReservation);
		fishingQuickReservationRepository.deleteById(id);
		return FishingQuickReservationMapper.FishingQuickReservationToDTO(fishingCourseQuickReservation);
	}

	private void freeReservedSpan(FishingQuickReservation fishingCourseQuickReservation) {
		FishingCourse fishingCourse = fishingCourseQuickReservation.getFishingCourse();
		FishingTrainer fishingTrainer = fishingTrainerRepository
				.findByUsername(fishingCourse.getFishingTrainer().getUsername());
		DateTimeSpan duration = fishingCourseQuickReservation.getDuration();
		DateTimeSpan newAvailableDateSpan = new DateTimeSpan(duration);
		Set<DateTimeSpan> availableDateSpans = new HashSet<>(fishingTrainer.getAvailableReservationDateSpan());
		boolean startChanged = false;
		boolean endChanged = false;
		for (DateTimeSpan dateTimeSpan : availableDateSpans) {
			if (newAvailableDateSpan.getStartDate().compareTo(dateTimeSpan.getEndDate()) == 0) {
				fishingTrainer.getAvailableReservationDateSpan().remove(dateTimeSpan);
				if (endChanged) {
					fishingTrainer.getAvailableReservationDateSpan().remove(newAvailableDateSpan);
					newAvailableDateSpan = new DateTimeSpan(dateTimeSpan.getStartDate(),
							newAvailableDateSpan.getEndDate());
				} else {
					newAvailableDateSpan = new DateTimeSpan(dateTimeSpan.getStartDate(), duration.getEndDate());
					startChanged = true;
				}
				fishingTrainer.getAvailableReservationDateSpan().add(newAvailableDateSpan);
			}
			if (newAvailableDateSpan.getEndDate().compareTo(dateTimeSpan.getStartDate()) == 0) {
				fishingTrainer.getAvailableReservationDateSpan().remove(dateTimeSpan);
				if (startChanged) {
					fishingTrainer.getAvailableReservationDateSpan().remove(newAvailableDateSpan);
					newAvailableDateSpan = new DateTimeSpan(newAvailableDateSpan.getStartDate(),
							dateTimeSpan.getEndDate());
				} else {
					newAvailableDateSpan = new DateTimeSpan(duration.getStartDate(), dateTimeSpan.getEndDate());
					endChanged = true;
				}
				fishingTrainer.getAvailableReservationDateSpan().add(newAvailableDateSpan);
			}
		}

		fishingTrainerRepository.save(fishingTrainer);
	}

	@Transactional
	@Override
	public FishingReservationDTO appointQuickReservation(Long reservationId, Long userId) {
		FishingQuickReservation fishingCourseQuickReservation = fishingQuickReservationRepository
				.findById(reservationId).get();
		if (!fishingCourseQuickReservation.getDuration().isHoursBefore(LocalDateTime.now(), 1)) {
			return null;
		}
		fishingCourseQuickReservation.setReserved(true);
		fishingQuickReservationRepository.save(fishingCourseQuickReservation);
		FishingReservation fishingCourseReservation = FishingQuickReservationMapper
				.FishingQuickReservationToFishingReservation(fishingCourseQuickReservation);
		fishingCourseReservation.setCustomer(customerRepository.findById(userId).get());
		FishingReservation fishingCourseReservationReturn = fishingReservationRepository.save(fishingCourseReservation);
		return FishingReservationMapper.FishingReservationToDTO(fishingCourseReservationReturn);
	}

	@Override
	public Set<FishingQuickReservationDTO> findByIsReservedFalseAndFishingCourseId(Long id) {
		Set<FishingQuickReservation> fishingCourseQuickReservations = new HashSet<>(
				fishingQuickReservationRepository.findByIsReservedFalseAndFishingCourseId(id));
		Set<FishingQuickReservationDTO> dtos = new HashSet<>();
		if (fishingCourseQuickReservations.size() != 0) {

			FishingQuickReservationDTO dto;
			for (FishingQuickReservation p : fishingCourseQuickReservations) {
				dto = FishingQuickReservationMapper.FishingQuickReservationToDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

	@Override
	public Set<FishingQuickReservationDTO> findByIsReservedFalse() {
		Set<FishingQuickReservation> fishingCourseQuickReservations = new HashSet<>(
				fishingQuickReservationRepository.findByIsReservedFalse());
		Set<FishingQuickReservationDTO> dtos = new HashSet<>();
		if (fishingCourseQuickReservations.size() != 0) {

			FishingQuickReservationDTO dto;
			for (FishingQuickReservation p : fishingCourseQuickReservations) {
				dto = FishingQuickReservationMapper.FishingQuickReservationToDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

	@Override
	public Set<FishingQuickReservationDTO> findByFishingTrainerId(Long id) {
		Set<FishingQuickReservation> fishingCourseQuickReservations = new HashSet<>(
				fishingQuickReservationRepository.findByFishingCourse_FishingTrainer_Id(id));
		Set<FishingQuickReservationDTO> dtos = new HashSet<>();
		for (FishingQuickReservation p : fishingCourseQuickReservations) {
			dtos.add(FishingQuickReservationMapper.FishingQuickReservationToDTO(p));
		}
		return dtos;
	}

}
