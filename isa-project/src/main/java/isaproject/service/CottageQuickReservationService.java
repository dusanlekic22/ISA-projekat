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

	Set<CottageQuickReservationDTO> findByCottageId(Long id);

	Set<CottageQuickReservationDTO> findByIsReservedFalseAndCottageId(Long id);

	Set<CottageQuickReservationDTO> findByIsReservedFalse();

	Set<CottageQuickReservationDTO> findAll();

	CottageQuickReservationDTO save(CottageQuickReservationDTO cottageQuickReservation, String siteUrl)
			throws UnsupportedEncodingException, MessagingException;

	CottageReservationDTO appointQuickReservation(Long reservationId, Long userId);
}
