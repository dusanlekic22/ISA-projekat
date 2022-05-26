package isaproject.service.boat;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.mail.MessagingException;

import isaproject.dto.boat.BoatQuickReservationDTO;
import isaproject.dto.boat.BoatReservationDTO;

public interface BoatQuickReservationService {
	BoatQuickReservationDTO findById(Long id);

	BoatQuickReservationDTO deleteById(Long id);

	Set<BoatQuickReservationDTO> findByBoatName(String name);

	Set<BoatQuickReservationDTO> findByBoatId(Long id);

	Set<BoatQuickReservationDTO> findByIsReservedFalseAndBoatId(Long id);

	Set<BoatQuickReservationDTO> findByIsReservedFalse();

	Set<BoatQuickReservationDTO> findAll();

	BoatQuickReservationDTO save(BoatQuickReservationDTO BoatQuickReservation, String siteUrl)
			throws UnsupportedEncodingException, MessagingException;

	BoatReservationDTO appointQuickReservation(Long reservationId, Long userId);
}
