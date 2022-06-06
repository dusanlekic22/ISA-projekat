package isaproject.service.impl.boat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.BoatAvailabilityDTO;
import isaproject.dto.DateSpanDTO;
import isaproject.dto.GradeDTO;
import isaproject.dto.IncomeDTO;
import isaproject.dto.ReservationCountDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.boat.BoatDTO;
import isaproject.dto.boat.BoatQuickReservationDTO;
import isaproject.dto.boat.BoatReservationDTO;
import isaproject.dto.cottage.CottageDTO;
import isaproject.mapper.CottageMapper;
import isaproject.mapper.CustomerMapper;
import isaproject.mapper.DateSpanMapper;
import isaproject.mapper.GradeMapper;
import isaproject.mapper.IncomeMapper;
import isaproject.mapper.ReservationCountMapper;
import isaproject.mapper.SortTypeMapper;
import isaproject.mapper.boat.BoatMapper;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.model.Grade;
import isaproject.model.Income;
import isaproject.model.ReservationCount;
import isaproject.model.SortType;
import isaproject.model.boat.Boat;
import isaproject.model.boat.BoatReservation;
import isaproject.model.cottage.Cottage;
import isaproject.repository.AddressRepository;
import isaproject.repository.boat.BoatRepository;
import isaproject.service.CustomerService;
import isaproject.service.StatisticsService;
import isaproject.service.boat.BoatQuickReservationService;
import isaproject.service.boat.BoatReservationService;
import isaproject.service.boat.BoatService;

@Service
public class BoatServiceImpl implements BoatService {

	private BoatRepository boatRepository;
	private BoatReservationService boatReservationService;
	private BoatQuickReservationService boatQuickReservationService;
	private StatisticsService statisticsService;
	private CustomerService customerService;

	@Autowired
	public BoatServiceImpl(BoatRepository boatRepository, AddressRepository addressRepository,
			BoatReservationService boatReservationService,
			BoatQuickReservationService boatQuickReservationService,
			StatisticsService statisticsService,CustomerService customerService) {
		super();
		this.boatRepository = boatRepository;
		this.boatReservationService = boatReservationService;
		this.boatQuickReservationService = boatQuickReservationService;
		this.statisticsService = statisticsService;
		this.customerService = customerService;
	}

	public BoatDTO findById(Long id) {
		Boat boat = boatRepository.findById(id).orElse(null);
		return BoatMapper.BoatToBoatDTO(boat);
	}

	public Set<BoatDTO> findAll() {
		Set<Boat> boats = new HashSet<>(boatRepository.findAll());
		Set<BoatDTO> dtos = new HashSet<>();
		if (boats.size() != 0) {
			BoatDTO dto;
			for (Boat p : boats) {
				dto = BoatMapper.BoatToBoatDTO(p);
				dtos.add(dto);
			}
		}
		return dtos;
	}
	
	public Page<BoatDTO> findAllPagination(List<SortTypeDTO> sortTypesDTO, Pageable pageable) {
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
		
		return BoatMapper.pageBoatToPageBoatDTO(boatRepository.findAll(paging));
		}else {
			return BoatMapper.pageBoatToPageBoatDTO(boatRepository.findAll(pageable));
		}
		}

	@Transactional
	@Override
	public BoatDTO deleteById(Long id) {
		BoatDTO boatDTO = findById(id);
		if (boatReservationService.findAllActiveByBoatId(id).isEmpty()) {
			boatRepository.deleteById(id);
			return boatDTO;
		}
		return null;
	}

	@Transactional
	@Override
	public BoatDTO save(BoatDTO boatDTO) {
		Boat boat = BoatMapper.BoatDTOToBoat(boatDTO);
		return BoatMapper.BoatToBoatDTO(boatRepository.save(boat));
	}

	@Transactional
	@Override
	public BoatDTO update(BoatDTO boatDTO) {
		Boat boat = BoatMapper.BoatDTOToBoat(boatDTO);
		return BoatMapper.BoatToBoatDTO(boatRepository.save(boat));
	}

	@Transactional
	@Override
	public BoatDTO updateAvailableTerms(Long id, DateTimeSpan newDateSpan) {
		Boat boat = boatRepository.findById(id).get();
		for (BoatReservationDTO boatReservation : boatReservationService.findByBoatId(id)) {
			if (newDateSpan.overlapsWith(boatReservation.getDuration()))
				return null;
		}
		for (BoatQuickReservationDTO boatQuickReservation : boatQuickReservationService.findByBoatId(id)) {
			if (newDateSpan.overlapsWith(boatQuickReservation.getDuration()))
				return null;
		}
		boolean overlapped = false;
		Set<DateTimeSpan> availaDateSpans = new HashSet<>(boat.getAvailableReservationDateSpan());
		for (DateTimeSpan reservationSpan : availaDateSpans) {
			if (reservationSpan.overlapsWith(newDateSpan)) {
				boat.getAvailableReservationDateSpan().remove(reservationSpan);
				if (newDateSpan.getStartDate().compareTo(reservationSpan.getStartDate()) <= 0) {
					reservationSpan = new DateTimeSpan(newDateSpan.getStartDate(), reservationSpan.getEndDate());
				}
				if (newDateSpan.getEndDate().compareTo(reservationSpan.getEndDate()) >= 0) {
					reservationSpan = new DateTimeSpan(reservationSpan.getStartDate(), newDateSpan.getEndDate());
				}
				overlapped = true;
				boat.getAvailableReservationDateSpan().add(reservationSpan);
			}
		}
		if (!overlapped)
			boat.getAvailableReservationDateSpan().add(newDateSpan);
		
		for (DateTimeSpan dateTimeSpan : boat.getUnavailableReservationDateSpan()) {
			if (newDateSpan.overlapsWith(dateTimeSpan)) {
				reserveUnavailableDateSpan(boat, newDateSpan, dateTimeSpan);
				break;
			}
		}

		return BoatMapper.BoatToBoatDTO(boatRepository.save(boat));
	}
	
	private void reserveUnavailableDateSpan(Boat boat, 
			DateTimeSpan availableDateSpan, DateTimeSpan unavailableDateSpan) {
		boat.getUnavailableReservationDateSpan().remove(unavailableDateSpan);
		if (availableDateSpan.getStartDate().compareTo(unavailableDateSpan.getStartDate()) <= 0
				&& availableDateSpan.getEndDate().compareTo(unavailableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(availableDateSpan.getEndDate(),
					unavailableDateSpan.getEndDate());
			boat.getUnavailableReservationDateSpan().add(newDateSpan);
		} else if (availableDateSpan.getStartDate().compareTo(unavailableDateSpan.getStartDate()) >= 0
				&& availableDateSpan.getEndDate().compareTo(unavailableDateSpan.getEndDate()) >= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(unavailableDateSpan.getStartDate(),
					availableDateSpan.getStartDate());

			boat.getUnavailableReservationDateSpan().add(newDateSpan);
		} else if (availableDateSpan.getStartDate().compareTo(unavailableDateSpan.getStartDate()) >= 0
				&& availableDateSpan.getEndDate().compareTo(unavailableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan1 = new DateTimeSpan(unavailableDateSpan.getStartDate(),
					availableDateSpan.getStartDate());
			DateTimeSpan newDateSpan2 = new DateTimeSpan(availableDateSpan.getEndDate(),
					unavailableDateSpan.getEndDate());

			boat.getUnavailableReservationDateSpan().add(newDateSpan1);
			boat.getUnavailableReservationDateSpan().add(newDateSpan2);
		}
		boatRepository.save(boat);
	}

	@Transactional
	@Override
	public BoatDTO updateUnavailableTerms(Long id, DateTimeSpan newDateSpan) {
		Boat boat = boatRepository.findById(id).get();
		for (BoatReservationDTO boatReservation : boatReservationService.findByBoatId(id)) {
			if (newDateSpan.overlapsWith(boatReservation.getDuration()))
				return null;
		}
		for (BoatQuickReservationDTO boatQuickReservation : boatQuickReservationService.findByBoatId(id)) {
			if (newDateSpan.overlapsWith(boatQuickReservation.getDuration()))
				return null;
		}
		boolean overlapped = false;
		Set<DateTimeSpan> unavailableDateSpans = new HashSet<>(boat.getUnavailableReservationDateSpan());
		for (DateTimeSpan reservationSpan : unavailableDateSpans) {
			if (reservationSpan.overlapsWith(newDateSpan)) {
				boat.getUnavailableReservationDateSpan().remove(reservationSpan);
				if (newDateSpan.getStartDate().compareTo(reservationSpan.getStartDate()) <= 0) {
					reservationSpan = new DateTimeSpan(newDateSpan.getStartDate(), reservationSpan.getEndDate());
				}
				if (newDateSpan.getEndDate().compareTo(reservationSpan.getEndDate()) >= 0) {
					reservationSpan = new DateTimeSpan(reservationSpan.getStartDate(), newDateSpan.getEndDate());
				}
				overlapped = true;
				boat.getUnavailableReservationDateSpan().add(reservationSpan);
			}
		}
		if (!overlapped)
			boat.getUnavailableReservationDateSpan().add(newDateSpan);

		for (DateTimeSpan dateTimeSpan : boat.getAvailableReservationDateSpan()) {
			if (newDateSpan.overlapsWith(dateTimeSpan)) {
				reserveAvailableDateSpan(boat, newDateSpan, dateTimeSpan);
				break;
			}
		}

		return BoatMapper.BoatToBoatDTO(boatRepository.save(boat));
	}
	
	private void reserveAvailableDateSpan(Boat boat, DateTimeSpan unavailableDateSpan,
			DateTimeSpan availableDateSpan) {
		boat.getAvailableReservationDateSpan().remove(availableDateSpan);
		if (unavailableDateSpan.getStartDate().compareTo(availableDateSpan.getStartDate()) <= 0
				&& unavailableDateSpan.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(unavailableDateSpan.getEndDate(),
					availableDateSpan.getEndDate());
			boat.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (unavailableDateSpan.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& unavailableDateSpan.getEndDate().compareTo(availableDateSpan.getEndDate()) >= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(availableDateSpan.getStartDate(),
					unavailableDateSpan.getStartDate());

			boat.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (unavailableDateSpan.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& unavailableDateSpan.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan1 = new DateTimeSpan(availableDateSpan.getStartDate(),
					unavailableDateSpan.getStartDate());
			DateTimeSpan newDateSpan2 = new DateTimeSpan(unavailableDateSpan.getEndDate(),
					availableDateSpan.getEndDate());

			boat.getAvailableReservationDateSpan().add(newDateSpan1);
			boat.getAvailableReservationDateSpan().add(newDateSpan2);
		}
		boatRepository.save(boat);
	}
	

	@Transactional
	@Override
	public BoatDTO updateInfo(BoatDTO boatDTO) {
		Boat boat = BoatMapper.BoatDTOToBoat(boatDTO);
		if (boatReservationService.findAllActiveByBoatId(boat.getId()).isEmpty()) {
			return BoatMapper.BoatToBoatDTO(boatRepository.save(boat));
		}
		return null;
	}

	@Override
	public Set<BoatDTO> findByBoatName(String name) {
		Set<Boat> boats = new HashSet<>(boatRepository.findByName(name));
		Set<BoatDTO> dtos = new HashSet<>();
		if (boats.size() != 0) {

			BoatDTO dto;
			for (Boat p : boats) {
				dto = BoatMapper.BoatToBoatDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

	@Override
	public Set<BoatDTO> findByBoatOwnerId(Long id) {
		Set<Boat> boats = new HashSet<>(boatRepository.findByBoatOwnerId(id));
		Set<BoatDTO> dtos = new HashSet<>();
		if (boats.size() != 0) {

			BoatDTO dto;
			for (Boat p : boats) {
				dto = BoatMapper.BoatToBoatDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

	@Override
	public Set<BoatDTO> findByReservationDate(DateSpanDTO reservationDateDTO) {
		DateTimeSpan reservationDate = DateSpanMapper.dateSpanDTOtoDateSpan(reservationDateDTO);
		Set<Boat> boats = new HashSet<>(boatRepository.findAll());
		Set<BoatDTO> availableBoats = new HashSet<>();
		if (boats.size() != 0) {

			BoatDTO dto;
			for (Boat p : boats) {
				for (DateTimeSpan d : p.getAvailableReservationDateSpan()) {
					if (d.isTimeSpanBetween(reservationDate)) {
						dto = BoatMapper.BoatToBoatDTO(p);
						availableBoats.add(dto);
					}
				}
			}
		}

		return availableBoats;
	}

	@Override
	public ReservationCountDTO getBoatReservationCountYearly(long id) {
		int[] count = new int[4];
		ReservationCount reservationCount = new ReservationCount();
		Boat boat = boatRepository.findById(id).get();
		Set<BoatReservation> reservations = boat.getBoatReservation();
        for (BoatReservation reservation : reservations)
        {
            for (int i = 1; i <= 4; i++) {
                count = statisticsService.countYearly(reservation.getDuration(), i, count);
            }
           
        }
        reservationCount.setYearlySum(count); 
        return ReservationCountMapper.ReservationCountToReservationCountDTO(reservationCount);
	}

	@Override
	public ReservationCountDTO getBoatReservationCountMonthly(long id) {
		int[] count = new int[12];
		ReservationCount reservationCount = new ReservationCount();
		Boat boat = boatRepository.findById(id).get();
		Set<BoatReservation> reservations = boat.getBoatReservation();
        for (BoatReservation reservation : reservations)
        {
            for (int i = 1; i <= 12; i++) {
                count = statisticsService.countMonthly(reservation.getDuration(), i, count);
            }
           
        }
        reservationCount.setMonthlySum(count); 
        return ReservationCountMapper.ReservationCountToReservationCountDTO(reservationCount);
	}

	@Override
	public ReservationCountDTO getBoatReservationCountWeekly(long id) {
		int[] count = new int[4];
		ReservationCount reservationCount = new ReservationCount();
		Boat boat = boatRepository.findById(id).get();
		Set<BoatReservation> reservations = boat.getBoatReservation();
        for (BoatReservation reservation : reservations)
        {
            for (int i = 1; i <= 4; i++) {
                count = statisticsService.countWeekly(reservation.getDuration(), i, count);
            }
           
        }
        reservationCount.setWeeklySum(count); 
        return ReservationCountMapper.ReservationCountToReservationCountDTO(reservationCount);
	}
	
	@Override
	public IncomeDTO getBoatIncomeYearly(DateTimeSpan duration, long id) {
		long yearCount = duration.getYears() + 1;
		double[] incomeSum = new double[(int) yearCount];
		Income income = new Income();
		Boat boat = boatRepository.findById(id).get();
		Set<BoatReservation> reservations = boat.getBoatReservation();
		for (BoatReservation reservation : reservations) {
			for (int i = 1; i <= yearCount; i++) {
				incomeSum = statisticsService.yearlyIncome(reservation.getDuration(), reservation.getOwnerIncome(), i,
						incomeSum, duration.getStartDate().getYear());
			}

		}
		income.setYearlySum(incomeSum);
		return IncomeMapper.IncomeToIncomeDTO(income);
	}

	@Override
	public IncomeDTO getBoatIncomeMonthly(DateTimeSpan duration, long id) {
		long yearCount = duration.getYears() + 1;
		double[][] incomeSum = new double[(int) yearCount][12];
		Income income = new Income();
		Boat boat = boatRepository.findById(id).get();
		Set<BoatReservation> reservations = boat.getBoatReservation();
		for (BoatReservation reservation : reservations) {
			for (int i = 1; i <= yearCount; i++) {
				for (int j = 1; j <= 12; j++) {
					if (duration.isBetween(reservation.getDuration().getEndDate())) {
						incomeSum = statisticsService.monthlyIncome(reservation.getDuration(), reservation.getOwnerIncome(),
								i, j, incomeSum, duration.getStartDate().getYear());
					}
				}
			}

		}
		income.setMonthlySum(incomeSum);
		return IncomeMapper.IncomeToIncomeDTO(income);
	}

	@Override
	public IncomeDTO getBoatIncomeDaily(DateTimeSpan duration, long id) {
		long yearCount = duration.getYears() + 1;
		double[][][] incomeSum = new double[(int) yearCount][12][31];
		Income income = new Income();
		Boat boat = boatRepository.findById(id).get();
		Set<BoatReservation> reservations = boat.getBoatReservation();
		for (BoatReservation reservation : reservations) {
			for (int i = 1; i <= yearCount; i++) {
				for (int j = 1; j <= 12; j++) {
					for (int k = 1; k <= 31; k++) {
						if (duration.isBetween(reservation.getDuration().getEndDate())) {
							incomeSum = statisticsService.dailyIncome(reservation.getDuration(), reservation.getOwnerIncome(),
									i, j, k, incomeSum, duration.getStartDate().getYear());
						}
					}
				}
			}

		}
		income.setDailySum(incomeSum);
		return IncomeMapper.IncomeToIncomeDTO(income);
		
	}
	public Page<BoatDTO> findByAvailability(BoatAvailabilityDTO boatAvailability, Pageable pageable) {

		int hours = 0;

		String name = "%";
		Double grade = -1.0;
		int bed = 0;
		if (boatAvailability.getName() != null) {
			name = name + boatAvailability.getName().toLowerCase().concat("%");
		}
		if (boatAvailability.getGrade() != null) {
			grade = boatAvailability.getGrade();
		}
		if (boatAvailability.getBedCapacity() != 0) {
			bed = boatAvailability.getBedCapacity();
		}
		Boolean isLocationSortDisabled = true;
		
		List<Sort.Order> sorts = new ArrayList<>();
		if (boatAvailability.getSortBy() != null && boatAvailability.getSortBy().size() != 0) {

			BoatDTO dto;
			for (SortType sortType : boatAvailability.getSortBy()) {
				if(sortType != null && sortType.getField().equals("latitude") ) {
					isLocationSortDisabled = false;
					sortType.setField("a."+sortType.getField());
					if(sortType.getDirection().toLowerCase().contains("desc")){
					sorts.add(new Sort.Order(Sort.Direction.DESC, "a.longitude"));}
					else {
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
		List<Boat> availableBoats;
		List<BoatDTO> availableBoatsWithPrice;

		if (boatAvailability.getDateSpan() == null || boatAvailability.getDateSpan().getStartDate() == null
				|| boatAvailability.getDateSpan().getEndDate() == null) {
			return searchBoat(name, grade, bed, isLocationSortDisabled, paging);
		} else {
			return checkAvailabilty(boatAvailability, name, grade, bed, isLocationSortDisabled, paging);
		}

	}

	private Page<BoatDTO> checkAvailabilty(BoatAvailabilityDTO boatAvailability, String name, Double grade,
			int bed, Boolean isLocationSortDisabled, Pageable paging) {
		int hours;
		List<Boat> availableBoats;
		List<BoatDTO> availableBoatsWithPrice;
		LocalDateTime start = boatAvailability.getDateSpan().getStartDate();
		LocalDateTime end = boatAvailability.getDateSpan().getEndDate();
		hours = (int) ChronoUnit.HOURS.between(start, end);
		Page<Boat> pageBoat;
		if(isLocationSortDisabled) {
		pageBoat = boatRepository.getAvailability(start, end, name, grade, bed, paging);
		}else {
			pageBoat = boatRepository.getAvailabilityWithSortLocation(start, end, name, grade, bed, paging);
		}
		availableBoats = pageBoat.getContent();
		availableBoatsWithPrice = new ArrayList<BoatDTO>();
		if (availableBoats.size() != 0) {
			BoatDTO dto;
			for (Boat p : availableBoats) {
				dto = BoatMapper.BoatToBoatDTOWithPrice(p, hours);
				availableBoatsWithPrice.add(dto);
			}
		}

		Page<BoatDTO> pc = new PageImpl(availableBoatsWithPrice, paging, pageBoat.getTotalElements());
		return pc;
	}

	private Page<BoatDTO> searchBoat(String name, Double grade, int bed, Boolean isLocationSortDisabled,
			Pageable paging) {
		List<Boat> availableBoats;
		Page<Boat> pageBoat;
		if(isLocationSortDisabled) {
		pageBoat = boatRepository.searchBoat(name, grade, bed, paging);
		}else {
		pageBoat = boatRepository.searchBoatWithSortLocation(name, grade, bed, paging);
		}
		availableBoats = pageBoat.getContent();
		return new PageImpl(availableBoats, paging, pageBoat.getTotalElements());
	}
	
	@Override
	public BoatDTO addGrade(GradeDTO gradeDTO,long boatId) {
		Boat boat = boatRepository.findById(boatId).get();
		for(Grade grade: boat.getGrades()) {
			if(grade.getUser().getId() == gradeDTO.getUser().getId()) {
				boat.getGrades().remove(grade);
				break;
			}
		}
		boat.addGrade(GradeMapper.GradeDTOToGrade(gradeDTO));
		boatRepository.save(boat);
		return BoatMapper.BoatToBoatDTO(boat);
	}
	
	@Override
	public BoatDTO subscribe(Long id, Long customerId) {
		Boat boat = boatRepository.findById(id).get();
		Customer customer = CustomerMapper.customerDTOtoCustomer(customerService.getCustomer(customerId));
		Set<Customer> customers = boat.getSubscribers();
		customers.add(customer);
		boat.setSubscribers(customers);
		boatRepository.save(boat);
		return BoatMapper.BoatToBoatDTO(boat);
	}
	
	@Override
	public BoatDTO unsubscribe(Long id, Long customerId){
		Boat boat = boatRepository.findById(id).get();
		Customer customer = CustomerMapper.customerDTOtoCustomer(this.customerService.getCustomer(customerId));
		Set<Customer> customers = boat.getSubscribers();
		customers = customers.stream()
				  .filter(e -> e.getId()!= customerId)
				  .collect(Collectors.toSet());
		boat.setSubscribers(customers);
		boatRepository.save(boat);
		return BoatMapper.BoatToBoatDTO(boat);
	}
	

}
