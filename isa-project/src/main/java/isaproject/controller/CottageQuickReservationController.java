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

import isaproject.dto.CottageQuickReservationDTO;
import isaproject.dto.CottageReservationDTO;
import isaproject.service.CottageQuickReservationService;

@RestController
@RequestMapping(value = "/cottageQuickReservation", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class CottageQuickReservationController {
	
	@Autowired
	CottageQuickReservationService cottageQuickReservationService;
	
	@GetMapping
	@ResponseBody
	public Set<CottageQuickReservationDTO> getAll(){
		return cottageQuickReservationService.findAll();
	}
	
	@PostMapping
	//@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageQuickReservationDTO> save(@RequestBody CottageQuickReservationDTO cottageQuickReservationDTO) {
		CottageQuickReservationDTO cottageQuickReservationReturnDTO = cottageQuickReservationService.save(cottageQuickReservationDTO);
		if(cottageQuickReservationReturnDTO == null)
			return new ResponseEntity<>(cottageQuickReservationDTO,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(cottageQuickReservationDTO,HttpStatus.CREATED);
	}
	
	@PostMapping
	//@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageReservationDTO> appointQuickCottageReservation(@RequestBody CottageReservationDTO cottageReservationDTO) {
		CottageReservationDTO cottageReservationReturnDTO = cottageQuickReservationService.appointQuickReservation(cottageReservationDTO);
		if(cottageReservationReturnDTO == null)
			return new ResponseEntity<>(cottageReservationReturnDTO,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(cottageReservationReturnDTO,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<CottageQuickReservationDTO> deleteById(@PathVariable("id") Long id) {
		CottageQuickReservationDTO cottageQuickReservationDTO = cottageQuickReservationService.deleteById(id);
		return new ResponseEntity<>(cottageQuickReservationDTO,HttpStatus.OK);
	}



}
