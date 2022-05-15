package isaproject.service;

import java.util.Set;

import isaproject.dto.CottageReservationDTO;

public interface CottageReservationService {
	
	CottageReservationDTO findById(Long id);
	CottageReservationDTO deleteById(Long id);
	Set<CottageReservationDTO> findByCottageName(String name);
	Set<CottageReservationDTO> findByCottageId(Long name);
    Set<CottageReservationDTO> findAll ();
    Set<CottageReservationDTO> findAllPast();
    Set<CottageReservationDTO> findAllActive();
    CottageReservationDTO save(CottageReservationDTO cottageReservation);

}
