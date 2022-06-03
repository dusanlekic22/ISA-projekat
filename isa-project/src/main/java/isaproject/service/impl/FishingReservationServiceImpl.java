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
import isaproject.model.FishingTrainer;
import isaproject.repository.CustomerRepository;
import isaproject.repository.FishingQuickReservationRepository;
import isaproject.repository.FishingReservationRepository;
import isaproject.repository.FishingTrainerRepository;
import isaproject.service.CustomerService;
import isaproject.service.FishingReservationService;

@Service
public class FishingReservationServiceImpl implements FishingReservationService {

	FishingReservationRepository fishingeReservationRepository;
	FishingQuickReservationRepository fishingeQuickReservationRepository;
	CustomerService customerService;
	CustomerRepository customerRepository;
	FishingTrainerRepository fishingTrainerRepository;

	@Autowired
	public FishingReservationServiceImpl(FishingReservationRepository fishingeReservationRepository,
			FishingQuickReservationRepository fishingeQuickReservationRepository, CustomerService customerService,
			CustomerRepository customerRepository, FishingTrainerRepository fishingTrainerRepository) {
		super();
		this.fishingeReservationRepository = fishingeReservationRepository;
		this.fishingeQuickReservationRepository = fishingeQuickReservationRepository;
		this.customerService = customerService;
		this.customerRepository = customerRepository;
		this.fishingTrainerRepository = fishingTrainerRepository;
	}

	@Override
	public FishingReservationDTO findById(Long id) {
		FishingReservation fishingeReservation = fishingeReservationRepository.findById(id).orElse(null);
		return FishingReservationMapper.FishingReservationToDTO(fishingeReservation);
	}

	@Override
	public Set<FishingReservationDTO> findAll() {
		Set<FishingReservation> fishinges = new HashSet<>(fishingeReservationRepository.findAll());
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservation fr : fishinges) {
			dtos.add(FishingReservationMapper.FishingReservationToDTO(fr));
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
				for (FishingReservation fishingeReservation : p.getFishingReservation()) {
					if (fishingeReservation.getDuration().isBetween(LocalDateTime.now())) {
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
	public FishingReservationDTO reserveCustomer(FishingReservationDTO fishingeReservationDTO) {
		FishingReservation fishingeReservation = FishingReservationMapper
				.DTOToFishingReservation(fishingeReservationDTO);
		fishingeReservation.setConfirmed(true);
		fishingeReservation
				.setCustomer(customerRepository.findById(fishingeReservationDTO.getCustomer().getId()).get());
		if (!fishingeReservation.getDuration().isHoursBefore(LocalDateTime.now(), 1)) {
			return null;
		}

		for (FishingReservation q : fishingeReservationRepository
				.findByFishingCourse_FishingTrainer_Id(fishingeReservation.getFishingCourse().getFishingTrainer().getId())) {
			if (q.getDuration().overlapsWith(fishingeReservation.getDuration())) {
				return null;
			}
		}

		boolean overlaps = false;
		FishingTrainer fishingTrainer = fishingTrainerRepository
				.findByUsername(fishingeReservation.getFishingCourse().getFishingTrainer().getUsername());
		for (DateTimeSpan dateTimeSpan : fishingTrainer.getAvailableReservationDateSpan()) {
			if (fishingeReservation.getDuration().overlapsWith(dateTimeSpan)) {
				overlaps = true;
				reserveAvailableDateSpanForCustomer(fishingeReservation, dateTimeSpan);
				break;
			}
		}

		if (!overlaps)
			return null;

		return FishingReservationMapper
				.FishingReservationToDTO(fishingeReservationRepository.save(fishingeReservation));
	}

	private boolean isCustomersReservationInAction(FishingReservation newReservation,
			FishingReservation existingReservation) {
		return (existingReservation.getCustomer().getId() == newReservation.getCustomer().getId())
				&& existingReservation.getDuration().isBetween(LocalDateTime.now());
	}

	private void reserveAvailableDateSpanForOwner(FishingReservation fishingeReservation,
			DateTimeSpan availableDateSpan) {
		FishingCourse fishingeCourse = fishingeReservation.getFishingCourse();
		FishingTrainer fishingTrainer = fishingTrainerRepository
				.findByUsername(fishingeCourse.getFishingTrainer().getUsername());
		DateTimeSpan duration = fishingeReservation.getDuration();
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

	private void reserveAvailableDateSpanForCustomer(FishingReservation fishingeReservation,
			DateTimeSpan availableDateSpan) {
		FishingCourse fishingeCourse = fishingeReservation.getFishingCourse();
		FishingTrainer fishingTrainer = fishingTrainerRepository
				.findByUsername(fishingeCourse.getFishingTrainer().getUsername());
		DateTimeSpan duration = fishingeReservation.getDuration();
		fishingTrainer.getAvailableReservationDateSpan().remove(availableDateSpan);
		if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) == 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());
			fishingTrainer.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) == 0) {
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
	public FishingReservationDTO reserveFishingOwner(FishingReservationDTO fishingeReservationDTO, String siteUrl) {
		FishingReservation fishingeReservation = FishingReservationMapper
				.DTOToFishingReservation(fishingeReservationDTO);
		fishingeReservation.setConfirmed(false);
		fishingeReservation
				.setCustomer(customerRepository.findById(fishingeReservationDTO.getCustomer().getId()).get());
		if (!fishingeReservation.getDuration().isHoursBefore(LocalDateTime.now(), 1)) {
			return null;
		}

		boolean inAction = false;
		for (FishingReservation q : fishingeReservationRepository
				.findByFishingCourse_FishingTrainer_Id(fishingeReservation.getFishingCourse().getFishingTrainer().getId())) {

			if (q.getDuration().overlapsWith(fishingeReservation.getDuration())) {
				return null;
			}

			if (isCustomersReservationInAction(fishingeReservation, q)) {
				inAction = true;
			}
		}

		for (FishingQuickReservation q : fishingeQuickReservationRepository
				.findByFishingCourse_FishingTrainer_Id(fishingeReservation.getFishingCourse().getFishingTrainer().getId())) {

			if (q.getDuration().overlapsWith(fishingeReservation.getDuration())) {
				return null;
			}
		}

		if (!inAction) {
			return null;
		}

		FishingTrainer fishingTrainer = fishingTrainerRepository
				.findByUsername(fishingeReservation.getFishingCourse().getFishingTrainer().getUsername());
		for (DateTimeSpan dateTimeSpan : fishingTrainer.getAvailableReservationDateSpan()) {
			if (fishingeReservation.getDuration().overlapsWith(dateTimeSpan)) {
				reserveAvailableDateSpanForOwner(fishingeReservation, dateTimeSpan);
				break;
			}
		}
		FishingReservation fishingeReservationReturn = fishingeReservationRepository.save(fishingeReservation);

		customerService.sendReservationConfirmationEmail(siteUrl, fishingeReservationReturn);

		return FishingReservationMapper.FishingReservationToDTO(fishingeReservationReturn);
	}

	@Transactional
	@Override
	public Set<FishingReservationDTO> findByFishingCourseFishingTrainerId(Long id) {
		Set<FishingReservation> fishingeReservations = new HashSet<>(
				fishingeReservationRepository.findByFishingCourse_FishingTrainer_Id(id));
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservation p : fishingeReservations) {
			dtos.add(FishingReservationMapper.FishingReservationToDTO(p));
		}
		return dtos;
	}

	@Transactional
	@Override
	public FishingReservationDTO deleteById(Long id) {
		FishingReservation fishingeReservation = fishingeReservationRepository.findById(id).get();
		freeReservedSpan(fishingeReservation);
		fishingeQuickReservationRepository.deleteById(id);
		return FishingReservationMapper.FishingReservationToDTO(fishingeReservation);
	}

	private void freeReservedSpan(FishingReservation fishingeReservation) {
		FishingCourse fishingeCourse = fishingeReservation.getFishingCourse();
		FishingTrainer fishingTrainer = fishingTrainerRepository
				.findByUsername(fishingeCourse.getFishingTrainer().getUsername());
		DateTimeSpan duration = fishingeReservation.getDuration();
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

	@Override
	public Set<FishingReservationDTO> findAllPast() {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingeReservationDTO : findAll()) {
			if (fishingeReservationDTO.getDuration().passed())
				dtos.add(fishingeReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<FishingReservationDTO> findAllActive() {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingeReservationDTO : findAll()) {
			if (!fishingeReservationDTO.getDuration().passed())
				dtos.add(fishingeReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<FishingReservationDTO> findAllPastByFishingCourseId(Long id) {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingeReservationDTO : findByFishingCourseId(id)) {
			if (fishingeReservationDTO.getDuration().passed())
				dtos.add(fishingeReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<FishingReservationDTO> findAllActiveByFishingCourseId(Long id) {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingeReservationDTO : findByFishingCourseId(id)) {
			if (!fishingeReservationDTO.getDuration().passed())
				dtos.add(fishingeReservationDTO);
		}
		return dtos;
	}

	@Transactional
	@Override
	public Set<FishingReservationDTO> findByFishingCourseId(Long id) {
		Set<FishingReservation> fishingeReservations = new HashSet<>(
				fishingeReservationRepository.findByFishingCourseId(id));
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservation p : fishingeReservations) {
			dtos.add(FishingReservationMapper.FishingReservationToDTO(p));
		}
		return dtos;
	}

	@Override
	public FishingReservationDTO confirmReservation(Long id) {
		FishingReservation fishingeReservation = fishingeReservationRepository.findById(id).get();
		fishingeReservation.setConfirmed(true);
		return FishingReservationMapper
				.FishingReservationToDTO(fishingeReservationRepository.save(fishingeReservation));
	}

	@Override
	public Set<FishingReservationDTO> findAllActiveByFishingTrainerId(Long id) {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingeReservationDTO : findByFishingCourseFishingTrainerId(id)) {
			if (!fishingeReservationDTO.getDuration().passed())
				dtos.add(fishingeReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<FishingReservationDTO> findAllPastByFishingTrainerId(Long id) {
		Set<FishingReservationDTO> dtos = new HashSet<>();
		for (FishingReservationDTO fishingeReservationDTO : findByFishingCourseFishingTrainerId(id)) {
			if (fishingeReservationDTO.getDuration().passed())
				dtos.add(fishingeReservationDTO);
		}
		return dtos;
	}

}
