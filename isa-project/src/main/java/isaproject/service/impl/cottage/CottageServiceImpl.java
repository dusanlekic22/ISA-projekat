package isaproject.service.impl.cottage;

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
import isaproject.model.DateTimeSpan;
import isaproject.model.cottage.Cottage;
import isaproject.repository.AddressRepository;
import isaproject.repository.cottage.CottageRepository;
import isaproject.service.cottage.CottageQuickReservationService;
import isaproject.service.cottage.CottageReservationService;
import isaproject.service.cottage.CottageService;

@Service
public class CottageServiceImpl implements CottageService {

	private CottageRepository cottageRepository;
	private AddressRepository addressRepository;
	private CottageReservationService cottageReservationService;
	private CottageQuickReservationService cottageQuickReservationService;

	@Autowired
	public CottageServiceImpl(CottageRepository cottageRepository, AddressRepository addressRepository,
			CottageReservationService cottageReservationService,
			CottageQuickReservationService cottageQuickReservationService) {
		super();
		this.cottageRepository = cottageRepository;
		this.addressRepository = addressRepository;
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

}
