package isaproject.service.boat;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import isaproject.dto.CustomerDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.boat.BoatReservationDTO;

public interface BoatReservationService {
	BoatReservationDTO findById(Long id);

	BoatReservationDTO deleteById(Long id);

	Set<BoatReservationDTO> findByBoatName(String name);

	Set<BoatReservationDTO> findByBoatId(Long id);

	Set<BoatReservationDTO> findAll();

	Set<BoatReservationDTO> findAllPast();

	Set<BoatReservationDTO> findAllActive();

	Set<BoatReservationDTO> findAllPastByBoatId(Long id);

	Set<BoatReservationDTO> findAllActiveByBoatId(Long id);
	
	Page<BoatReservationDTO> findAllPagination(Long id, List<SortTypeDTO> sortTypeDTO, Pageable pageable);

	BoatReservationDTO reserveCustomer(BoatReservationDTO BoatReservation);

	BoatReservationDTO reserveBoatOwner(BoatReservationDTO BoatReservation, String siteUrl)
			throws UnsupportedEncodingException, MessagingException;

	BoatReservationDTO confirmReservation(Long id);

	Set<CustomerDTO> findCustomersHasCurrentReservation();

	Set<BoatReservationDTO> findByBoatBoatOwnerId(Long id);

	Set<BoatReservationDTO> findAllActiveByBoatOwnerId(Long id);

	Set<BoatReservationDTO> findAllPastByBoatOwnerId(Long id);
}
