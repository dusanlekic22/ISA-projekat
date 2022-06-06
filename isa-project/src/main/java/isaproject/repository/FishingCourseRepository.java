package isaproject.repository;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import isaproject.model.FishingCourse;

public interface FishingCourseRepository extends PagingAndSortingRepository<FishingCourse, Long> {
	 
	
	Set<FishingCourse> findAll();
	Page<FishingCourse> findAll(Pageable paging);
	Set<FishingCourse> findByName(String name);
	Set<FishingCourse> findByFishingTrainerId(Long id);

	@Query(value =
			  "SELECT *  FROM public.fishing_course as fc natural join public.fishing_trainer_available_date_spans  "
				+ " WHERE ((fc.fishing_trainer_id = fishing_trainer_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
				+ " and (lower(fc.name) like :name OR :name is null)  " 
				+ " and ( fc.average_grade = :grade OR :grade = -1.0)  "
				+ " and ( fc.capacity = :bed OR :bed = 0 ) "
				+ " and ( fc.fishing_trainer_id = :fishingTrainerId OR :fishingTrainerId = 0 ) ",
				countQuery  =  "SELECT count(*)  FROM public.fishing_course as fc natural join public.fishing_trainer_available_date_spans as  "
						+ " WHERE ((fc.fishing_trainer_id = fishing_trainer_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
						+ " and (lower(fc.name) like :name OR :name is null)  " 
						+ " and ( fc.grade = :grade OR :grade = -1.0)  "
						+ " and ( fc.capacity = :bed OR :bed = 0 ) "
						+ " and ( fc.fishing_trainer_id = :fishingTrainerId OR :fishingTrainerId = 0 ) ",
					nativeQuery = true)
	Page<FishingCourse> getAvailability(
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end,
			@Param("name") String name, @Param("grade") Double grade, @Param("bed") int bed, @Param("fishingTrainerId")Long fishingTrainerId,Pageable pageable );

	
	@Query(value =
			  " SELECT *  FROM public.fishing_course as fc natural join public.fishing_trainer_available_date_spans natural join public.address as a "
				+ " WHERE ((fc.fishing_trainer_id = fishing_trainer_id)  and fc.address_id =  a.id and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
				+ " and (lower(fc.name) like :name OR :name is null)  " 
				+ " and ( fc.average_grade = :grade OR :grade = -1.0)  "
				+ " and ( fc.capacity = :bed OR :bed = 0 ) "
				+ " and ( fc.fishing_trainer_id = :fishingTrainerId OR :fishingTrainerId = 0 ) ",
				countQuery  = " SELECT count(*)  FROM public.fishing_course as fc natural join public.fishing_trainer_available_date_spans natural join public.address as a "
						+ " WHERE ((fc.fishing_trainer_id = fishing_trainer_id)  and (fc.address_id= a.id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
						+ " and (lower(fc.name) like :name OR :name is null)  " 
						+ " and ( fc.average_grade = :grade OR :grade = -1.0)  "
						+ " and ( fc.capacity = :bed OR :bed = 0 ) "
						+ " and ( fc.fishing_trainer_id = :fishingTrainerId OR :fishingTrainerId = 0 ) ",
					nativeQuery = true)
	Page<FishingCourse> getAvailabilityWithSortLocation(
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end,
			@Param("name") String name, @Param("grade") Double grade, @Param("bed") int bed, @Param("fishingTrainerId")Long fishingTrainerId,Pageable pageable );

	
	@Query(value =
			  " SELECT *  FROM public.fishing_course as fc "
			    + " WHERE "
				+ " (lower(fc.name) like :name OR :name is null)  " 
				+ " and ( fc.average_grade = :grade OR :grade = -1.0)  "
				+ " and ( fc.capacity = :bed OR :bed = 0 ) "
				+ " and ( fc.fishing_trainer_id = :fishingTrainerId OR :fishingTrainerId = 0 ) ",
				countQuery  =
				  " SELECT count(*)  FROM public.fishing_course as fc "
					+ " WHERE "
					+ " (lower(fc.name) like :name OR :name is null)  " 
					+ " and ( fc.average_grade = :grade OR :grade = -1.0)  "
					+ " and ( fc.capacity = :bed OR :bed = 0 ) "
					+ " and ( fc.fishing_trainer_id = :fishingTrainerId OR :fishingTrainerId = 0 ) ",
					nativeQuery = true)
	Page<FishingCourse> searchFishingCourse(
			@Param("name") String name, @Param("grade") Double grade, @Param("bed") int bed, @Param("fishingTrainerId")Long fishingTrainerId,Pageable pageable );

	@Query(value =
			  " SELECT *  FROM public.fishing_course as fc natural join public.address as a "
			    + " WHERE "
				+ "  (lower(fc.name) like :name OR :name is null)  " 
				+ " and ( fc.average_grade = :grade OR :grade = -1.0)  "
				+ " and ( fc.capacity = :bed OR :bed = 0 ) "
				+ " and ( fc.fishing_trainer_id = :fishingTrainerId OR :fishingTrainerId = 0 ) ",
				countQuery  =
				  " SELECT count(*)  FROM public.fishing_course as fc "
					+ " WHERE "
					+ " (lower(fc.name) like :name OR :name is null)  " 
					+ " and ( fc.average_grade = :grade OR :grade = -1.0)  "
					+ " and ( fc.capacity = :bed OR :bed = 0 ) "
					+ " and ( fc.fishing_trainer_id = :fishingTrainerId OR :fishingTrainerId = 0 ) ",
					nativeQuery = true)
	Page<FishingCourse> searchFishingCourseWithSortLocation(
			@Param("name") String name, @Param("grade") Double grade, @Param("bed") int bed, @Param("fishingTrainerId")Long fishingTrainerId,Pageable pageable );
	Page<FishingCourse> findByNameContains(String name, Pageable paging);

	@Query(value =
			  " SELECT * from public.fishing_course natural join public.fishing_course_subscribers as fs  "
			    + " WHERE fs.customer_id = :customerId and fs.fishing_course_id = id",	
				countQuery  =
				  "SELECT count(*) from public.fishing_course natural join public.fishing_course_subscribers as fs  "
					+ " WHERE fs.customer_id = :customerId and fs.fishing_course_id = id ",
					nativeQuery = true)
	Page<FishingCourse> subscriptionsFishing(
			@Param("customerId") Long customerId,Pageable pageable );
	
	
}
