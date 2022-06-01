package isaproject.service.impl.boat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.DateSpanDTO;
import isaproject.dto.ReservationCountDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.boat.BoatDTO;
import isaproject.dto.boat.BoatQuickReservationDTO;
import isaproject.dto.boat.BoatReservationDTO;
import isaproject.dto.cottage.CottageDTO;
import isaproject.mapper.CottageMapper;
import isaproject.mapper.DateSpanMapper;
import isaproject.mapper.ReservationCountMapper;
import isaproject.mapper.SortTypeMapper;
import isaproject.mapper.boat.BoatMapper;
import isaproject.model.DateTimeSpan;
import isaproject.model.ReservationCount;
import isaproject.model.SortType;
import isaproject.model.boat.Boat;
import isaproject.model.boat.BoatReservation;
import isaproject.repository.AddressRepository;
import isaproject.repository.boat.BoatRepository;
import isaproject.service.ReservationCountService;
import isaproject.service.boat.BoatQuickReservationService;
import isaproject.service.boat.BoatReservationService;
import isaproject.service.boat.BoatService;

@Service
public class BoatServiceImpl implements BoatService {

	private BoatRepository boatRepository;
	private BoatReservationService boatReservationService;
	private BoatQuickReservationService boatQuickReservationService;
	private ReservationCountService reservationCountService;

	@Autowired
	public BoatServiceImpl(BoatRepository boatRepository, AddressRepository addressRepository,
			BoatReservationService boatReservationService,
			BoatQuickReservationService boatQuickReservationService,
			ReservationCountService reservationCountService) {
		super();
		this.boatRepository = boatRepository;
		this.boatReservationService = boatReservationService;
		this.boatQuickReservationService = boatQuickReservationService;
		this.reservationCountService = reservationCountService;
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

		return BoatMapper.BoatToBoatDTO(boatRepository.save(boat));
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
                count = reservationCountService.countYearly(reservation.getDuration(), i, count);
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
                count = reservationCountService.countMonthly(reservation.getDuration(), i, count);
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
                count = reservationCountService.countWeekly(reservation.getDuration(), i, count);
            }
           
        }
        reservationCount.setWeeklySum(count); 
        return ReservationCountMapper.ReservationCountToReservationCountDTO(reservationCount);
	}

}
