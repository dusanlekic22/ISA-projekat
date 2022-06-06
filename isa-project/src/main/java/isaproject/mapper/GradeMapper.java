package isaproject.mapper;

import isaproject.dto.GradeDTO;
import isaproject.model.Grade;

public class GradeMapper {
	public static Grade GradeDTOToGrade(GradeDTO gradeDTO) {
		Grade grade = new Grade();
		grade.setId(gradeDTO.getId());
		grade.setValue(gradeDTO.getValue());
		grade.setUser(gradeDTO.getUser());
		grade.setReview(gradeDTO.getReview());
		grade.setIsAccepted(gradeDTO.getIsAccepted());
		grade.setCottage(gradeDTO.getCottage());
		grade.setBoat(gradeDTO.getBoat());
		grade.setFishingCourse(gradeDTO.getFishingCourse());
		grade.setCottageOwner(gradeDTO.getCottageOwner());
		grade.setBoatOwner(gradeDTO.getBoatOwner());
		grade.setFishingTrainer(gradeDTO.getFishingTrainer());
		return grade;
	}

	public static GradeDTO GradeToGradeDTO(Grade grade) {
		GradeDTO gradeDTO = new GradeDTO();
		gradeDTO.setId(grade.getId());
		gradeDTO.setValue(grade.getValue());
		gradeDTO.setUser(grade.getUser());
		gradeDTO.setIsAccepted(grade.getIsAccepted());
		gradeDTO.setReview(grade.getReview());
		gradeDTO.setCottage(grade.getCottage());
		gradeDTO.setBoat(grade.getBoat());
		gradeDTO.setFishingCourse(grade.getFishingCourse());
		gradeDTO.setCottageOwner(grade.getCottageOwner());
		gradeDTO.setBoatOwner(grade.getBoatOwner());
		gradeDTO.setFishingTrainer(grade.getFishingTrainer());
		return gradeDTO;
	}

}
