package isaproject.service.cottage;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import isaproject.dto.CottageAvailabilityDTO;
import isaproject.dto.DateSpanDTO;
import isaproject.dto.cottage.CottageDTO;
import isaproject.model.DateTimeSpan;

public interface CottageService {
	
	CottageDTO findById(Long id);
	Set<CottageDTO> findByCottageName(String name);
	Set<CottageDTO> findByCottageOwnerId(Long id);
    Set<CottageDTO> findAll ();
	CottageDTO save(CottageDTO cottage);
	CottageDTO update(CottageDTO cottage);
	CottageDTO deleteById(Long id);
	CottageDTO updateInfo(CottageDTO cottageDTO);
	CottageDTO updateAvailableTerms(Long id, DateTimeSpan newDateSpan);
	Set<CottageDTO> findByReservationDate(DateSpanDTO reservationDate);
	Page<CottageDTO> findByAvailability(CottageAvailabilityDTO cottageAvailability,Pageable pageable);
}
