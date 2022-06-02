package isaproject.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import isaproject.model.FishingCourse;
import isaproject.model.cottage.Cottage;

public interface FishingCourseRepository extends PagingAndSortingRepository<FishingCourse, Long> {
	 
	
	Set<FishingCourse> findAll();
	Page<FishingCourse> findAll(Pageable paging);
	Set<FishingCourse> findByName(String name);
	Set<FishingCourse> findByFishingTrainerId(Long id);

}
