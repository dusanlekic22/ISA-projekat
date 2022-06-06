package isaproject.service.cottage;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import isaproject.dto.CottageAvailabilityDTO;
import isaproject.dto.DateSpanDTO;
import isaproject.dto.GradeDTO;
import isaproject.dto.IncomeDTO;
import isaproject.dto.ReservationCountDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.cottage.CottageDTO;
import isaproject.model.DateTimeSpan;

public interface CottageService {

	CottageDTO findById(Long id);

	Set<CottageDTO> findByCottageName(String name);

	Set<CottageDTO> findByCottageOwnerId(Long id);

	Set<CottageDTO> findAll();
	
	Page<CottageDTO> findAllPagination(List<SortTypeDTO> sortTypeDTO,Pageable paging );

	CottageDTO save(CottageDTO cottage);

	CottageDTO update(CottageDTO cottage);

	CottageDTO deleteById(Long id);

	CottageDTO updateInfo(CottageDTO cottageDTO);

	CottageDTO updateAvailableTerms(Long id, DateTimeSpan newDateSpan);

	Set<CottageDTO> findByReservationDate(DateSpanDTO reservationDate);
	
	Page<CottageDTO> findByAvailability(CottageAvailabilityDTO cottageAvailability,Pageable pageable);

	ReservationCountDTO getCottageReservationCountYearly(long id);

	ReservationCountDTO getCottageReservationCountMonthly(long id);

	ReservationCountDTO getCottageReservationCountWeekly(long id);

	CottageDTO updateUnavailableTerms(Long id, DateTimeSpan newDateSpan);

	IncomeDTO getCottageIncomeYearly(DateTimeSpan duration, long id);

	IncomeDTO getCottageIncomeMonthly(DateTimeSpan duration, long id);

	IncomeDTO getCottageIncomeDaily(DateTimeSpan duration, long id);

	CottageDTO addGrade(GradeDTO gradeDTO, long cottageId);

	CottageDTO subscribe(Long id, Long customerId);
	
	CottageDTO unsubscribe(Long id, Long customerId);

	Page<CottageDTO> findAllCottageSubscriptionByCustomer(Long customerId, Pageable pageable);

}
