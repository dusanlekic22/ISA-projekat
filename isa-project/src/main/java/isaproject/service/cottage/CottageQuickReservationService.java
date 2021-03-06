package isaproject.service.cottage;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import isaproject.dto.boat.BoatQuickReservationDTO;
import isaproject.dto.cottage.CottageQuickReservationDTO;
import isaproject.dto.cottage.CottageReservationDTO;

public interface CottageQuickReservationService {

	CottageQuickReservationDTO findById(Long id);

	CottageQuickReservationDTO deleteById(Long id);

	Set<CottageQuickReservationDTO> findByCottageId(Long id);

	Set<CottageQuickReservationDTO> findByIsReservedFalseAndCottageId(Long id);

	Set<CottageQuickReservationDTO> findByIsReservedFalse();

	Set<CottageQuickReservationDTO> findAll();

	CottageQuickReservationDTO save(CottageQuickReservationDTO cottageQuickReservation, String siteUrl)
			throws UnsupportedEncodingException, MessagingException;

	CottageReservationDTO appointQuickReservation(Long reservationId, Long userId);

	Set<CottageQuickReservationDTO> findByCottageOwnerId(Long id);
	
	Page<CottageQuickReservationDTO> findAllPagination(Long cottageOwnerId,Pageable paging);
}
