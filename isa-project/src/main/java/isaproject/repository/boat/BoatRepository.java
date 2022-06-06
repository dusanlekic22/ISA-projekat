package isaproject.repository.boat;

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

import isaproject.model.boat.Boat;

public interface BoatRepository extends PagingAndSortingRepository<Boat, Long> {
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Boat b WHERE b.id=:id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
	Boat getNotLockedBoat(long id);
	
	public Set<Boat> findAll();
	List<Boat> findByName(String name);

	List<Boat> findByBoatOwnerId(Long id);
	
	@Query(value =
			  " SELECT *  FROM public.boat  ",
				countQuery  = " SELECT count(*)  FROM public.boat"
						,
					nativeQuery = true)
	Page<Boat> findAllBoats(Pageable paging);
	
	@Query(value =
			  " SELECT *  FROM public.boat as b natural join public.boat_available_date_spans  "
				+ " WHERE ((b.id = boat_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
				+ " and (lower(b.name) like :name OR :name is null)  " 
				+ " and ( b.average_grade = :grade OR :grade = -1.0)  "
				+ " and ( b.capacity = :bed OR :bed = 0 ) "
				+ " and ( b.deleted = false )",
				countQuery  = " SELECT count(*)  FROM public.boat as b natural join public.boat_available_date_spans  "
						+ " WHERE ((id = boat_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
						+ " and (lower(b.name) like :name OR :name is null)  " 
						+ " and ( b.average_grade = :grade OR :grade = -1.0)  "
						+ " and ( b.capacity = :bed OR :bed = 0 ) "
						+ " and ( b.deleted = false )",
					nativeQuery = true)
	Page<Boat> getAvailability(
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end,
			@Param("name") String name, @Param("grade") Double grade, @Param("bed") int bed,Pageable pageable );

	
	@Query(value =
			  " SELECT *  FROM  public.cboat as b natural join public.address as a natural join public.boat_available_date_spans  "
				+ " WHERE ((b.id = boat_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
				+ " and (lower(b.name) like :name OR :name is null)  " 
				+ " and ( b.average_grade = :grade OR :grade = -1.0)  "
				+ " and ( b.capacity = :bed OR :bed = 0 ) "
				+ " and ( b.deleted = false )",
				countQuery  = " SELECT count(*)  FROM public.boat as b natural join public.boat_available_date_spans  "
						+ " WHERE ((id = boat_id) and ( :start between start_date and end_date ) and ( :end between start_date and end_date)) "
						+ " and (lower(b.name) like :name OR :name is null)  " 
						+ " and ( b.average_grade = :grade OR :grade = -1.0)  "
						+ " and ( b.capacity = :bed OR :bed = 0 ) "
						+ " and ( b.deleted = false )",
					nativeQuery = true)
	Page<Boat> getAvailabilityWithSortLocation(
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end,
			@Param("name") String name, @Param("grade") Double grade, @Param("bed") int bed,Pageable pageable );

	
	@Query(value =
			  " SELECT *  FROM public.boat as b "
			    + " WHERE "
				+ " (lower(b.name) like :name OR :name is null)  " 
				+ " and ( b.average_grade = :grade OR :grade = -1.0)  "
				+ " and ( b.capacity = :bed OR :bed = 0 ) "
				+ " and ( b.deleted = false )",
				countQuery  =
				  " SELECT count(*)  FROM public.boat as b "
					+ " WHERE "
					+ " (lower(b.name) like :name OR :name is null)  " 
					+ " and ( b.average_grade = :grade OR :grade = -1.0)  "
					+ " and ( b.capacity = :bed OR :bed = 0 ) "
					+ " and ( b.deleted = false )",
					nativeQuery = true)
	Page<Boat> searchBoat(
			@Param("name") String name, @Param("grade") Double grade, @Param("bed") int bed,Pageable pageable );

	@Query(value =
			  " SELECT *  FROM public.boat as b natural join public.address as a "
			    + " WHERE "
				+ "  (lower(b.name) like :name OR :name is null)  " 
				+ " and ( b.average_grade = :grade OR :grade = -1.0)  "
				+ " and ( b.capacity = :bed OR :bed = 0 ) "
				+ " and ( b.deleted = false )",
				countQuery  =
				  " SELECT count(*)  FROM public.boat as b "
					+ " WHERE "
					+ " (lower(b.name) like :name OR :name is null)  " 
					+ " and ( b.average_grade = :grade OR :grade = -1.0)  "
					+ " and ( b.capacity = :bed OR :bed = 0 ) "
					+ " and ( b.deleted = false )",
					nativeQuery = true)
	Page<Boat> searchBoatWithSortLocation(
			@Param("name") String name, @Param("grade") Double grade, @Param("bed") int bed,Pageable pageable );
	public Page<Boat> findByNameContains(String name, Pageable paging);
	
	
	@Query(value =
			  " SELECT * from public.boat natural join public.boat_subscribers as bs "
			    + " WHERE  bs.customer_id = :customerId and bs.boat_id = id "
				+ " and ( b.deleted = false )",
				countQuery  =
				  "SELECT count(*) from public.boat natural join public.boat_subscribers as bs   "
					+ " WHERE bs.customer_id = :customerId and bs.boat_id = id  "
					+ " and ( b.deleted = false )",
					nativeQuery = true)
	Page<Boat> subscriptionsBoat(
			@Param("customerId") Long customerId,Pageable pageable );
	Set<Boat> findAllByDeletedIsFalse();
	public Page<Boat> findAllByDeletedIsFalse(Pageable paging);	
}