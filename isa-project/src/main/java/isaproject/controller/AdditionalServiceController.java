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
		return new ResponseEntity<>(additionalServiceService.save(additionalServiceDTO),HttpStatus.CREATED);
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
	public ResponseEntity<Set<AdditionalServiceDTO>> getByCottageId(@PathVariable("id") Long id){
		return new ResponseEntity<>(additionalServiceService.findByCottageId(id),HttpStatus.OK);
	}
	
	@GetMapping("/cottageReservation/{id}")
	@ResponseBody
    @PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<Set<AdditionalServiceDTO>> getByCottageReservationId(@PathVariable("id") Long id){
		return new ResponseEntity<>(additionalServiceService.findByCottageReservationId(id),HttpStatus.OK);
	}
	
	@GetMapping("/cottageQuickReservation/{id}")
	@ResponseBody
    @PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<Set<AdditionalServiceDTO>> getByCottageQuickReservationId(@PathVariable("id") Long id){
		return new ResponseEntity<>(additionalServiceService.findByCottageQuickReservationId(id),HttpStatus.OK);
	}
	
	@GetMapping("/boat/{id}")
	@ResponseBody
    @PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Set<AdditionalServiceDTO>> getByBoatId(@PathVariable("id") Long id){
		return new ResponseEntity<>(additionalServiceService.findByBoatId(id),HttpStatus.OK);
	}

	@GetMapping("/fishingCourse/{id}")
	@ResponseBody
	public Set<AdditionalServiceDTO> getByFishingCourseId(@PathVariable("id") Long id){
		return additionalServiceService.findByFishingCourseId(id);
	}
}
