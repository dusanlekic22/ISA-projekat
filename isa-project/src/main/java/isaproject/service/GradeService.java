package isaproject.service;

import java.util.Set;

import isaproject.dto.GradeDTO;

public interface GradeService {

	GradeDTO approve(GradeDTO gradeDTO);

	GradeDTO decline(GradeDTO gradeDTO);

	Set<GradeDTO> getAllGrades();

}
