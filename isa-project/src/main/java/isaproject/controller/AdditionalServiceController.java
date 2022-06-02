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
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER','BOAT_OWNER','FISHING_TRAINER','CUSTOMER')")
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
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER','CUSTOMER')")
	public ResponseEntity<Set<AdditionalServiceDTO>> getByCottageId(@PathVariable("id") Long id){
		return new ResponseEntity<>(additionalServiceService.findByCottageId(id),HttpStatus.OK);
	}
	
	@GetMapping("/cottageReservation/{id}")
	@ResponseBody
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER','CUSTOMER')")
	public ResponseEntity<Set<AdditionalServiceDTO>> getByCottageReservationId(@PathVariable("id") Long id){
		return new ResponseEntity<>(additionalServiceService.findByCottageReservationId(id),HttpStatus.OK);
	}
	
	@GetMapping("/cottageQuickReservation/{id}")
	@ResponseBody
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER','CUSTOMER')")
	public ResponseEntity<Set<AdditionalServiceDTO>> getByCottageQuickReservationId(@PathVariable("id") Long id){
		return new ResponseEntity<>(additionalServiceService.findByCottageQuickReservationId(id),HttpStatus.OK);
	}
	
	@GetMapping("/boat/{id}")
	@ResponseBody
    @PreAuthorize("hasAnyRole('BOAT_OWNER','CUSTOMER')")
	public ResponseEntity<Set<AdditionalServiceDTO>> getByBoatId(@PathVariable("id") Long id){
		return new ResponseEntity<>(additionalServiceService.findByBoatId(id),HttpStatus.OK);
	}
	
	@GetMapping("/boatReservation/{id}")
	@ResponseBody
    @PreAuthorize("hasAnyRole('BOAT_OWNER','CUSTOMER')")
	public ResponseEntity<Set<AdditionalServiceDTO>> getByBoatReservationId(@PathVariable("id") Long id){
		return new ResponseEntity<>(additionalServiceService.findByBoatReservationId(id),HttpStatus.OK);
	}
	
	@GetMapping("/boatQuickReservation/{id}")
	@ResponseBody
    @PreAuthorize("hasAnyRole('BOAT_OWNER','CUSTOMER')")
	public ResponseEntity<Set<AdditionalServiceDTO>> getByBoatQuickReservationId(@PathVariable("id") Long id){
		return new ResponseEntity<>(additionalServiceService.findByBoatQuickReservationId(id),HttpStatus.OK);
	}

	@GetMapping("/fishingCourse/{id}")
	@ResponseBody
    @PreAuthorize("hasAnyRole('FISHING_TRAINER','CUSTOMER')")
	public Set<AdditionalServiceDTO> getByFishingCourseId(@PathVariable("id") Long id){
		return additionalServiceService.findByFishingCourseId(id);
	}
	
	@GetMapping("/fishingReservation/{id}")
	@ResponseBody
    @PreAuthorize("hasAnyRole('FISHING_TRAINER','CUSTOMER')")
	public ResponseEntity<Set<AdditionalServiceDTO>> getByFishingReservationId(@PathVariable("id") Long id){
		return new ResponseEntity<>(additionalServiceService.findByFishingReservationId(id),HttpStatus.OK);
	}
	
	@GetMapping("/fishingQuickReservation/{id}")
	@ResponseBody
    @PreAuthorize("hasAnyRole('FISHING_TRAINER','CUSTOMER')")
	public ResponseEntity<Set<AdditionalServiceDTO>> getByFishingQuickReservationId(@PathVariable("id") Long id){
		return new ResponseEntity<>(additionalServiceService.findByFishingQuickReservationId(id),HttpStatus.OK);
	}
}
