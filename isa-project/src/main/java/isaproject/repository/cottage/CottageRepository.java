package isaproject.repository.cottage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import isaproject.model.cottage.Cottage;

public interface CottageRepository extends PagingAndSortingRepository<Cottage, Long> {

	Set<Cottage> findAll();
	Set<Cottage> findByName(String name);

	@Query(value =
			  " SELECT *  FROM public.cottage as c natural join public.cottage_available_date_spans  "
				+ " WHERE ((c.id = cottage_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
				+ " and (lower(c.name) like :name OR :name is null)  " 
				+ " and ( c.grade = :grade OR :grade = -1.0)  "
				+ " and ( c.bed_count = :bed OR :bed = 0 ) ",
				countQuery  = " SELECT count(*)  FROM public.cottage as c natural join public.cottage_available_date_spans  "
						+ " WHERE ((id = cottage_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
						+ " and (lower(c.name) like :name OR :name is null)  " 
						+ " and ( c.grade = :grade OR :grade = -1.0)  "
						+ " and ( c.bed_count = :bed OR :bed = 0 ) ",
					nativeQuery = true)
	List<Cottage> getAvailability(
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end,
			@Param("name") String name, @Param("grade") Double grade, @Param("bed") int bed,Pageable pageable );

	@Query(value =
			  " SELECT *  FROM public.cottage as c "
			    + " WHERE "
				+ " (lower(c.name) like :name OR :name is null)  " 
				+ " and ( c.grade = :grade OR :grade = -1.0)  "
				+ " and ( c.bed_count = :bed OR :bed = 0 ) ",
				countQuery  =
				  " SELECT count(*)  FROM public.cottage as c "
					+ " WHERE "
					+ " (lower(c.name) like :name OR :name is null)  " 
					+ " and ( c.grade = :grade OR :grade = -1.0)  "
					+ " and ( c.bed_count = :bed OR :bed = 0 ) ",
					nativeQuery = true)
	List<Cottage> searchCottage(
			@Param("name") String name, @Param("grade") Double grade, @Param("bed") int bed,Pageable pageable );

	List<Cottage> findByCottageOwnerId(Long id);

}
