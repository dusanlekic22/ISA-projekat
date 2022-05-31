package isaproject.service;

import java.util.Set;

import isaproject.dto.CustomerDTO;
import isaproject.dto.FishingReservationDTO;

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
}
