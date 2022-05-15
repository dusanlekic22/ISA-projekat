package isaproject.service;

import java.util.Set;

import isaproject.dto.CottageReservationDTO;

public interface CottageReservationService {
	
	CottageReservationDTO findById(Long id);
	CottageReservationDTO deleteById(Long id);
	Set<CottageReservationDTO> findByCottageName(String name);
	Set<CottageReservationDTO> findByCottageId(Long id);
    Set<CottageReservationDTO> findAll ();
    Set<CottageReservationDTO> findAllPast();
    Set<CottageReservationDTO> findAllActive();
    Set<CottageReservationDTO> findAllPastByCottageId(Long id);
    Set<CottageReservationDTO> findAllActiveByCottageId(Long id);
    CottageReservationDTO save(CottageReservationDTO cottageReservation);

}
