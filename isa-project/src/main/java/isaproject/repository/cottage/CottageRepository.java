package isaproject.repository.cottage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import isaproject.model.cottage.Cottage;

public interface CottageRepository extends PagingAndSortingRepository<Cottage, Long> {
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM cottage c WHERE c.id=:id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
	Cottage getNotLockedCottage(long id);
	
	Set<Cottage> findAll();
	Page<Cottage> findAll(Pageable paging);
	Set<Cottage> findByName(String name);
	
	@Query(value =
			  " SELECT *  FROM public.cottage WHERE deleted = false  ",
				countQuery  = " SELECT count(*)  FROM public.cottage WHERE deleted = false "
						,
					nativeQuery = true)
	Page<Cottage> findAllCottages(Pageable paging);
	
	@Query(value =
			  " SELECT *  FROM public.cottage as c natural join public.cottage_available_date_spans  "
				+ " WHERE ((c.id = cottage_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
				+ " and (lower(c.name) like :name OR :name is null)  " 
				+ " and ( c.average_grade = :average_grade OR :average_grade = -1.0)  "
				+ " and ( c.bed_count = :bed OR :bed = 0 ) "
				+ " and ( c.deleted = false )",
				countQuery  = " SELECT count(*)  FROM public.cottage as c natural join public.cottage_available_date_spans  "
						+ " WHERE ((id = cottage_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
						+ " and (lower(c.name) like :name OR :name is null)  " 
						+ " and ( c.average_grade = :average_grade OR :average_grade = -1.0)  "
						+ " and ( c.bed_count = :bed OR :bed = 0 ) "
						+ " and ( c.deleted = false )",
					nativeQuery = true)
	Page<Cottage> getAvailability(
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end,
			@Param("name") String name, @Param("average_grade") Double averageGrade, @Param("bed") int bed,Pageable pageable );

	
	@Query(value =
			  " SELECT *  FROM  public.cottage as c natural join public.address as a natural join public.cottage_available_date_spans  "
				+ " WHERE ((c.id = cottage_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
				+ " and (lower(c.name) like :name OR :name is null)  " 
				+ " and ( c.average_grade = :average_grade OR :average_grade = -1.0)  "
				+ " and ( c.bed_count = :bed OR :bed = 0 ) "
				+ " and ( c.deleted = false )",
				countQuery  = " SELECT count(*)  FROM public.cottage as c natural join public.cottage_available_date_spans  "
						+ " WHERE ((id = cottage_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
						+ " and (lower(c.name) like :name OR :name is null)  " 
						+ " and ( c.average_grade = :average_grade OR :average_grade = -1.0)  "
						+ " and ( c.bed_count = :bed OR :bed = 0 ) "
						+ " and ( c.deleted = false )",
					nativeQuery = true)
	Page<Cottage> getAvailabilityWithSortLocation(
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end,
			@Param("name") String name, @Param("average_grade") Double averageGrade, @Param("bed") int bed,Pageable pageable );

	
	@Query(value =
			  " SELECT *  FROM public.cottage as c "
			    + " WHERE "
				+ " (lower(c.name) like :name OR :name is null)  " 
				+ " and ( c.average_grade = :average_grade OR :average_grade = -1.0)  "
				+ " and ( c.bed_count = :bed OR :bed = 0 ) "
				+ " and ( c.deleted = false )",
				countQuery  =
				  " SELECT count(*)  FROM public.cottage as c "
					+ " WHERE "
					+ " (lower(c.name) like :name OR :name is null)  " 
					+ " and ( c.average_grade = :average_grade OR :average_grade = -1.0)  "
					+ " and ( c.bed_count = :bed OR :bed = 0 ) "
					+ " and ( c.deleted = false )",
					nativeQuery = true)
	Page<Cottage> searchCottage(
			@Param("name") String name, @Param("average_grade") Double averageGrade, @Param("bed") int bed,Pageable pageable );

	@Query(value =
			  " SELECT *  FROM public.cottage as c natural join public.address as a "
			    + " WHERE "
				+ "  (lower(c.name) like :name OR :name is null)  " 
				+ " and ( c.average_grade = :average_grade OR :average_grade = -1.0)  "
				+ " and ( c.bed_count = :bed OR :bed = 0 ) ",
				countQuery  =
				  " SELECT count(*)  FROM public.cottage as c "
					+ " WHERE "
					+ " (lower(c.name) like :name OR :name is null)  " 
					+ " and ( c.average_grade = :average_grade OR :average_grade = -1.0)  "
					+ " and ( c.bed_count = :bed OR :bed = 0 ) ",
					nativeQuery = true)
	Page<Cottage> searchCottageWithSortLocation(
			@Param("name") String name, @Param("average_grade") Double averageGrade, @Param("bed") int bed,Pageable pageable );

	
	@Query(value =
			  " SELECT * from public.cottage natural join public.cottage_subscribers as cs "
			    + " WHERE  cs.customer_id = :customerId  and cs.cottage_id = id "
				+ " and ( c.deleted = false )",
				countQuery  =
				  "SELECT count(*) from public.cottage natural join public.cottage_subscribers as cs  "
						  + " WHERE  cs.customer_id = :customerId  and cs.cottage_id = id "
						  + " and ( c.deleted = false )",
					nativeQuery = true)
	Page<Cottage> subscriptionsCottage(
			@Param("customerId") Long customerId,Pageable pageable );
	
	
	List<Cottage> findByCottageOwnerId(Long id);
	Page<Cottage> findByNameContains(String name, Pageable paging);
	Set<Cottage> findAllByDeletedIsFalse();
	Page<Cottage> findAllByDeletedIsFalse(Pageable paging);

}
