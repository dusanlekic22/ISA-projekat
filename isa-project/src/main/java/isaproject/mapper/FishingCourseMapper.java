package isaproject.mapper;

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
		fishingCourse.setFishingEquipment(fishingCourse.getFishingEquipment());
		fishingCourse.setAdditionalService(fishingCourseDTO.getAdditionalService());
		fishingCourse.setFishingReservation(fishingCourseDTO.getFishingReservation());
		fishingCourse.setPrice(fishingCourseDTO.getPrice());
		fishingCourse.setCancellationPercentageKeep(fishingCourseDTO.getCancellationPercentageKeep());
		fishingCourse.setFishingTrainer(fishingCourseDTO.getFishingTrainer());
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
		return fishingCourseDTO;
	}
}
