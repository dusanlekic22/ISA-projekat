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
	
	@GetMapping("/{cottageId}")
	//@PreAuthorize("hasRole('COTTAGE_OWNER')")	
	public CottageDTO loadById(@PathVariable Long cottageId) {
		return cottageService.findById(cottageId);
	}
	
	@PostMapping("/update")
	//@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageDTO> update(@RequestBody CottageDTO cottageDTO) {
		cottageService.update(cottageDTO);
		return new ResponseEntity<>(cottageDTO,HttpStatus.CREATED);
	}
	
	@PostMapping("/save")
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
	
	@DeleteMapping("/{cottageId}")
	@ResponseBody
	public CottageDTO deleteById(@PathVariable("cottageId") Long id) {
		return cottageService.deleteById(id);
	}
}
