package isaproject.service.boat;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import isaproject.dto.BoatAvailabilityDTO;
import isaproject.dto.CottageAvailabilityDTO;
import isaproject.dto.DateSpanDTO;
import isaproject.dto.IncomeDTO;
import isaproject.dto.ReservationCountDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.boat.BoatDTO;
import isaproject.model.DateTimeSpan;

public interface BoatService {
	BoatDTO findById(Long id);

	Set<BoatDTO> findByBoatName(String name);

	Set<BoatDTO> findByBoatOwnerId(Long id);

	Set<BoatDTO> findAll();
	
	Page<BoatDTO> findAllPagination(List<SortTypeDTO> sortTypeDTO,Pageable paging );

	Page<BoatDTO> findByAvailability(BoatAvailabilityDTO boatAvailability, Pageable pageable);
	
	BoatDTO save(BoatDTO Boat);

	BoatDTO update(BoatDTO Boat);

	BoatDTO deleteById(Long id);

	BoatDTO updateInfo(BoatDTO BoatDTO);

	BoatDTO updateAvailableTerms(Long id, DateTimeSpan newDateSpan);

	Set<BoatDTO> findByReservationDate(DateSpanDTO reservationDate);
	
	ReservationCountDTO getBoatReservationCountYearly(long id);

	ReservationCountDTO getBoatReservationCountMonthly(long id);

	ReservationCountDTO getBoatReservationCountWeekly(long id);

	BoatDTO updateUnavailableTerms(Long id, DateTimeSpan newDateSpan);

	IncomeDTO getBoatIncomeYearly(DateTimeSpan duration, long id);

	IncomeDTO getBoatIncomeDaily(DateTimeSpan duration, long id);

	IncomeDTO getBoatIncomeMonthly(DateTimeSpan duration, long id);
}
