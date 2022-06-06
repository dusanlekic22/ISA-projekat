package isaproject.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import isaproject.dto.FishingCourseAvailabilityDTO;
import isaproject.dto.FishingCourseDTO;
import isaproject.dto.GradeDTO;
import isaproject.dto.IncomeDTO;
import isaproject.dto.ReservationCountDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.exception.ReservedServiceException;
import isaproject.mapper.CustomerMapper;
import isaproject.mapper.FishingCourseMapper;
import isaproject.mapper.GradeMapper;
import isaproject.mapper.IncomeMapper;
import isaproject.mapper.ReservationCountMapper;
import isaproject.mapper.SortTypeMapper;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.model.FishingCourse;
import isaproject.model.FishingReservation;
import isaproject.model.Grade;
import isaproject.model.Income;
import isaproject.model.RequestStatus;
import isaproject.model.ReservationCount;
import isaproject.model.SortType;
import isaproject.repository.FishingCourseRepository;
import isaproject.service.CustomerService;
import isaproject.service.FishingCourseService;
import isaproject.service.FishingReservationService;
import isaproject.service.StatisticsService;

@Service
public class FishingCourseServiceImpl implements FishingCourseService {

	private FishingCourseRepository courseRepository;
	private FishingReservationService fishingReservationService;
	private StatisticsService statisticsService;
	private CustomerService customerService;

	@Autowired
	public FishingCourseServiceImpl(FishingCourseRepository courseRepository,
			FishingReservationService fishingReservationService, StatisticsService statisticsService,CustomerService customerService) {
		super();
		this.courseRepository = courseRepository;
		this.fishingReservationService = fishingReservationService;
		this.statisticsService = statisticsService;
		this.customerService = customerService;
	}

	@Override
	public Set<FishingCourseDTO> findAll() {
		Set<FishingCourse> courses = new HashSet<FishingCourse>(courseRepository.findAllByDeletedIsFalse());
		Set<FishingCourseDTO> courseDTOs = new HashSet<FishingCourseDTO>();
		for (FishingCourse fishingCourse : courses) {
			courseDTOs.add(FishingCourseMapper.FishingCourseToDTO(fishingCourse));
		}
		return courseDTOs;
	}

	@Override
	public FishingCourseDTO findById(Long id) {
		FishingCourse course = courseRepository.findById(id).get();
		return FishingCourseMapper.FishingCourseToDTO(course);
	}

	@Transactional
	@Override
	public FishingCourseDTO save(FishingCourseDTO fishingCourseDTO) {
		FishingCourse course = FishingCourseMapper.DTOToFishingCourse(fishingCourseDTO);
		return FishingCourseMapper.FishingCourseToDTO(courseRepository.save(course));
	}

	@Transactional
	@Override
	public FishingCourseDTO update(FishingCourseDTO fishingCourseDTO) {
		FishingCourse fishingCourse = FishingCourseMapper.DTOToFishingCourse(fishingCourseDTO);
		return FishingCourseMapper.FishingCourseToDTO(courseRepository.save(fishingCourse));
	}

	@Transactional
	@Override
	public void deleteById(Long id) {
		FishingCourse course = courseRepository.findById(id).get();
		if (isReserved(course.getId())) {
			throw new ReservedServiceException(
					String.format("Fishing course '%s' is reserved and can't be deleted or changed.", id));
		}
		course.setDeleted(true);
	}

	@Transactional
	@Override
	public FishingCourseDTO updateInfo(FishingCourseDTO fishingCourseDTO) {
		FishingCourse fishingCourse = FishingCourseMapper.DTOToFishingCourse(fishingCourseDTO);
		if (isReserved(fishingCourse.getId())) {
			throw new ReservedServiceException(String
					.format("Fishing course '%s' is reserved and can't be deleted or changed.", fishingCourse.getId()));
		}
		return FishingCourseMapper.FishingCourseToDTO(courseRepository.save(fishingCourse));
	}

	private boolean isReserved(Long id) {
		return !fishingReservationService.findAllActiveByFishingCourseId(id).isEmpty();
	}

	@Override
	public Set<FishingCourseDTO> findByFishingCourseName(String name) {
		Set<FishingCourse> courses = courseRepository.findByName(name);
		Set<FishingCourseDTO> courseDTOs = new HashSet<FishingCourseDTO>();
		for (FishingCourse fishingCourse : courses) {
			courseDTOs.add(FishingCourseMapper.FishingCourseToDTO(fishingCourse));
		}
		return courseDTOs;
	}

	@Override
	public Set<FishingCourseDTO> findByFishingTrainerId(Long id) {
		Set<FishingCourse> courses = courseRepository.findByFishingTrainerId(id);
		Set<FishingCourseDTO> courseDTOs = new HashSet<FishingCourseDTO>();
		for (FishingCourse fishingCourse : courses) {
			courseDTOs.add(FishingCourseMapper.FishingCourseToDTO(fishingCourse));
		}
		return courseDTOs;
	}

	@Override
	public Page<FishingCourseDTO> findAllPagination(List<SortTypeDTO> sortTypesDTO, Pageable pageable) {
		List<Sort.Order> sorts = new ArrayList<>();
		List<SortType> sortTypes = sortTypesDTO.stream()
				.map(sortTypeDTO -> SortTypeMapper.SortTypeDTOToSortType(sortTypeDTO)).collect(Collectors.toList());
		if (sortTypes != null) {
			for (SortType sortType : sortTypes) {
				if (sortType != null && sortType.getField().equals("price_per_hour")) {
					sortType.setField("price");
				}
				if (sortType != null && sortType.getDirection().toLowerCase().contains("desc")) {
					sorts.add(new Sort.Order(Sort.Direction.DESC, sortType.getField()));
				} else if (sortType != null && sortType.getDirection().toLowerCase().contains("asc")) {
					sorts.add(new Sort.Order(Sort.Direction.ASC, sortType.getField()));
				}
			}

			Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sorts));

			return FishingCourseMapper.pageFishingCourseToPageFishingCourseDTO(courseRepository.findAllByDeletedIsFalse(paging));
		} else {
			return FishingCourseMapper.pageFishingCourseToPageFishingCourseDTO(courseRepository.findAllByDeletedIsFalse(pageable));
		}
	}
	
	@Override
	public Page<FishingCourseDTO> findAllPaginationAdmin(List<SortTypeDTO> sortTypesDTO, Pageable pageable) {
		List<Sort.Order> sorts = new ArrayList<>();
		List<SortType> sortTypes = sortTypesDTO.stream()
				.map(sortTypeDTO -> SortTypeMapper.SortTypeDTOToSortType(sortTypeDTO)).collect(Collectors.toList());
		if (sortTypes != null) {
			for (SortType sortType : sortTypes) {
				if (sortType != null && sortType.getField().equals("price_per_hour")) {
					sortType.setField("price");
				}
				if (sortType != null && sortType.getDirection().toLowerCase().contains("desc")) {
					sorts.add(new Sort.Order(Sort.Direction.DESC, sortType.getField()));
				} else if (sortType != null && sortType.getDirection().toLowerCase().contains("asc")) {
					sorts.add(new Sort.Order(Sort.Direction.ASC, sortType.getField()));
				}
			}

			Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sorts));

			return FishingCourseMapper.pageFishingCourseToPageFishingCourseDTO(courseRepository.findAll(paging));
		} else {
			return FishingCourseMapper.pageFishingCourseToPageFishingCourseDTO(courseRepository.findAll(pageable));
		}
	}

	@Override
	public Page<FishingCourseDTO> findByAvailability(FishingCourseAvailabilityDTO fishingCourseAvailability,
			Pageable pageable) {
		int hours = 0;

		String name = "%";
		Double grade = -1.0;
		int bed = 0;
		Long fishingTrainerId = 0L;

		if (fishingCourseAvailability.getName() != null) {
			name = name + fishingCourseAvailability.getName().toLowerCase().concat("%");
		}
		if (fishingCourseAvailability.getGrade() != null) {
			grade = fishingCourseAvailability.getGrade();
		}
		if (fishingCourseAvailability.getBedCapacity() != 0) {
			bed = fishingCourseAvailability.getBedCapacity();
		}
		if (fishingCourseAvailability.getFishingTrainer() != 0) {
			fishingTrainerId = fishingCourseAvailability.getFishingTrainer();
		}
		Boolean isLocationSortDisabled = true;

		List<Sort.Order> sorts = new ArrayList<>();
		if (fishingCourseAvailability.getSortBy() != null && fishingCourseAvailability.getSortBy().size() != 0) {

			FishingCourseDTO dto;
			for (SortType sortType : fishingCourseAvailability.getSortBy()) {
				if (sortType != null && sortType.getField().equals("price_per_hour")) {
					sortType.setField("price");
				}
				if (sortType != null && sortType.getField().equals("latitude")) {
					isLocationSortDisabled = false;
					sortType.setField("a." + sortType.getField());
					if (sortType.getDirection().toLowerCase().contains("desc")) {
						sorts.add(new Sort.Order(Sort.Direction.DESC, "a.longitude"));
					} else {
						sorts.add(new Sort.Order(Sort.Direction.ASC, "a.longitude"));
					}
				}
				if (sortType != null && sortType.getDirection().toLowerCase().contains("desc")) {
					sorts.add(new Sort.Order(Sort.Direction.DESC, sortType.getField()));
				} else if (sortType != null && sortType.getDirection().toLowerCase().contains("asc")) {
					sorts.add(new Sort.Order(Sort.Direction.ASC, sortType.getField()));
				}
			}
		}

		Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sorts));
		List<FishingCourse> availableFishingCourses;
		List<FishingCourseDTO> availableFishingCoursesWithPrice;

		if (fishingCourseAvailability.getDateSpan() == null
				|| fishingCourseAvailability.getDateSpan().getStartDate() == null
				|| fishingCourseAvailability.getDateSpan().getEndDate() == null) {
			return searchFishingCourse(name, grade, bed, fishingTrainerId, isLocationSortDisabled, paging);
		} else {
			return checkAvailabilty(fishingCourseAvailability, name, grade, bed, fishingTrainerId,
					isLocationSortDisabled, paging);
		}

	}

	private Page<FishingCourseDTO> checkAvailabilty(FishingCourseAvailabilityDTO fishingCourseAvailability, String name,
			Double grade, int bed, Long fishingTrainerId, Boolean isLocationSortDisabled, Pageable paging) {
		int hours;
		List<FishingCourse> availableFishingCourses;
		List<FishingCourseDTO> availableFishingCoursesWithPrice;
		LocalDateTime start = fishingCourseAvailability.getDateSpan().getStartDate();
		LocalDateTime end = fishingCourseAvailability.getDateSpan().getEndDate();
		hours = (int) ChronoUnit.HOURS.between(start, end);
		Page<FishingCourse> pageFishingCourse;
		if (isLocationSortDisabled) {
			pageFishingCourse = courseRepository.getAvailability(start, end, name, grade, bed, fishingTrainerId,
					paging);
		} else {
			pageFishingCourse = courseRepository.getAvailabilityWithSortLocation(start, end, name, grade, bed,
					fishingTrainerId, paging);
		}
		availableFishingCourses = pageFishingCourse.getContent();
		availableFishingCoursesWithPrice = new ArrayList<FishingCourseDTO>();
		if (availableFishingCourses.size() != 0) {
			FishingCourseDTO dto;
			for (FishingCourse p : availableFishingCourses) {
				dto = FishingCourseMapper.FishingCourseToFishingCourseDTOWithPrice(p, hours);
				availableFishingCoursesWithPrice.add(dto);
			}
		}

		Page<FishingCourseDTO> pc = new PageImpl(availableFishingCoursesWithPrice, paging,
				pageFishingCourse.getTotalElements());
		return pc;
	}

	private Page<FishingCourseDTO> searchFishingCourse(String name, Double grade, int bed, Long fishingTrainerId,
			Boolean isLocationSortDisabled, Pageable paging) {
		List<FishingCourse> availableFishingCourses;
		Page<FishingCourse> pageFishingCourse;
		if (isLocationSortDisabled) {
			pageFishingCourse = courseRepository.searchFishingCourse(name, grade, bed, fishingTrainerId, paging);
		} else {
			pageFishingCourse = courseRepository.searchFishingCourseWithSortLocation(name, grade, bed, fishingTrainerId,
					paging);
		}
		availableFishingCourses = pageFishingCourse.getContent();
		return new PageImpl(availableFishingCourses, paging, pageFishingCourse.getTotalElements());
	}

	@Override
	public ReservationCountDTO getFishingCourseReservationCountYearly(long id) {
		int[] count = new int[4];
		ReservationCount reservationCount = new ReservationCount();
		FishingCourse fishingCourse = courseRepository.findById(id).get();
		Set<FishingReservation> reservations = fishingCourse.getFishingReservation();
		for (FishingReservation reservation : reservations) {
			for (int i = 1; i <= 4; i++) {
				count = statisticsService.countYearly(reservation.getDuration(), i, count);
			}

		}
		reservationCount.setYearlySum(count);
		return ReservationCountMapper.ReservationCountToReservationCountDTO(reservationCount);
	}

	@Override
	public ReservationCountDTO getFishingCourseReservationCountMonthly(long id) {
		int[] count = new int[12];
		ReservationCount reservationCount = new ReservationCount();
		FishingCourse fishingCourse = courseRepository.findById(id).get();
		Set<FishingReservation> reservations = fishingCourse.getFishingReservation();
		for (FishingReservation reservation : reservations) {
			for (int i = 1; i <= 12; i++) {
				count = statisticsService.countMonthly(reservation.getDuration(), i, count);
			}

		}
		reservationCount.setMonthlySum(count);
		return ReservationCountMapper.ReservationCountToReservationCountDTO(reservationCount);
	}

	@Override
	public ReservationCountDTO getFishingCourseReservationCountWeekly(long id) {
		int[] count = new int[4];
		ReservationCount reservationCount = new ReservationCount();
		FishingCourse fishingCourse = courseRepository.findById(id).get();
		Set<FishingReservation> reservations = fishingCourse.getFishingReservation();
		for (FishingReservation reservation : reservations) {
			for (int i = 1; i <= 4; i++) {
				count = statisticsService.countWeekly(reservation.getDuration(), i, count);
			}

		}
		reservationCount.setWeeklySum(count);
		return ReservationCountMapper.ReservationCountToReservationCountDTO(reservationCount);
	}

	@Override
	public IncomeDTO getFishingCourseIncomeYearly(DateTimeSpan duration, long id) {
		long yearCount = duration.getYears() + 1;
		double[] incomeSum = new double[(int) yearCount];
		Income income = new Income();
		FishingCourse fishingCourse = courseRepository.findById(id).get();
		Set<FishingReservation> reservations = fishingCourse.getFishingReservation();
		for (FishingReservation reservation : reservations) {
			for (int i = 1; i <= yearCount; i++) {
				incomeSum = statisticsService.yearlyIncome(reservation.getDuration(), reservation.getOwnerIncome(), i,
						incomeSum, duration.getStartDate().getYear());
			}

		}
		income.setYearlySum(incomeSum);
		return IncomeMapper.IncomeToIncomeDTO(income);
	}

	@Override
	public IncomeDTO getFishingCourseIncomeMonthly(DateTimeSpan duration, long id) {
		long yearCount = duration.getYears() + 1;
		double[][] incomeSum = new double[(int) yearCount][12];
		Income income = new Income();
		FishingCourse fishingCourse = courseRepository.findById(id).get();
		Set<FishingReservation> reservations = fishingCourse.getFishingReservation();
		for (FishingReservation reservation : reservations) {
			for (int i = 1; i <= yearCount; i++) {
				for (int j = 1; j <= 12; j++) {
					if (duration.isBetween(reservation.getDuration().getEndDate())) {
						incomeSum = statisticsService.monthlyIncome(reservation.getDuration(),
								reservation.getOwnerIncome(), i, j, incomeSum, duration.getStartDate().getYear());
					}
				}
			}

		}
		income.setMonthlySum(incomeSum);
		return IncomeMapper.IncomeToIncomeDTO(income);
	}

	@Override
	public IncomeDTO getFishingCourseIncomeDaily(DateTimeSpan duration, long id) {
		long yearCount = duration.getYears() + 1;
		double[][][] incomeSum = new double[(int) yearCount][12][31];
		Income income = new Income();
		FishingCourse fishingCourse = courseRepository.findById(id).get();
		Set<FishingReservation> reservations = fishingCourse.getFishingReservation();
		for (FishingReservation reservation : reservations) {
			for (int i = 1; i <= yearCount; i++) {
				for (int j = 1; j <= 12; j++) {
					for (int k = 1; k <= 31; k++) {
						if (duration.isBetween(reservation.getDuration().getEndDate())) {
							incomeSum = statisticsService.dailyIncome(reservation.getDuration(),
									reservation.getOwnerIncome(), i, j, k, incomeSum,
									duration.getStartDate().getYear());
						}
					}
				}
			}

		}
		income.setDailySum(incomeSum);
		return IncomeMapper.IncomeToIncomeDTO(income);
	}

	@Override
	@Transactional
	public FishingCourseDTO addGrade(GradeDTO gradeDTO, long fishingId) {
		gradeDTO.setIsAccepted(RequestStatus.Waiting);
		FishingCourse fishing = courseRepository.findById(fishingId).get();
		for (Grade grade : fishing.getGrades()) {
			if (grade.getUser().getId() == gradeDTO.getUser().getId()) {
				fishing.getGrades().remove(grade);
				break;
			}
		}
		fishing.getGrades().add(GradeMapper.GradeDTOToGrade(gradeDTO));
		courseRepository.save(fishing);
		return FishingCourseMapper.FishingCourseToDTO(fishing);
	}
	
	@Override
	public FishingCourseDTO subscribe(Long id, Long customerId) {
		FishingCourse fishingCourse = courseRepository.findById(id).get();
		Customer customer = CustomerMapper.customerDTOtoCustomer(customerService.getCustomer(customerId));
		Set<Customer> customers = fishingCourse.getSubscribers();
		customers.add(customer);
		fishingCourse.setSubscribers(customers);
		courseRepository.save(fishingCourse);
		return FishingCourseMapper.FishingCourseToDTO(fishingCourse);
	}
	
	@Override
	public FishingCourseDTO unsubscribe(Long id, Long customerId) {
		FishingCourse fishingCourse = courseRepository.findById(id).get();
		Customer customer = CustomerMapper.customerDTOtoCustomer(customerService.getCustomer(customerId));
		Set<Customer> customers = fishingCourse.getSubscribers();
		customers = customers.stream()
				  .filter(e -> e.getId()!= customerId)
				  .collect(Collectors.toSet());
		fishingCourse.setSubscribers(customers);
		courseRepository.save(fishingCourse);
		return FishingCourseMapper.FishingCourseToDTO(fishingCourse);
	}
	
	
	@Override
	public Page<FishingCourseDTO> findAllFishingSubscriptionByCustomer(Long customerId, Pageable pageable){
		return FishingCourseMapper.pageFishingCourseToPageFishingCourseDTO(courseRepository.subscriptionsFishing(customerId, pageable));
	}
	

	@Override
	public Page<FishingCourseDTO> findByName(String name, Pageable paging) {
		return FishingCourseMapper.pageFishingCourseToPageFishingCourseDTO(courseRepository.findByNameContains(name, paging));
	}


}
