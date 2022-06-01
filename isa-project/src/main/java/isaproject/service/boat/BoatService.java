package isaproject.service.boat;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import isaproject.dto.DateSpanDTO;
import isaproject.dto.ReservationCountDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.boat.BoatDTO;
import isaproject.dto.cottage.CottageDTO;
import isaproject.model.DateTimeSpan;

public interface BoatService {
	BoatDTO findById(Long id);

	Set<BoatDTO> findByBoatName(String name);

	Set<BoatDTO> findByBoatOwnerId(Long id);

	Set<BoatDTO> findAll();
	
	Page<BoatDTO> findAllPagination(List<SortTypeDTO> sortTypeDTO,Pageable paging );

	BoatDTO save(BoatDTO Boat);

	BoatDTO update(BoatDTO Boat);

	BoatDTO deleteById(Long id);

	BoatDTO updateInfo(BoatDTO BoatDTO);

	BoatDTO updateAvailableTerms(Long id, DateTimeSpan newDateSpan);

	Set<BoatDTO> findByReservationDate(DateSpanDTO reservationDate);
	
	ReservationCountDTO getBoatReservationCountYearly(long id);

	ReservationCountDTO getBoatReservationCountMonthly(long id);

	ReservationCountDTO getBoatReservationCountWeekly(long id);
}
