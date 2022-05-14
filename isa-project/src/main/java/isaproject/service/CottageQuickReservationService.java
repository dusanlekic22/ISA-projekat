package isaproject.service;

import java.util.Set;

import isaproject.dto.CottageQuickReservationDTO;
import isaproject.dto.CottageReservationDTO;

public interface CottageQuickReservationService {

	CottageQuickReservationDTO findById(Long id);
	CottageQuickReservationDTO deleteById(Long id);
	Set<CottageQuickReservationDTO> findByCottageName(String name);
	Set<CottageQuickReservationDTO> findByCottageId(Long name);
    Set<CottageQuickReservationDTO> findAll ();
    CottageQuickReservationDTO save(CottageQuickReservationDTO cottageQuickReservation);
    CottageReservationDTO appointQuickReservation(CottageReservationDTO cottageReservation);
}
