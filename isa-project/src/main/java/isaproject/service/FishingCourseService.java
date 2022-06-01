package isaproject.service;

import java.util.Set;

import isaproject.dto.FishingCourseDTO;
import isaproject.dto.IncomeDTO;
import isaproject.dto.ReservationCountDTO;
import isaproject.model.DateTimeSpan;

public interface FishingCourseService {

	Set<FishingCourseDTO> findAll();

	FishingCourseDTO findById(Long id);

	FishingCourseDTO save(FishingCourseDTO fishingCourseDTO);

	FishingCourseDTO update(FishingCourseDTO fishingCourseDTO);

	void deleteById(Long id);

	FishingCourseDTO updateInfo(FishingCourseDTO fishingCourseDTO);

	Set<FishingCourseDTO> findByFishingCourseName(String name);

	Set<FishingCourseDTO> findByFishingTrainerId(Long id);

	ReservationCountDTO getFishingCourseReservationCountYearly(long id);

	ReservationCountDTO getFishingCourseReservationCountMonthly(long id);

	ReservationCountDTO getFishingCourseReservationCountWeekly(long id);

	IncomeDTO getFishingCourseIncomeYearly(DateTimeSpan duration, long id);

	IncomeDTO getFishingCourseIncomeDaily(DateTimeSpan duration, long id);

	IncomeDTO getFishingCourseIncomeMonthly(DateTimeSpan duration, long id);

}
