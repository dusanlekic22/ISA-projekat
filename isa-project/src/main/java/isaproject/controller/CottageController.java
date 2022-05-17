package isaproject.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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

import isaproject.dto.CottageDTO;
import isaproject.service.CottageService;

@RestController
@RequestMapping(value = "/cottage", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class CottageController {
	
	@Autowired
	CottageService cottageService;
	
	@GetMapping
	@ResponseBody
	public Set<CottageDTO> getAll(){
		return cottageService.findAll();
	}
	
	@GetMapping("/{id}")
	//@PreAuthorize("hasRole('COTTAGE_OWNER')")	
	public CottageDTO loadById(@PathVariable("id") Long id) {
		return cottageService.findById(id);
	}
	
	@PutMapping
	//@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageDTO> update(@RequestBody CottageDTO cottageDTO) {
		cottageService.update(cottageDTO);
		return new ResponseEntity<>(cottageDTO,HttpStatus.CREATED);
	}
	
	@PostMapping
	//@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageDTO> save(@RequestBody CottageDTO cottageDTO) {
		cottageService.save(cottageDTO);
		return new ResponseEntity<>(cottageDTO,HttpStatus.CREATED);
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
}
