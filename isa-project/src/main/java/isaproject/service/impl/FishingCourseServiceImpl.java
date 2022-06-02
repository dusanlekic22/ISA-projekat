package isaproject.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import isaproject.dto.FishingCourseAvailabilityDTO;
import isaproject.dto.FishingCourseDTO;
import isaproject.dto.IncomeDTO;
import isaproject.dto.ReservationCountDTO;
import isaproject.mapper.IncomeMapper;
import isaproject.mapper.ReservationCountMapper;
import isaproject.model.DateTimeSpan;
import isaproject.model.FishingCourse;
import isaproject.model.FishingReservation;
import isaproject.model.Income;
import isaproject.model.ReservationCount;
import isaproject.dto.SortTypeDTO;
import isaproject.exception.ReservedServiceException;
import isaproject.mapper.FishingCourseMapper;
import isaproject.mapper.SortTypeMapper;
import isaproject.mapper.boat.BoatMapper;
import isaproject.model.SortType;
import isaproject.repository.AddressRepository;
import isaproject.repository.FishingCourseRepository;
import isaproject.service.FishingCourseService;
import isaproject.service.FishingReservationService;
import isaproject.service.StatisticsService;

@Service
public class FishingCourseServiceImpl implements FishingCourseService {

	private FishingCourseRepository courseRepository;
	private AddressRepository addressRepository;
	private FishingReservationService fishingReservationService;
	private StatisticsService statisticsService;

	@Autowired
	public FishingCourseServiceImpl(FishingCourseRepository courseRepository, AddressRepository addressRepository,
			FishingReservationService fishingReservationService,StatisticsService statisticsService) {
		super();
		this.courseRepository = courseRepository;
		this.addressRepository = addressRepository;
		this.fishingReservationService = fishingReservationService;
		this.statisticsService = statisticsService;
	}

	@Override
	public Set<FishingCourseDTO> findAll() {
		Set<FishingCourse> courses = new HashSet<FishingCourse>(courseRepository.findAll());
		Set<FishingCourseDTO> courseDTOs = new HashSet<FishingCourseDTO>();
		for (FishingCourse fishingCourse : courses) {
			courseDTOs.add(FishingCourseMapper.FishingCourseToDTO(fishingCourse));
		}
		return courseDTOs;
	}

	@Override
	public FishingCourseDTO findById(Long id) {
		FishingCourse course = courseRepository.findById(id).orElse(null);
		return FishingCourseMapper.FishingCourseToDTO(course);
	}

	@Transactional
	@Override
	public FishingCourseDTO save(FishingCourseDTO fishingCourseDTO) {
		FishingCourse course = FishingCourseMapper.DTOToFishingCourse(fishingCourseDTO);
		addressRepository.save(fishingCourseDTO.getAddress());
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
		FishingCourseDTO courseDTO = findById(id);
		if (isReserved(courseDTO.getId())) {
			throw new ReservedServiceException(
					String.format("Fishing course '%s' is reserved and can't be deleted or changed.", id));
		}
		courseRepository.deleteById(courseDTO.getId());
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
	public Page<FishingCourseDTO> findAllPagination(List<SortTypeDTO> sortTypesDTO,Pageable pageable ){
		List<Sort.Order> sorts = new ArrayList<>();
		List<SortType> sortTypes = sortTypesDTO.stream().map(sortTypeDTO -> SortTypeMapper.SortTypeDTOToSortType(sortTypeDTO)).collect(Collectors.toList());
		if(sortTypes !=null) {
			for (SortType sortType : sortTypes) {
				if (sortType != null && sortType.getDirection().toLowerCase().contains("desc")) {
					sorts.add(new Sort.Order(Sort.Direction.DESC, sortType.getField()));
				} else if (sortType != null && sortType.getDirection().toLowerCase().contains("asc")) {
					sorts.add(new Sort.Order(Sort.Direction.ASC, sortType.getField()));
				}
			}

		Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sorts));
		
		return FishingCourseMapper.pageFishingCourseToPageFishingCourseDTO(courseRepository.findAll(paging));
		}else {
			return FishingCourseMapper.pageFishingCourseToPageFishingCourseDTO(courseRepository.findAll(pageable));
		}
	}

	@Override
	public Page<FishingCourseDTO> findByAvailability(FishingCourseAvailabilityDTO fishingCourseAvailability, Pageable pageable){
		return null;
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
		System.out.println(reservations);
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
		int[] incomeSum = new int[(int) yearCount];
		Income income = new Income();
		FishingCourse fishingCourse = courseRepository.findById(id).get();
		Set<FishingReservation> reservations = fishingCourse.getFishingReservation();
		for (FishingReservation reservation : reservations) {
			for (int i = 1; i <= yearCount; i++) {
				incomeSum = statisticsService.yearlyIncome(reservation.getDuration(), reservation.getPrice().intValue(), i,
						incomeSum, duration.getStartDate().getYear());
			}

		}
		income.setYearlySum(incomeSum);
		return IncomeMapper.IncomeToIncomeDTO(income);
	}

	@Override
	public IncomeDTO getFishingCourseIncomeMonthly(DateTimeSpan duration, long id) {
		long yearCount = duration.getYears() + 1;
		int[][] incomeSum = new int[(int) yearCount][12];
		Income income = new Income();
		FishingCourse fishingCourse = courseRepository.findById(id).get();
		Set<FishingReservation> reservations = fishingCourse.getFishingReservation();
		for (FishingReservation reservation : reservations) {
			for (int i = 1; i <= yearCount; i++) {
				for (int j = 1; j <= 12; j++) {
					if (duration.isBetween(reservation.getDuration().getEndDate())) {
						incomeSum = statisticsService.monthlyIncome(reservation.getDuration(),reservation.getPrice().intValue(),
								i, j, incomeSum, duration.getStartDate().getYear());
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
		int[][][] incomeSum = new int[(int) yearCount][12][31];
		Income income = new Income();
		FishingCourse fishingCourse = courseRepository.findById(id).get();
		Set<FishingReservation> reservations = fishingCourse.getFishingReservation();
		for (FishingReservation reservation : reservations) {
			for (int i = 1; i <= yearCount; i++) {
				for (int j = 1; j <= 12; j++) {
					for (int k = 1; k <= 31; k++) {
						if (duration.isBetween(reservation.getDuration().getEndDate())) {
							incomeSum = statisticsService.dailyIncome(reservation.getDuration(), reservation.getPrice().intValue(),
									i, j, k, incomeSum, duration.getStartDate().getYear());
						}
					}
				}
			}

		}
		income.setDailySum(incomeSum);
		return IncomeMapper.IncomeToIncomeDTO(income);
	}

}
