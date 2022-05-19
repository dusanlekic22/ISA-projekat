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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.CottageReservationDTO;
import isaproject.service.CottageReservationService;

@RestController
@RequestMapping(value = "/cottageReservation", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class CottageReservationController {
	
	@Autowired
	CottageReservationService cottageReservationService;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<Set<CottageReservationDTO>> getAll(){
		return new ResponseEntity<>(cottageReservationService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/passed")
	@ResponseBody
	public ResponseEntity<Set<CottageReservationDTO>> getAllPassed(){
		return new ResponseEntity<>(cottageReservationService.findAllPast(),HttpStatus.OK);
	}
	
	@GetMapping("/active")
	@ResponseBody
	public ResponseEntity<Set<CottageReservationDTO>> getAllActive(){
		return new ResponseEntity<>(cottageReservationService.findAllActive(),HttpStatus.OK);
	}
	
	@GetMapping("/active/{cottageId}")
	@ResponseBody
	public ResponseEntity<Set<CottageReservationDTO>> getAllActiveByCottageId(@PathVariable("cottageId") Long id){
		return new ResponseEntity<>(cottageReservationService.findAllActiveByCottageId(id),HttpStatus.OK);
	}
	
	@GetMapping("/passed/{cottageId}")
	@ResponseBody
	public ResponseEntity<Set<CottageReservationDTO>> getAllPassedByCottageId(@PathVariable("cottageId") Long id){
		return new ResponseEntity<>(cottageReservationService.findAllPastByCottageId(id),HttpStatus.OK);
	}
	
	@PostMapping("/owner")
	//@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageReservationDTO> reserveOwner(@RequestBody CottageReservationDTO cottageReservationDTO) {
		CottageReservationDTO cottageReservationReturnDTO = cottageReservationService.reserveCottageOwner(cottageReservationDTO);
		if(cottageReservationReturnDTO == null)
			return new ResponseEntity<>(cottageReservationDTO,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(cottageReservationDTO,HttpStatus.CREATED);
	}
	
	@PostMapping("/customer")
	//@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageReservationDTO> reserveCustomer(@RequestBody CottageReservationDTO cottageReservationDTO) {
		CottageReservationDTO cottageReservationReturnDTO = cottageReservationService.reserveCustomer(cottageReservationDTO);
		if(cottageReservationReturnDTO == null)
			return new ResponseEntity<>(cottageReservationDTO,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(cottageReservationDTO,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<CottageReservationDTO> deleteById(@PathVariable("id") Long id) {
		CottageReservationDTO cottageReservationDTO = cottageReservationService.deleteById(id);
		return new ResponseEntity<>(cottageReservationDTO,HttpStatus.OK);
	}



}
