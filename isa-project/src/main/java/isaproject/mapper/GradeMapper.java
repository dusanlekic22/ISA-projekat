package isaproject.mapper;

import isaproject.dto.GradeDTO;
import isaproject.model.Grade;

public class GradeMapper {
	public static Grade GradeDTOToGrade(GradeDTO gradeDTO) {
		Grade grade = new Grade(gradeDTO.getValue(), gradeDTO.getUser(),
				gradeDTO.getIsAccepted());
		return grade;
	}

	public static GradeDTO GradeToGradeDTO(Grade grade) {
		GradeDTO gradeDTO = new GradeDTO();
		gradeDTO.setValue(grade.getValue());
		gradeDTO.setUser(grade.getUser());
		gradeDTO.setIsAccepted(grade.getIsAccepted());
		return gradeDTO;
	}

}
