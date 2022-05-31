package isaproject.service.impl.cottage;

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

import isaproject.dto.CottageAvailabilityDTO;
import isaproject.dto.DateSpanDTO;
import isaproject.dto.ReservationCountDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.cottage.CottageDTO;
import isaproject.dto.cottage.CottageQuickReservationDTO;
import isaproject.dto.cottage.CottageReservationDTO;
import isaproject.mapper.CottageMapper;
import isaproject.mapper.DateSpanMapper;
import isaproject.mapper.ReservationCountMapper;
import isaproject.mapper.SortTypeMapper;
import isaproject.model.DateTimeSpan;
import isaproject.model.ReservationCount;
import isaproject.model.SortType;
import isaproject.model.cottage.Cottage;
import isaproject.model.cottage.CottageReservation;
import isaproject.repository.cottage.CottageRepository;
import isaproject.service.ReservationCountService;
import isaproject.service.cottage.CottageQuickReservationService;
import isaproject.service.cottage.CottageReservationService;
import isaproject.service.cottage.CottageService;

@Service
public class CottageServiceImpl implements CottageService {

	private CottageRepository cottageRepository;

	private CottageReservationService cottageReservationService;
	private CottageQuickReservationService cottageQuickReservationService;
	private ReservationCountService reservationCountService;

	@Autowired
	public CottageServiceImpl(CottageRepository cottageRepository, CottageReservationService cottageReservationService,
			CottageQuickReservationService cottageQuickReservationService,
			ReservationCountService reservationCountService) {
		super();
		this.cottageRepository = cottageRepository;
		this.cottageReservationService = cottageReservationService;
		this.cottageQuickReservationService = cottageQuickReservationService;
		this.reservationCountService = reservationCountService;
	}

	public CottageDTO findById(Long id) {
		Cottage cottage = cottageRepository.findById(id).orElse(null);
		return CottageMapper.CottageToCottageDTO(cottage);
	}

	public Set<CottageDTO> findAll() {
		Set<Cottage> cottages = new HashSet<>(cottageRepository.findAll());
		Set<CottageDTO> dtos = new HashSet<>();
		if (cottages.size() != 0) {
			CottageDTO dto;
			for (Cottage p : cottages) {
				dto = CottageMapper.CottageToCottageDTO(p);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	public Page<CottageDTO> findAllPagination(List<SortTypeDTO> sortTypesDTO, Pageable pageable) {
		List<Sort.Order> sorts = new ArrayList<>();
		List<SortType> sortTypes = sortTypesDTO.stream().map(sortTypeDTO -> SortTypeMapper.SortTypeDTOToSortType(sortTypeDTO)).collect(Collectors.toList());
		if(sortTypes !=null) {
			for (SortType sortType : sortTypes) {
				if (sortType != null && sortType.getDirection().toLowerCase().contains("desc")) {
					System.out.println("Poljeeee"+sortType.getField());
					sorts.add(new Sort.Order(Sort.Direction.DESC, sortType.getField()));
					System.out.println(sortType.getField());
				} else if (sortType != null && sortType.getDirection().toLowerCase().contains("asc")) {
					System.out.println("Poljeeee"+sortType.getField());
					sorts.add(new Sort.Order(Sort.Direction.ASC, sortType.getField()));
				}
			}

		Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sorts));
		
		return CottageMapper.pageCottageToPageCottageDTO(cottageRepository.findAll(paging));
		}else {
			return CottageMapper.pageCottageToPageCottageDTO(cottageRepository.findAll(pageable));
		}
		}

	@Transactional
	@Override
	public CottageDTO deleteById(Long id) {
		CottageDTO cottageDTO = findById(id);
		if (cottageReservationService.findAllActiveByCottageId(id).isEmpty()) {
			cottageRepository.deleteById(id);
			return cottageDTO;
		}
		return null;
	}

	@Transactional
	@Override
	public CottageDTO save(CottageDTO cottageDTO) {
		Cottage cottage = CottageMapper.CottageDTOToCottage(cottageDTO);
		return CottageMapper.CottageToCottageDTO(cottageRepository.save(cottage));
	}

	@Transactional
	@Override
	public CottageDTO update(CottageDTO cottageDTO) {
		Cottage cottage = CottageMapper.CottageDTOToCottage(cottageDTO);
		return CottageMapper.CottageToCottageDTO(cottageRepository.save(cottage));
	}

	@Transactional
	@Override
	public CottageDTO updateAvailableTerms(Long id, DateTimeSpan newDateSpan) {
		Cottage cottage = cottageRepository.findById(id).get();
		for (CottageReservationDTO cottageReservation : cottageReservationService.findByCottageId(id)) {
			if (newDateSpan.overlapsWith(cottageReservation.getDuration()))
				return null;
		}
		for (CottageQuickReservationDTO cottageQuickReservation : cottageQuickReservationService.findByCottageId(id)) {
			if (newDateSpan.overlapsWith(cottageQuickReservation.getDuration()))
				return null;
		}
		boolean overlapped = false;
		Set<DateTimeSpan> availaDateSpans = new HashSet<>(cottage.getAvailableReservationDateSpan());
		for (DateTimeSpan reservationSpan : availaDateSpans) {
			if (reservationSpan.overlapsWith(newDateSpan)) {
				cottage.getAvailableReservationDateSpan().remove(reservationSpan);
				if (newDateSpan.getStartDate().compareTo(reservationSpan.getStartDate()) <= 0) {
					reservationSpan = new DateTimeSpan(newDateSpan.getStartDate(), reservationSpan.getEndDate());
				}
				if (newDateSpan.getEndDate().compareTo(reservationSpan.getEndDate()) >= 0) {
					reservationSpan = new DateTimeSpan(reservationSpan.getStartDate(), newDateSpan.getEndDate());
				}
				overlapped = true;
				cottage.getAvailableReservationDateSpan().add(reservationSpan);
			}
		}
		if (!overlapped)
			cottage.getAvailableReservationDateSpan().add(newDateSpan);

		return CottageMapper.CottageToCottageDTO(cottageRepository.save(cottage));
	}

	@Transactional
	@Override
	public CottageDTO updateInfo(CottageDTO cottageDTO) {
		Cottage cottage = CottageMapper.CottageDTOToCottage(cottageDTO);
		if (cottageReservationService.findAllActiveByCottageId(cottage.getId()).isEmpty()) {
			return CottageMapper.CottageToCottageDTO(cottageRepository.save(cottage));
		}
		return null;
	}

	@Override
	public Set<CottageDTO> findByCottageName(String name) {
		Set<Cottage> cottages = cottageRepository.findByName(name);
		Set<CottageDTO> dtos = new HashSet<>();
		if (cottages.size() != 0) {

			CottageDTO dto;
			for (Cottage p : cottages) {
				dto = CottageMapper.CottageToCottageDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

	@Override
	public Set<CottageDTO> findByCottageOwnerId(Long id) {
		Set<Cottage> cottages = new HashSet<>(cottageRepository.findByCottageOwnerId(id));
		Set<CottageDTO> dtos = new HashSet<>();
		if (cottages.size() != 0) {

			CottageDTO dto;
			for (Cottage p : cottages) {
				dto = CottageMapper.CottageToCottageDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

	@Override
	public Set<CottageDTO> findByReservationDate(DateSpanDTO reservationDateDTO) {
		DateTimeSpan reservationDate = DateSpanMapper.dateSpanDTOtoDateSpan(reservationDateDTO);
		Set<Cottage> cottages = new HashSet<>(cottageRepository.findAll());
		Set<CottageDTO> availableCottages = new HashSet<>();
		if (cottages.size() != 0) {

			CottageDTO dto;
			for (Cottage p : cottages) {
				for (DateTimeSpan d : p.getAvailableReservationDateSpan()) {
					if (d.isTimeSpanBetween(reservationDate)) {
						dto = CottageMapper.CottageToCottageDTO(p);
						availableCottages.add(dto);
					}
				}
			}
		}

		return availableCottages;
	}

	@Override
	public Page<CottageDTO> findByAvailability(CottageAvailabilityDTO cottageAvailability, Pageable pageable) {

		int hours = 0;

		String name = "%";
		Double grade = -1.0;
		int bed = 0;
		if (cottageAvailability.getName() != null) {
			name = name + cottageAvailability.getName().toLowerCase().concat("%");
		}
		if (cottageAvailability.getGrade() != null) {
			grade = cottageAvailability.getGrade();
		}
		if (cottageAvailability.getBedCapacity() != 0) {
			bed = cottageAvailability.getBedCapacity();
		}
		Boolean isLocationSortDisabled = true;
		
		List<Sort.Order> sorts = new ArrayList<>();
		if (cottageAvailability.getSortBy() != null && cottageAvailability.getSortBy().size() != 0) {

			CottageDTO dto;
			for (SortType sortType : cottageAvailability.getSortBy()) {
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
					System.out.println(sortType.getField());
				} else if (sortType != null && sortType.getDirection().toLowerCase().contains("asc")) {
					sorts.add(new Sort.Order(Sort.Direction.ASC, sortType.getField()));
				}
			}
		}

		Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sorts));
		List<Cottage> availableCottages;
		List<CottageDTO> availableCottagesWithPrice;

		if (cottageAvailability.getDateSpan() == null || cottageAvailability.getDateSpan().getStartDate() == null
				|| cottageAvailability.getDateSpan().getEndDate() == null) {
			Page<Cottage> pageCottage;
			if(isLocationSortDisabled) {
			pageCottage = cottageRepository.searchCottage(name, grade, bed, paging);
			}else {
			pageCottage = cottageRepository.searchCottageWithSortLocation(name, grade, bed, paging);
			}
			availableCottages = pageCottage.getContent();
			return new PageImpl(availableCottages, paging, pageCottage.getTotalElements());
		} else {
			LocalDateTime start = cottageAvailability.getDateSpan().getStartDate();
			LocalDateTime end = cottageAvailability.getDateSpan().getEndDate();
			hours = (int) ChronoUnit.HOURS.between(start, end);
			Page<Cottage> pageCottage;
			if(isLocationSortDisabled) {
			pageCottage = cottageRepository.getAvailability(start, end, name, grade, bed, paging);
			}else {
				pageCottage = cottageRepository.getAvailabilityWithSortLocation(start, end, name, grade, bed, paging);
			}
			availableCottages = pageCottage.getContent();
			availableCottagesWithPrice = new ArrayList<CottageDTO>();
			if (availableCottages.size() != 0) {
				CottageDTO dto;
				for (Cottage p : availableCottages) {
					dto = CottageMapper.CottageToCottageDTOWithPrice(p, hours);
					availableCottagesWithPrice.add(dto);
				}
			}
		
			Page<CottageDTO> pc = new PageImpl(availableCottagesWithPrice, paging, pageCottage.getTotalElements());
			return pc;
		}

	}

	@Override
	public ReservationCountDTO getCottageReservationCountYearly(long id) {
		int[] count = new int[4];
		ReservationCount reservationCount = new ReservationCount();
		Cottage cottage = cottageRepository.findById(id).get();
		Set<CottageReservation> reservations = cottage.getCottageReservation();
		for (CottageReservation reservation : reservations) {
			for (int i = 1; i <= 4; i++) {
				count = reservationCountService.countYearly(reservation.getDuration(), i, count);
			}

		}
		reservationCount.setYearlySum(count);
		return ReservationCountMapper.ReservationCountToReservationCountDTO(reservationCount);
	}

	@Override
	public ReservationCountDTO getCottageReservationCountMonthly(long id) {
		int[] count = new int[12];
		ReservationCount reservationCount = new ReservationCount();
		Cottage cottage = cottageRepository.findById(id).get();
		Set<CottageReservation> reservations = cottage.getCottageReservation();
		for (CottageReservation reservation : reservations) {
			for (int i = 1; i <= 12; i++) {
				count = reservationCountService.countMonthly(reservation.getDuration(), i, count);
			}

		}
		reservationCount.setMonthlySum(count);
		return ReservationCountMapper.ReservationCountToReservationCountDTO(reservationCount);
	}

	@Override
	public ReservationCountDTO getCottageReservationCountWeekly(long id) {
		int[] count = new int[4];
		ReservationCount reservationCount = new ReservationCount();
		Cottage cottage = cottageRepository.findById(id).get();
		Set<CottageReservation> reservations = cottage.getCottageReservation();
		for (CottageReservation reservation : reservations) {
			for (int i = 1; i <= 4; i++) {
				count = reservationCountService.countWeekly(reservation.getDuration(), i, count);
			}

		}
		reservationCount.setWeeklySum(count);
		return ReservationCountMapper.ReservationCountToReservationCountDTO(reservationCount);
	}

}
