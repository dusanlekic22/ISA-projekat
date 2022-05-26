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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@PreAuthorize("hasAnyRole('FISHING_TRAINER', 'ROLE_ADMIN')")
	public FishingCourseDTO get(@PathVariable Long id) {
		return fishingCourseService.findById(id);
	}

	@PostMapping
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<FishingCourseDTO> save(@RequestBody FishingCourseDTO fishingCourseDTO) {
		FishingCourseDTO courseDTO = fishingCourseService.save(fishingCourseDTO);
		return new ResponseEntity<>(courseDTO, HttpStatus.CREATED);
	}

	@PutMapping
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<FishingCourseDTO> update(@RequestBody FishingCourseDTO fishingCourseDTO) {
		FishingCourseDTO courseDTO = fishingCourseService.update(fishingCourseDTO);
		return new ResponseEntity<>(courseDTO, HttpStatus.OK);
	}

	@PutMapping("/info")
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<FishingCourseDTO> updateInfo(@RequestBody FishingCourseDTO fishingCourseDTO) {
		FishingCourseDTO courseDTO = fishingCourseService.updateInfo(fishingCourseDTO);
		return new ResponseEntity<>(courseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('FISHING_TRAINER', 'ROLE_ADMIN')")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		fishingCourseService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/search")
	public Set<FishingCourseDTO> search(@RequestParam("name") String fishingCourseName) {
		return fishingCourseService.findByFishingCourseName(fishingCourseName);
	}

	@GetMapping("/owner/{id}")
	public Set<FishingCourseDTO> getByFishingTrainerId(@PathVariable("id") Long id) {
		return fishingCourseService.findByFishingTrainerId(id);
	}

}
