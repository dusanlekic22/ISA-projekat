package isaproject.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import isaproject.dto.FishingCourseAvailabilityDTO;
import isaproject.dto.FishingCourseDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.exception.ReservedServiceException;
import isaproject.mapper.FishingCourseMapper;
import isaproject.mapper.SortTypeMapper;
import isaproject.mapper.boat.BoatMapper;
import isaproject.model.FishingCourse;
import isaproject.model.SortType;
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
		FishingCourse course = courseRepository.findById(id).orElse(null);
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
	
	@Override
	public Page<FishingCourseDTO> findAllPagination(List<SortTypeDTO> sortTypesDTO,Pageable pageable ){
		List<Sort.Order> sorts = new ArrayList<>();
		List<SortType> sortTypes = sortTypesDTO.stream().map(sortTypeDTO -> SortTypeMapper.SortTypeDTOToSortType(sortTypeDTO)).collect(Collectors.toList());
		if(sortTypes !=null) {
			for (SortType sortType : sortTypes) {
				if (sortType != null && sortType.getDirection().toLowerCase().contains("desc")) {
					sorts.add(new Sort.Order(Sort.Direction.DESC, sortType.getField()));
				} else if (sortType != null && sortType.getDirection().toLowerCase().contains("asc")) {
					sorts.add(new Sort.Order(Sort.Direction.ASC, sortType.getField()));
				}
			}

		Pageable paging = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sorts));
		
		return FishingCourseMapper.pageFishingCourseToPageFishingCourseDTO(courseRepository.findAll(paging));
		}else {
			return FishingCourseMapper.pageFishingCourseToPageFishingCourseDTO(courseRepository.findAll(pageable));
		}
	}

	@Override
	public Page<FishingCourseDTO> findByAvailability(FishingCourseAvailabilityDTO fishingCourseAvailability, Pageable pageable){
		return null;
	}

}
