package isaproject.service.cottage;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.mail.MessagingException;

import isaproject.dto.CustomerDTO;
import isaproject.dto.cottage.CottageReservationDTO;

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
    CottageReservationDTO reserveCustomer(CottageReservationDTO cottageReservation);

	CottageReservationDTO reserveCottageOwner(CottageReservationDTO cottageReservation, String siteUrl)
			throws UnsupportedEncodingException, MessagingException;
    CottageReservationDTO confirmReservation(Long id);
	Set<CustomerDTO> findCustomersHasCurrentReservation();
	Set<CottageReservationDTO> findByCottageCottageOwnerId(Long id);
	Set<CottageReservationDTO> findAllActiveByCottageOwnerId(Long id);
	Set<CottageReservationDTO> findAllPastByCottageOwnerId(Long id);
}
