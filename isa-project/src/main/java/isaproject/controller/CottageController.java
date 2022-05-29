package isaproject.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.CottageAvailabilityDTO;
import isaproject.dto.CottageDTO;
import isaproject.model.DateTimeSpan;
import isaproject.service.CottageService;

@RestController
@RequestMapping(value = "/cottage", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class CottageController {

	@Autowired
	CottageService cottageService;

	@GetMapping
	@ResponseBody
	public Set<CottageDTO> getAll() {
		return cottageService.findAll();
	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasRole('COTTAGE_OWNER')")
	public CottageDTO loadById(@PathVariable("id") Long id) {
		return cottageService.findById(id);
	}

	@PutMapping
	// @PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageDTO> update(@RequestBody CottageDTO cottageDTO) {
		cottageService.update(cottageDTO);
		return new ResponseEntity<>(cottageDTO, HttpStatus.CREATED);
	}

	@PutMapping("/info")
	// @PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageDTO> updateInfo(@RequestBody CottageDTO cottageDTO) {
		CottageDTO cottageReturnDTO = cottageService.updateInfo(cottageDTO);
		if (cottageReturnDTO == null)
			return new ResponseEntity<>(cottageReturnDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(cottageReturnDTO, HttpStatus.OK);
	}

	@PutMapping("/availableTerms/{id}")
	// @PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageDTO> updateAvailableTerms(@PathVariable("id") Long id,
			@RequestBody DateTimeSpan dateTimeSpan) {
		CottageDTO cottageReturnDTO = cottageService.updateAvailableTerms(id, dateTimeSpan);
		if (cottageReturnDTO == null)
			return new ResponseEntity<>(cottageReturnDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(cottageReturnDTO, HttpStatus.OK);
	}

	@PostMapping
	// @PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageDTO> save(@RequestBody CottageDTO cottageDTO) {
		cottageService.save(cottageDTO);
		return new ResponseEntity<>(cottageDTO, HttpStatus.CREATED);
	}

	@GetMapping("/search")
	@ResponseBody
	public Set<CottageDTO> search(@RequestParam("name") String cottageName) {
		return cottageService.findByCottageName(cottageName);
	}

	@GetMapping("/owner/{id}")
	@ResponseBody
	public Set<CottageDTO> getByCottageOwnerId(@PathVariable("id") Long id) {
		return cottageService.findByCottageOwnerId(id);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public CottageDTO deleteById(@PathVariable("id") Long id) {
		return cottageService.deleteById(id);
	}

	@PostMapping("/availability")
	@ResponseBody
	public Page<CottageDTO> search(@RequestBody CottageAvailabilityDTO cottageAvailability,Principal user,	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size,
			  @RequestParam(defaultValue = "id") String sortBy) {
		Pageable paging = PageRequest.of(page, size);
		return cottageService.findByAvailability(
				cottageAvailability,
				user,
				paging);
	}
	
	
	
}
