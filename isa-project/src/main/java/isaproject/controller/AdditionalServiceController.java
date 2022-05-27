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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.AdditionalServiceDTO;
import isaproject.service.AdditionalServiceService;

@RestController
@RequestMapping(value = "/additionalService", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class AdditionalServiceController {
	
	@Autowired
	private AdditionalServiceService additionalServiceService;

	@PostMapping
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER','BOAT_OWNER','FISHING_TRAINER')")
	public ResponseEntity<AdditionalServiceDTO> save(@RequestBody AdditionalServiceDTO additionalServiceDTO) {
		additionalServiceService.save(additionalServiceDTO);
		return new ResponseEntity<>(additionalServiceDTO,HttpStatus.CREATED);
	}
	
	@GetMapping("/free")
	@ResponseBody
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER','BOAT_OWNER','FISHING_TRAINER')")
	public Set<AdditionalServiceDTO> getFree(){
		return additionalServiceService.findFree();
	}
	
	@GetMapping("/cottage/{id}")
	@ResponseBody
    @PreAuthorize("hasRole('COTTAGE_OWNER')")
	public Set<AdditionalServiceDTO> getByCottageId(@PathVariable("id") Long id){
		return additionalServiceService.findByCottageId(id);
	}
	
	@GetMapping("/boat/{id}")
	@ResponseBody
    @PreAuthorize("hasRole('BOAT_OWNER')")
	public Set<AdditionalServiceDTO> getByBoatId(@PathVariable("id") Long id){
		return additionalServiceService.findByBoatId(id);
	}

}
