package isaproject.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.GradeDTO;
import isaproject.service.GradeService;

@RestController
@RequestMapping(value = "/grade", produces = MediaType.APPLICATION_JSON_VALUE)
public class GradeController {

	GradeService gradeService;

	@Autowired
	public GradeController(GradeService gradeService) {
		super();
		this.gradeService = gradeService;
	}

	@PostMapping("/approve")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<GradeDTO> approve(@RequestBody GradeDTO gradeDTO) {
		GradeDTO grade = gradeService.approve(gradeDTO);
		return new ResponseEntity<>(grade, HttpStatus.OK);
	}

	@PostMapping("/decline")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<GradeDTO> decline(@RequestBody GradeDTO gradeDTO) {
		GradeDTO grade = gradeService.decline(gradeDTO);
		return new ResponseEntity<>(grade, HttpStatus.OK);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Set<GradeDTO>> getAllGrades() {
		Set<GradeDTO> grades = gradeService.getAllGrades();
		return new ResponseEntity<Set<GradeDTO>>(grades, HttpStatus.OK);
	}

}
