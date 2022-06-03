package isaproject.repository;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import isaproject.model.FishingTrainer;

public interface FishingTrainerRepository extends PagingAndSortingRepository<FishingTrainer, Long> {

	public Set<FishingTrainer> findAll();
	FishingTrainer findByUsername(String username);
	@Query(value =
			  " SELECT *  FROM public.fishing_trainer as f natural join public.app_user as u natural join public.fishing_trainer_available_date_spans  "
				+ " WHERE ((f.id = fishing_trainer_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
				+ "  and (lower(u.first_name) like :name OR lower(u.last_name) like :name  OR :name is null)  " 
				+ " and ( f.grade = :grade OR :grade = -1.0)  ",
				countQuery  = " SELECT count(*)  FROM public.fishing_trainer as c natural join public.cottage_available_date_spans  "
						+ " WHERE ((id = fishing_trainer_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
						+ "  (lower(u.first_name) like :name OR lower(u.last_name) like :name  OR :name is null)  " 
						+ " and ( f.average_grade = :grade OR :grade = -1.0)  ",
					nativeQuery = true)
	Page<FishingTrainer> getAvailability(
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end,
			@Param("name") String name, @Param("grade") Double grade,Pageable pageable );

	
	@Query(value =
			  " SELECT *  FROM public.fishing_trainer as f natural join public.app_user as u natural join public.address as a natural join public.fishing_trainer_available_date_spans  "
				+ " WHERE ((f.id = fishing_trainer_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
				+ "  and (lower(u.first_name) like :name OR lower(u.last_name) like :name  OR :name is null)  " 
				+ " and ( f.grade = :grade OR :grade = -1.0)  ",
				countQuery  = " SELECT *  FROM public.fishing_trainer as f natural join public.app_user as u natural join public.address as a natural join public.fishing_trainer_available_date_spans  "
						+ " WHERE ((f.id = fishing_trainer_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
						+ "  (lower(u.first_name) like :name OR lower(u.last_name) like :name  OR :name is null)  " 
						+ " and ( f.grade = :grade OR :grade = -1.0)  ",
					nativeQuery = true)
	Page<FishingTrainer> getAvailabilityWithSortLocation(
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end,
			@Param("name") String name, @Param("grade") Double grade,Pageable pageable );

	
	@Query(value =
			  " SELECT *  FROM public.fishing_trainer as f natural join public.app_user as u"
			    + " WHERE "
			    + "  (lower(u.first_name) like :name OR lower(u.last_name) like :name  OR :name is null)  " 
				+ " and ( f.grade = :grade OR :grade = -1.0)  ",
				countQuery  =
				  " SELECT count(*)  FROM public.fishing_trainer as f natural join public.app_user as u"
					+ " WHERE "
					+ "  (lower(u.first_name) like :name OR lower(u.last_name) like :name  OR :name is null)  " 
					+ " and ( f.grade = :grade OR :grade = -1.0)  ",
					nativeQuery = true)
	Page<FishingTrainer> searchFishingTrainer(
			@Param("name") String name, @Param("grade") Double grade,Pageable pageable );

	@Query(value =
			  " SELECT *  FROM public.fishing_trainer as f natural join public.app_user as u natural join public.address as a "
			    + " WHERE "
				+ "  (lower(u.first_name) like :name OR lower(u.last_name) like :name  OR :name is null)  " 
				+ " and ( f.grade = :grade OR :grade = -1.0)  ",
				countQuery  =
				  " SELECT count(*)  FROM public.fishing_trainer as f natural join public.app_user as u natural join public.address as a"
					+ " WHERE "
					+ "  (lower(u.first_name) like :name OR lower(u.last_name) like :name  OR :name is null)  " 
					+ " and ( f.grade = :grade OR :grade = -1.0)  ",
					nativeQuery = true)
	Page<FishingTrainer> searchFishingTrainerWithSortLocation(
			@Param("name") String name, @Param("grade") Double grade, Pageable pageable );
}
