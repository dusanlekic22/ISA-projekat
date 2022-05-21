package isaproject.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.FishingCourse;

public interface FishingCourseRepository extends JpaRepository<FishingCourse, Long> {
	 
	Set<FishingCourse> findByName(String name);
	Set<FishingCourse> findByFishingTrainerId(Long id);

}
