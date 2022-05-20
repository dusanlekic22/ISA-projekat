package isaproject.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isaproject.dto.FishingCourseDTO;
import isaproject.mapper.FishingCourseMapper;
import isaproject.model.FishingCourse;
import isaproject.repository.AddressRepository;
import isaproject.repository.FishingCourseRepository;
import isaproject.service.FishingCourseService;

@Service
public class FishingCourseServiceImpl implements FishingCourseService {

	private FishingCourseRepository courseRepository;
	private AddressRepository addressRepository;

	@Autowired
	public FishingCourseServiceImpl(FishingCourseRepository courseRepository, AddressRepository addressRepository) {
		super();
		this.courseRepository = courseRepository;
		this.addressRepository = addressRepository;
	}

	@Override
	public Set<FishingCourseDTO> findAll() {
		Set<FishingCourse> courses = new HashSet<FishingCourse>(courseRepository.findAll());
		Set<FishingCourseDTO> courseDTOs = new HashSet<FishingCourseDTO>();
		for (FishingCourse fishingCourse : courses) {
			courseDTOs.add(FishingCourseMapper.FishingCourseToDTO(fishingCourse));
		}
		return courseDTOs;
	}

	@Override
	public FishingCourseDTO findById(Long id) {
		FishingCourse course = courseRepository.getById(id);
		return FishingCourseMapper.FishingCourseToDTO(course);
	}

	@Override
	public FishingCourseDTO save(FishingCourseDTO fishingCourseDTO) {
		FishingCourse course = FishingCourseMapper.DTOToFishingCourse(fishingCourseDTO);
		addressRepository.save(fishingCourseDTO.getAddress());
		return FishingCourseMapper.FishingCourseToDTO(courseRepository.save(course));
	}

	@Override
	public void deleteById(Long id) {
		courseRepository.deleteById(id);
	}

}
