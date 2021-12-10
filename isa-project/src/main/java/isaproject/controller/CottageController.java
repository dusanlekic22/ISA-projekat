package isaproject.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	@PreAuthorize("hasRole('COTTAGE_OWNER')")	
	public CottageDTO loadById(@PathVariable Long cottageId) {
		return cottageService.findById(cottageId);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageDTO> save(CottageDTO cottageDTO) {
		cottageService.save(cottageDTO);
		return new ResponseEntity<>(cottageDTO,HttpStatus.CREATED);
	}

	@GetMapping("/search")
	@ResponseBody
	public Set<CottageDTO> search(@RequestParam("name") String cottageName) {
		return cottageService.findByCottageName(cottageName);
	}
}
