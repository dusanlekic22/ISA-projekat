package isaproject.service;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.mail.MessagingException;

import isaproject.dto.CottageQuickReservationDTO;
import isaproject.dto.CottageReservationDTO;

public interface CottageQuickReservationService {

	CottageQuickReservationDTO findById(Long id);
	CottageQuickReservationDTO deleteById(Long id);
	Set<CottageQuickReservationDTO> findByCottageName(String name);
	Set<CottageQuickReservationDTO> findByCottageId(Long name);
    Set<CottageQuickReservationDTO> findAll ();

	CottageQuickReservationDTO save(CottageQuickReservationDTO cottageQuickReservation)
			throws UnsupportedEncodingException, MessagingException;
    CottageReservationDTO appointQuickReservation(CottageReservationDTO cottageReservation);
}
