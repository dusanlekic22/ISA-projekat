package isaproject.service;

import java.util.Set;

import isaproject.dto.CottageQuickReservationDTO;

public interface CottageQuickReservationService {

	CottageQuickReservationDTO findById(Long id);
	Set<CottageQuickReservationDTO> findByCottageName(String name);
	Set<CottageQuickReservationDTO> findByCottageId(Long name);
    Set<CottageQuickReservationDTO> findAll ();
    CottageQuickReservationDTO save(CottageQuickReservationDTO cottage);
	
}
