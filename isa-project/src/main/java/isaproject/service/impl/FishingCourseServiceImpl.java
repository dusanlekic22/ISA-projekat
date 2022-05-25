package isaproject.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isaproject.dto.FishingCourseDTO;
import isaproject.exception.ReservedServiceException;
import isaproject.mapper.FishingCourseMapper;
import isaproject.model.FishingCourse;
import isaproject.repository.AddressRepository;
import isaproject.repository.FishingCourseRepository;
import isaproject.service.FishingCourseService;
import isaproject.service.FishingReservationService;

@Service
public class FishingCourseServiceImpl implements FishingCourseService {

	private FishingCourseRepository courseRepository;
	private AddressRepository addressRepository;
	private FishingReservationService fishingReservationService;

	@Autowired
	public FishingCourseServiceImpl(FishingCourseRepository courseRepository, AddressRepository addressRepository,
			FishingReservationService fishingReservationService) {
		super();
		this.courseRepository = courseRepository;
		this.addressRepository = addressRepository;
		this.fishingReservationService = fishingReservationService;
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

	@Transactional
	@Override
	public FishingCourseDTO save(FishingCourseDTO fishingCourseDTO) {
		FishingCourse course = FishingCourseMapper.DTOToFishingCourse(fishingCourseDTO);
		addressRepository.save(fishingCourseDTO.getAddress());
		return FishingCourseMapper.FishingCourseToDTO(courseRepository.save(course));
	}

	@Transactional
	@Override
	public FishingCourseDTO update(FishingCourseDTO fishingCourseDTO) {
		FishingCourse fishingCourse = FishingCourseMapper.DTOToFishingCourse(fishingCourseDTO);
		return FishingCourseMapper.FishingCourseToDTO(courseRepository.save(fishingCourse));
	}

	@Transactional
	@Override
	public void deleteById(Long id) {
		FishingCourseDTO courseDTO = findById(id);
		if (isReserved(courseDTO.getId())) {
			throw new ReservedServiceException(
					String.format("Fishing course '%s' is reserved and can't be deleted or changed.", id));
		}
		courseRepository.deleteById(courseDTO.getId());
	}

	@Transactional
	@Override
	public FishingCourseDTO updateInfo(FishingCourseDTO fishingCourseDTO) {
		FishingCourse fishingCourse = FishingCourseMapper.DTOToFishingCourse(fishingCourseDTO);
		if (isReserved(fishingCourse.getId())) {
			throw new ReservedServiceException(String
					.format("Fishing course '%s' is reserved and can't be deleted or changed.", fishingCourse.getId()));
		}
		return FishingCourseMapper.FishingCourseToDTO(courseRepository.save(fishingCourse));
	}

	private boolean isReserved(Long id) {
		return !fishingReservationService.findAllActiveByFishingCourseId(id).isEmpty();
	}

	@Override
	public Set<FishingCourseDTO> findByFishingCourseName(String name) {
		Set<FishingCourse> courses = courseRepository.findByName(name);
		Set<FishingCourseDTO> courseDTOs = new HashSet<FishingCourseDTO>();
		for (FishingCourse fishingCourse : courses) {
			courseDTOs.add(FishingCourseMapper.FishingCourseToDTO(fishingCourse));
		}
		return courseDTOs;
	}

	@Override
	public Set<FishingCourseDTO> findByFishingTrainerId(Long id) {
		Set<FishingCourse> courses = courseRepository.findByFishingTrainerId(id);
		Set<FishingCourseDTO> courseDTOs = new HashSet<FishingCourseDTO>();
		for (FishingCourse fishingCourse : courses) {
			courseDTOs.add(FishingCourseMapper.FishingCourseToDTO(fishingCourse));
		}
		return courseDTOs;
	}

}
