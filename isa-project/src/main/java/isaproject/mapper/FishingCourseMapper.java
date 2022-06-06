package isaproject.mapper;

import java.util.function.Function;

import org.springframework.data.domain.Page;

import isaproject.dto.FishingCourseDTO;
import isaproject.model.FishingCourse;

public class FishingCourseMapper {
	
	public static FishingCourse DTOToFishingCourse(FishingCourseDTO fishingCourseDTO) {
		FishingCourse fishingCourse = new FishingCourse();
		fishingCourse.setId(fishingCourseDTO.getId());
		fishingCourse.setName(fishingCourseDTO.getName());
		fishingCourse.setAddress(fishingCourseDTO.getAddress());
		fishingCourse.setPromoDescription(fishingCourseDTO.getPromoDescription());
		fishingCourse.setFishingImage(fishingCourseDTO.getFishingImage());
		fishingCourse.setCapacity(fishingCourseDTO.getCapacity());
		fishingCourse.setFishingQuickReservation(fishingCourseDTO.getFishingQuickReservation());
		fishingCourse.setFishingRules(fishingCourseDTO.getFishingRules());
		fishingCourse.setFishingEquipment(fishingCourseDTO.getFishingEquipment());
		fishingCourse.setAdditionalService(fishingCourseDTO.getAdditionalService());
		fishingCourse.setFishingReservation(fishingCourseDTO.getFishingReservation());
		fishingCourse.setPrice(fishingCourseDTO.getPrice());
		fishingCourse.setCancellationPercentageKeep(fishingCourseDTO.getCancellationPercentageKeep());
		fishingCourse.setFishingTrainer(fishingCourseDTO.getFishingTrainer());
		fishingCourse.setAverageGrade();
		fishingCourseDTO.setSubscribers(fishingCourseDTO.getSubscribers());
		return fishingCourse;
	}
	
	public static FishingCourseDTO FishingCourseToDTO(FishingCourse fishingCourse) {
		FishingCourseDTO fishingCourseDTO = new FishingCourseDTO();
        fishingCourseDTO.setId(fishingCourse.getId());
		fishingCourseDTO.setName(fishingCourse.getName());
		fishingCourseDTO.setAddress(fishingCourse.getAddress());
		fishingCourseDTO.setPromoDescription(fishingCourse.getPromoDescription());
		fishingCourseDTO.setFishingImage(fishingCourse.getFishingImage());
		fishingCourseDTO.setCapacity(fishingCourse.getCapacity());
		fishingCourseDTO.setFishingQuickReservation(fishingCourse.getFishingQuickReservation());
		fishingCourseDTO.setFishingRules(fishingCourse.getFishingRules());
		fishingCourseDTO.setFishingEquipment(fishingCourse.getFishingEquipment());
		fishingCourseDTO.setAdditionalService(fishingCourse.getAdditionalService());
		fishingCourseDTO.setFishingReservation(fishingCourse.getFishingReservation());
		fishingCourseDTO.setPrice(fishingCourse.getPrice());
		fishingCourseDTO.setCancellationPercentageKeep(fishingCourse.getCancellationPercentageKeep());
		fishingCourseDTO.setFishingTrainer(fishingCourse.getFishingTrainer());
		fishingCourseDTO.setAverageGrade(fishingCourse.getAverageGrade());
		fishingCourseDTO.setSubscribers(fishingCourse.getSubscribers());
		fishingCourseDTO.setDeleted(fishingCourse.getDeleted());
		return fishingCourseDTO;
	}
	
	public static FishingCourseDTO FishingCourseToFishingCourseDTOWithPrice(FishingCourse fishingCourse, int hours) {
		fishingCourse.setPrice(fishingCourse.getPrice()*hours);
		FishingCourseDTO fishingCourseDTO = FishingCourseToDTO(fishingCourse);
		return fishingCourseDTO;
	}
	
	public static Page<FishingCourseDTO> pageFishingCourseToPageFishingCourseDTO(Page<FishingCourse> fishingCoursePages) {
		Page<FishingCourseDTO> fishingCourseDTOPages = fishingCoursePages.map(new Function<FishingCourse, FishingCourseDTO>() {
			@Override
			public FishingCourseDTO apply(FishingCourse entity) {
				FishingCourseDTO dto = FishingCourseMapper.FishingCourseToDTO(entity);
				return dto;
			}
		});
		return fishingCourseDTOPages;
	}
}
