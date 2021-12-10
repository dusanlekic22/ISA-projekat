package isaproject.service;

import java.util.Set;

import isaproject.dto.CottageDTO;

public interface CottageService {
	
	CottageDTO findById(Long id);
	Set<CottageDTO> findByCottageName(String name);
    Set<CottageDTO> findAll ();
	CottageDTO save(CottageDTO cottage);
}
