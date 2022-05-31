package isaproject.controller.boat;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.boat.BoatQuickReservationDTO;
import isaproject.dto.boat.BoatReservationDTO;
import isaproject.service.boat.BoatQuickReservationService;
import isaproject.util.ProjectUtil;

@RestController
@RequestMapping(value = "/boatQuickReservation", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class BoatQuickReservationController {
	
	@Autowired
	BoatQuickReservationService boatQuickReservationService;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<Set<BoatQuickReservationDTO>> getAll(){
		return new ResponseEntity<>(boatQuickReservationService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/boat/{id}")
	@ResponseBody
	public ResponseEntity<Set<BoatQuickReservationDTO>> getByBoatId(@PathVariable("id")Long id){
		return new ResponseEntity<>(boatQuickReservationService.findByBoatId(id),HttpStatus.OK);
	}
	
	@GetMapping("/notReserved")
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Set<BoatQuickReservationDTO>> getIsReservedFalse(){
		return new ResponseEntity<>(boatQuickReservationService.findByIsReservedFalse(),HttpStatus.OK);
	}
	
	@GetMapping("/boat/{id}/notReserved")
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Set<BoatQuickReservationDTO>> getNotReservedAndByBoatId(@PathVariable("id")Long id){
		return new ResponseEntity<>(boatQuickReservationService.findByIsReservedFalseAndBoatId(id),HttpStatus.OK);
	}
	
	
	@PostMapping
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatQuickReservationDTO> save(
			@RequestBody BoatQuickReservationDTO boatQuickReservationDTO,HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {
		BoatQuickReservationDTO boatQuickReservationReturnDTO = boatQuickReservationService.save(boatQuickReservationDTO,ProjectUtil.getSiteURL(request));
		if(boatQuickReservationReturnDTO == null)
			return new ResponseEntity<>(boatQuickReservationReturnDTO,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(boatQuickReservationReturnDTO,HttpStatus.CREATED);
	}
	
	@GetMapping("/appoint/{reservationId}/user/{id}")
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatReservationDTO> appointQuickBoatReservation(@PathVariable("reservationId") Long reservationId,@PathVariable("id") Long userId) {
		BoatReservationDTO boatReservationReturnDTO = boatQuickReservationService.appointQuickReservation(reservationId,userId);
		if(boatReservationReturnDTO == null)
			return new ResponseEntity<>(boatReservationReturnDTO,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(boatReservationReturnDTO,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatQuickReservationDTO> deleteById(@PathVariable("id") Long id) {
		BoatQuickReservationDTO boatQuickReservationDTO = boatQuickReservationService.deleteById(id);
		return new ResponseEntity<>(boatQuickReservationDTO,HttpStatus.OK);
	}
	

}
