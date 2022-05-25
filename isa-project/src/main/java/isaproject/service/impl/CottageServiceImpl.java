package isaproject.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.CottageDTO;
import isaproject.dto.CottageQuickReservationDTO;
import isaproject.dto.CottageReservationDTO;
import isaproject.dto.DateSpanDTO;
import isaproject.mapper.CottageMapper;
import isaproject.mapper.DateSpanMapper;
import isaproject.model.Cottage;
import isaproject.model.DateTimeSpan;
import isaproject.model.ReservationDateSpanWithPrice;
import isaproject.repository.AddressRepository;
import isaproject.repository.CottageRepository;
import isaproject.service.CottageQuickReservationService;
import isaproject.service.CottageReservationService;
import isaproject.service.CottageService;

@Service
public class CottageServiceImpl implements CottageService {

	@Autowired
	private CottageRepository cottageRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private CottageReservationService cottageReservationService;
	@Autowired
	private CottageQuickReservationService cottageQuickReservationService;

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
		addressRepository.save(cottageDTO.getAddress());
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
		for (CottageQuickReservationDTO cottageQuickReservation : cottageQuickReservationService
				.findByCottageId(id)) {
			if (newDateSpan.overlapsWith(cottageQuickReservation.getDuration()))
				return null;
		}
		boolean overlapped = false;
		Set<ReservationDateSpanWithPrice> availaDateSpans = new HashSet<>(cottage.getAvailableReservationDateSpanWithPrice());
		for (ReservationDateSpanWithPrice reservationSpanWithPrice : availaDateSpans) {
			if (reservationSpanWithPrice.getDateSpan().overlapsWith(newDateSpan)) {
				cottage.getAvailableReservationDateSpanWithPrice().remove(reservationSpanWithPrice);
				if (newDateSpan.getStartDate().compareTo(reservationSpanWithPrice.getDateSpan().getStartDate()) <= 0) {
					reservationSpanWithPrice = new ReservationDateSpanWithPrice( new DateTimeSpan(newDateSpan.getStartDate(), reservationSpanWithPrice.getDateSpan().getEndDate()),33);
				}
				if (newDateSpan.getEndDate().compareTo(reservationSpanWithPrice.getDateSpan().getEndDate()) >= 0) {
					reservationSpanWithPrice = new ReservationDateSpanWithPrice( new DateTimeSpan(reservationSpanWithPrice.getDateSpan().getStartDate(), newDateSpan.getEndDate()),33);
				}
				overlapped = true;
				cottage.getAvailableReservationDateSpanWithPrice().add(new ReservationDateSpanWithPrice( reservationSpanWithPrice.getDateSpan(),33));
			}
		}
		if (!overlapped)
			cottage.getAvailableReservationDateSpanWithPrice().add(new ReservationDateSpanWithPrice(newDateSpan, 0));

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
	            	for(ReservationDateSpanWithPrice d : p.getAvailableReservationDateSpanWithPrice()) {
	            	if(d.getDateSpan().isTimeSpanBetween(reservationDate)) {	
	                dto = CottageMapper.CottageToCottageDTO(p);
	                availableCottages.add(dto);
	                }
	                }
	            }
	        }
		
		  return availableCottages;
	}	
	
	

	
}
