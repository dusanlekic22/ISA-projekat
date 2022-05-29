package isaproject.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import isaproject.model.Cottage;

public interface CottageRepository extends JpaRepository<Cottage, Long> {

	Set<Cottage> findByName(String name);

	@Query(value =
//			"CREATE TABLE reservationduration ("
//			+ "  price int ) "
//            + 
	" WITH tab1 AS ( "
			+ "SELECT id,   r.hours AS days FROM public.cottage   natural join public.cottage_available_date_spans, reservationduration r  "
			+ "             WHERE ((id = cottage_id) and ('2022-05-31 00:00:00' between start_date and end_date) and ('2022-05-31 00:00:00' between start_date and end_date)) "
			+ "             order  by  name ) "
			+ " SELECT c.id, c.bed_count, c.cottage_rules, c.grade, c.name, tab1.days*c.price_per_hour AS price_per_hour  ,c.promo_description, c.room_count, c.address_id, c.cottage_owner_id  FROM public.cottage as c natural join public.cottage_available_date_spans, tab1  "
			+ "             WHERE ((c.id = cottage_id)  and ('2022-05-31 00:00:00' between start_date and end_date) and ('2022-06-30 00:00:00' between start_date and end_date)) "
			+ "             order  by  name  " 
//			" WITH tab1 AS ( "
//			+ " SELECT id,   Extract(epoch FROM (end_date - start_date))/3600 AS days FROM public.cottage  natural join public.cottage_available_date_spans "
//			+ "             WHERE ((id = cottage_id) and ('2022-05-31 00:00:00' between start_date and end_date) and ('2022-05-31 00:00:00' between start_date and end_date)) "
//			+ "             order  by  name ) "
			
//			 " SELECT *  FROM public.cottage as c natural join public.cottage_available_date_spans  "
//				+ "WHERE cottage_id = id and (:start  between start_date and end_date) and (:end  between start_date and end_date) "
//			+ " and (lower(c.name) like :name OR :name is null)  " + " and ( c.grade = :grade OR :grade = -1.0)  "
//			+ " and ( c.bed_count = :bed OR :bed = 6 ) "
//			+ " and ( :filed1  NOT LIKE '111' ) and ( :direction1  NOT LIKE '111' )  " 
			
//			"  ORDER BY "
//			+ " CASE WHEN :filed1 LIKE 'name' and :direction1 LIKE 'desc' THEN c.name END desc "
//			+ " CASE WHEN :filed1 LIKE 'name' and :direction1 LIKE 'asc' THEN name END asc "
//			+ " CASE WHEN :filed1 LIKE 'price' and :direction1 LIKE 'desc' THEN name END desc "
//			+ " CASE WHEN :filed1 LIKE 'price' and :direction1 LIKE 'desc' THEN name END desc "
			,
			
			countQuery = " SELECT count(*) FROM public.cottage  natural join public.cottage_available_date_spans "
					+ "WHERE cottage_id = id and (:start  between start_date and end_date) and (:end  between start_date and end_date) "
					+ " and (lower(name) like :name OR :name is null)  " + " and ( grade = :grade OR :grade = -1.0)  "
					+ " and ( bed_count = :bed OR :bed = 6 )  ", nativeQuery = true)
	Page<Cottage> getAvailability(
			@Param("start") LocalDateTime start, @Param("end") LocalDateTime end,long hours,
//			@Param("name") String name, @Param("grade") Double grade, @Param("bed") int bed,
//			@Param("filed1") String filed1, @Param("direction1") String direction1,
			Pageable pageable);

	List<Cottage> findByCottageOwnerId(Long id);

}
