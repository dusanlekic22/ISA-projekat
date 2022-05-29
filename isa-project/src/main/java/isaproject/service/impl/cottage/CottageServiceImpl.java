package isaproject.service.impl.cottage;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.CottageAvailabilityDTO;
import isaproject.dto.CottageDTO;
import isaproject.dto.CottageQuickReservationDTO;
import isaproject.dto.CottageReservationDTO;
import isaproject.dto.DateSpanDTO;
import isaproject.dto.cottage.CottageDTO;
import isaproject.dto.cottage.CottageQuickReservationDTO;
import isaproject.dto.cottage.CottageReservationDTO;
import isaproject.mapper.CottageMapper;
import isaproject.mapper.DateSpanMapper;
import isaproject.model.DateTimeSpan;
import isaproject.model.SortType;
import isaproject.repository.AddressRepository;
import isaproject.repository.cottage.CottageRepository;
import isaproject.service.cottage.CottageQuickReservationService;
import isaproject.service.cottage.CottageReservationService;
import isaproject.service.cottage.CottageService;

@Service
public class CottageServiceImpl implements CottageService {

	private CottageRepository cottageRepository;
	
	private CottageReservationService cottageReservationService;
	private CottageQuickReservationService cottageQuickReservationService;

	@Autowired
	public CottageServiceImpl(CottageRepository cottageRepository
			CottageReservationService cottageReservationService,
			CottageQuickReservationService cottageQuickReservationService) {
		super();
		this.cottageRepository = cottageRepository;
		this.cottageReservationService = cottageReservationService;
		this.cottageQuickReservationService = cottageQuickReservationService;
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
		  if(cottages.size()!=0){
	        	
	            CottageDTO dto;
	            for(Cottage p : cottages){
	            	for(DateTimeSpan d : p.getAvailableReservationDateSpan()) {
	            	if(d.isTimeSpanBetween(reservationDate)) {	
	                dto = CottageMapper.CottageToCottageDTO(p);
	                availableCottages.add(dto);
	                }
	                }
	            }
	        }
		
		  return availableCottages;
	}

	@Override
	public Page<CottageDTO> findByAvailability(
			CottageAvailabilityDTO cottageAvailability,
			Pageable pageable) {

		
		int  hours = 0;
		
		String name = "%";
		Double grade = -1.0;
		int bed = 0;
		if(cottageAvailability.getName() != null) {
			name= name +cottageAvailability.getName().toLowerCase().concat("%");
		}
		if(cottageAvailability.getGrade() != null) {
			grade= cottageAvailability.getGrade();
		}
		if(cottageAvailability.getBedCapacity() != 0) {
		   bed= cottageAvailability.getBedCapacity();
		}
		
		List<Sort.Order> sorts= new ArrayList<>();
		  if(cottageAvailability.getSortBy() != null && cottageAvailability.getSortBy().size()!=0){
	        	
	            CottageDTO dto;
	            for(SortType sortType : cottageAvailability.getSortBy()){
	            	if(sortType.direction.toLowerCase().contains("desc")) {
	         		   sorts.add(new Sort.Order(Sort.Direction.DESC,sortType.getField()));
	         		   }
	            	else {
	          		   sorts.add(new Sort.Order(Sort.Direction.ASC,sortType.getField() ));
	            	}
	            }
	        }
		
		
	
		Pageable paging = PageRequest.of(0, 6,Sort.by(sorts));
		
		
		
		
		List<Cottage> availableCottages;
		List<CottageDTO> availableCottagesWithPrice;
		
		if(cottageAvailability.getDateSpan() == null ||
		   cottageAvailability.getDateSpan().getStartDate() == null ||
		   cottageAvailability.getDateSpan().getEndDate() == null
		   ) {
			
			availableCottages = cottageRepository.searchCottage(name,grade,bed, paging);
			
			
			return new PageImpl(availableCottages,paging,availableCottages.size());
			}
		else {
			LocalDateTime start = cottageAvailability.getDateSpan().getStartDate();
			LocalDateTime end = cottageAvailability.getDateSpan().getEndDate();
			hours = (int) ChronoUnit.HOURS.between(start, end);
			System.out.println("adadad"+hours);
			availableCottages = cottageRepository.getAvailability(start,end,name,grade,bed, paging);
			availableCottagesWithPrice = new ArrayList<CottageDTO>();
			  if(availableCottages.size()!=0){
		            CottageDTO dto;
		            for(Cottage p : availableCottages){
		                dto = CottageMapper.CottageToCottageDTOWithPrice(p,hours);
		                availableCottagesWithPrice.add(dto);
		            }
		        }
			  return new PageImpl(availableCottagesWithPrice,paging,availableCottagesWithPrice.size());
		}
		
		
		
		
		 
		 
		  
	
		  
		  
		  
		
		
	
	}

	
	
	

	
}
