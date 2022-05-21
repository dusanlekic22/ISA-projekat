package isaproject.service;

import java.util.Set;

import isaproject.dto.FishingCourseDTO;

public interface FishingCourseService {

	Set<FishingCourseDTO> findAll();
	FishingCourseDTO findById(Long id);
	FishingCourseDTO save(FishingCourseDTO fishingCourseDTO);
	FishingCourseDTO update(FishingCourseDTO fishingCourseDTO);
	void deleteById(Long id);
	FishingCourseDTO updateInfo(FishingCourseDTO fishingCourseDTO);
	Set<FishingCourseDTO> findByFishingCourseName(String name);
	Set<FishingCourseDTO> findByFishingTrainerId(Long id);
	
}
