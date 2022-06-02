package isaproject.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import isaproject.dto.FishingCourseAvailabilityDTO;
import isaproject.dto.FishingCourseDTO;
import isaproject.dto.SortTypeDTO;

public interface FishingCourseService {

	Set<FishingCourseDTO> findAll();
	FishingCourseDTO findById(Long id);
	FishingCourseDTO save(FishingCourseDTO fishingCourseDTO);
	FishingCourseDTO update(FishingCourseDTO fishingCourseDTO);
	void deleteById(Long id);
	FishingCourseDTO updateInfo(FishingCourseDTO fishingCourseDTO);
	Set<FishingCourseDTO> findByFishingCourseName(String name);
	Set<FishingCourseDTO> findByFishingTrainerId(Long id);
	Page<FishingCourseDTO> findAllPagination(List<SortTypeDTO> sortTypeDTO,Pageable paging );

	Page<FishingCourseDTO> findByAvailability(FishingCourseAvailabilityDTO fishingCourseAvailability, Pageable pageable);
}
