package isaproject.service.boat;

import java.util.Set;

import isaproject.dto.DateSpanDTO;
import isaproject.dto.boat.BoatDTO;
import isaproject.model.DateTimeSpan;

public interface BoatService {
	BoatDTO findById(Long id);

	Set<BoatDTO> findByBoatName(String name);

	Set<BoatDTO> findByBoatOwnerId(Long id);

	Set<BoatDTO> findAll();

	BoatDTO save(BoatDTO Boat);

	BoatDTO update(BoatDTO Boat);

	BoatDTO deleteById(Long id);

	BoatDTO updateInfo(BoatDTO BoatDTO);

	BoatDTO updateAvailableTerms(Long id, DateTimeSpan newDateSpan);

	Set<BoatDTO> findByReservationDate(DateSpanDTO reservationDate);
}
