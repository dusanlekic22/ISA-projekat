package isaproject.controller.cottage;

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

import isaproject.dto.cottage.CottageQuickReservationDTO;
import isaproject.dto.cottage.CottageReservationDTO;
import isaproject.service.cottage.CottageQuickReservationService;
import isaproject.util.ProjectUtil;

@RestController
@RequestMapping(value = "/cottageQuickReservation", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class CottageQuickReservationController {
	
	@Autowired
	CottageQuickReservationService cottageQuickReservationService;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<Set<CottageQuickReservationDTO>> getAll(){
		return new ResponseEntity<>(cottageQuickReservationService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("/notReserved")
    @PreAuthorize("hasRole('COTTAGE_OWNER')")
	@ResponseBody
	public ResponseEntity<Set<CottageQuickReservationDTO>> getIsReservedFalse(){
		return new ResponseEntity<>(cottageQuickReservationService.findByIsReservedFalse(),HttpStatus.OK);
	}
	
	@GetMapping("/cottage/{id}/notReserved")
    @PreAuthorize("hasRole('COTTAGE_OWNER')")
	@ResponseBody
	public ResponseEntity<Set<CottageQuickReservationDTO>> getNotReservedAndByCottageId(@PathVariable("id")Long id){
		return new ResponseEntity<>(cottageQuickReservationService.findByIsReservedFalseAndCottageId(id),HttpStatus.OK);
	}
	
	
	@PostMapping
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageQuickReservationDTO> save(
			@RequestBody CottageQuickReservationDTO cottageQuickReservationDTO,HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {
		CottageQuickReservationDTO cottageQuickReservationReturnDTO = cottageQuickReservationService.save(cottageQuickReservationDTO,ProjectUtil.getSiteURL(request));
		if(cottageQuickReservationReturnDTO == null)
			return new ResponseEntity<>(cottageQuickReservationReturnDTO,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(cottageQuickReservationReturnDTO,HttpStatus.CREATED);
	}
	
	@GetMapping("/appoint/{reservationId}/user/{id}")
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageReservationDTO> appointQuickCottageReservation(@PathVariable("reservationId") Long reservationId,@PathVariable("id") Long userId) {
		CottageReservationDTO cottageReservationReturnDTO = cottageQuickReservationService.appointQuickReservation(reservationId,userId);
		if(cottageReservationReturnDTO == null)
			return new ResponseEntity<>(cottageReservationReturnDTO,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(cottageReservationReturnDTO,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
    @PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageQuickReservationDTO> deleteById(@PathVariable("id") Long id) {
		CottageQuickReservationDTO cottageQuickReservationDTO = cottageQuickReservationService.deleteById(id);
		return new ResponseEntity<>(cottageQuickReservationDTO,HttpStatus.OK);
	}
	

}
