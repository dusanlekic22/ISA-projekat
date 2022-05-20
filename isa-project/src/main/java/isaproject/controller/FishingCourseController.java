package isaproject.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.FishingCourseDTO;
import isaproject.service.FishingCourseService;

@RestController
@RequestMapping(value = "/fishingCourse", produces = MediaType.APPLICATION_JSON_VALUE)
public class FishingCourseController {

	private FishingCourseService fishingCourseService;

	@Autowired
	public FishingCourseController(FishingCourseService fishingCourseService) {
		super();
		this.fishingCourseService = fishingCourseService;
	}

	@GetMapping
	public Set<FishingCourseDTO> getAll() {
		return fishingCourseService.findAll();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public FishingCourseDTO get(@PathVariable Long id) {
		return fishingCourseService.findById(id);
	}

	@PostMapping
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<FishingCourseDTO> save(@RequestBody FishingCourseDTO fishingCourseDTO) {
		FishingCourseDTO courseDTO = fishingCourseService.save(fishingCourseDTO);
		return new ResponseEntity<>(courseDTO, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('FISHING_TRAINER', 'ROLE_ADMIN')")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		fishingCourseService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
