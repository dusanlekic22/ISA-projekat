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

import isaproject.dto.CustomerDTO;
import isaproject.dto.boat.BoatReservationDTO;
import isaproject.service.boat.BoatReservationService;
import isaproject.util.ProjectUtil;

@RestController
@RequestMapping(value = "/boatReservation", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class BoatReservationController {

	@Autowired
	BoatReservationService BoatReservationService;

	@GetMapping
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Set<BoatReservationDTO>> getAll() {
		return new ResponseEntity<>(BoatReservationService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/customerHasReservationNow")
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Set<CustomerDTO>> findCustomersHasCurrentReservation() {
		return new ResponseEntity<>(BoatReservationService.findCustomersHasCurrentReservation(), HttpStatus.OK);
	}

	@GetMapping("/passed")
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Set<BoatReservationDTO>> getAllPassed() {
		return new ResponseEntity<>(BoatReservationService.findAllPast(), HttpStatus.OK);
	}


	@GetMapping("/active")
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Set<BoatReservationDTO>> getAllActive() {
		return new ResponseEntity<>(BoatReservationService.findAllActive(), HttpStatus.OK);
	}

	@GetMapping("/active/{boatId}")
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Set<BoatReservationDTO>> getAllActiveByBoatId(@PathVariable("boatId") Long id) {
		return new ResponseEntity<>(BoatReservationService.findAllActiveByBoatId(id), HttpStatus.OK);
	}

	@GetMapping("/passed/{boatId}")
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Set<BoatReservationDTO>> getAllPassedByBoatId(@PathVariable("boatId") Long id) {
		return new ResponseEntity<>(BoatReservationService.findAllPastByBoatId(id), HttpStatus.OK);
	}

	@PostMapping("/owner")
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatReservationDTO> reserveOwner(@RequestBody BoatReservationDTO BoatReservationDTO,
			HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		BoatReservationDTO BoatReservationReturnDTO = BoatReservationService
				.reserveBoatOwner(BoatReservationDTO, ProjectUtil.getSiteURL(request));
		if (BoatReservationReturnDTO == null)
			return new ResponseEntity<>(BoatReservationReturnDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(BoatReservationReturnDTO, HttpStatus.CREATED);
	}

	@PostMapping("/customer")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<BoatReservationDTO> reserveCustomer(
			@RequestBody BoatReservationDTO BoatReservationDTO) {
		BoatReservationDTO BoatReservationReturnDTO = BoatReservationService
				.reserveCustomer(BoatReservationDTO);
		if (BoatReservationReturnDTO == null)
			return new ResponseEntity<>(BoatReservationReturnDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(BoatReservationReturnDTO, HttpStatus.CREATED);
	}

	@GetMapping("/confirm/{id}")
	public ResponseEntity<BoatReservationDTO> confirmReservation(@PathVariable("id") Long id) {
		BoatReservationDTO BoatReservationDTO = BoatReservationService.confirmReservation(id);
		if (BoatReservationDTO == null)
			return new ResponseEntity<>(BoatReservationDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(BoatReservationDTO, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<BoatReservationDTO> deleteById(@PathVariable("id") Long id) {
		BoatReservationDTO BoatReservationDTO = BoatReservationService.deleteById(id);
		return new ResponseEntity<>(BoatReservationDTO, HttpStatus.OK);
	}

}
