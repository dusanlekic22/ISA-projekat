package isaproject.service;

import java.util.Set;

import isaproject.dto.CottageDTO;
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
	
}
