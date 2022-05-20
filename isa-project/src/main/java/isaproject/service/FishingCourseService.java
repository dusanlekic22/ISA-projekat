package isaproject.service;

import java.util.Set;

import isaproject.dto.FishingCourseDTO;

public interface FishingCourseService {

	Set<FishingCourseDTO> findAll();
	FishingCourseDTO findById(Long id);
	FishingCourseDTO save(FishingCourseDTO fishingCourseDTO);
	void deleteById(Long id);
	
}
