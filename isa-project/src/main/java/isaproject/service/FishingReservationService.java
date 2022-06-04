package isaproject.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import isaproject.dto.CustomerDTO;
import isaproject.dto.FishingReservationDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.boat.BoatReservationDTO;

public interface FishingReservationService {
	FishingReservationDTO findById(Long id);

	FishingReservationDTO deleteById(Long id);

	Set<FishingReservationDTO> findAll();

	Set<FishingReservationDTO> findAllPast();

	Set<FishingReservationDTO> findAllActive();

	Set<FishingReservationDTO> findAllPastByFishingCourseId(Long id);

	Set<FishingReservationDTO> findAllActiveByFishingCourseId(Long id);
	

	FishingReservationDTO reserveCustomer(FishingReservationDTO cottageReservation);

	FishingReservationDTO reserveFishingOwner(FishingReservationDTO cottageReservation, String siteUrl);

	FishingReservationDTO confirmReservation(Long id);

	Set<CustomerDTO> findCustomersHasCurrentReservation();

	Set<FishingReservationDTO> findAllActiveByFishingTrainerId(Long id);

	Set<FishingReservationDTO> findAllPastByFishingTrainerId(Long id);

	Set<FishingReservationDTO> findByFishingCourseFishingTrainerId(Long id);

	Set<FishingReservationDTO> findByFishingCourseId(Long id);
	Page<FishingReservationDTO> findAllPagination(Long id,SortTypeDTO sortTypeDTO, Pageable paging);
	Page<FishingReservationDTO> findAllIncomingPagination(Long id,SortTypeDTO sortTypeDTO, Pageable paging);
}
