package isaproject.controller;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.CustomerDTO;
import isaproject.dto.FishingReservationDTO;
import isaproject.service.FishingReservationService;
import isaproject.util.ProjectUtil;

@RestController
@RequestMapping(value = "/fishingReservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class FishingReservationController {

	FishingReservationService fishingReservationService;

	@Autowired
	public FishingReservationController(FishingReservationService fishingReservationService) {
		super();
		this.fishingReservationService = fishingReservationService;
	}

	@GetMapping
	public ResponseEntity<Set<FishingReservationDTO>> getAll() {
		return new ResponseEntity<>(fishingReservationService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/customerHasReservationNow")
	public ResponseEntity<Set<CustomerDTO>> findCustomersHasCurrentReservation() {
		return new ResponseEntity<>(fishingReservationService.findCustomersHasCurrentReservation(), HttpStatus.OK);
	}

	@GetMapping("/passed")
	public ResponseEntity<Set<FishingReservationDTO>> getAllPassed() {
		return new ResponseEntity<>(fishingReservationService.findAllPast(), HttpStatus.OK);
	}

	@GetMapping("/active")
	public ResponseEntity<Set<FishingReservationDTO>> getAllActive() {
		return new ResponseEntity<>(fishingReservationService.findAllActive(), HttpStatus.OK);
	}

	@GetMapping("/active/{fishingId}")
	public ResponseEntity<Set<FishingReservationDTO>> getAllActiveByFishingId(@PathVariable("fishingId") Long id) {
		return new ResponseEntity<>(fishingReservationService.findAllActiveByFishingCourseId(id), HttpStatus.OK);
	}

	@GetMapping("/passed/{fishingId}")
	public ResponseEntity<Set<FishingReservationDTO>> getAllPassedByFishingId(@PathVariable("fishingId") Long id) {
		return new ResponseEntity<>(fishingReservationService.findAllPastByFishingCourseId(id), HttpStatus.OK);
	}
	
	@GetMapping("/active/trainer/{fishingTrainerId}")
	public ResponseEntity<Set<FishingReservationDTO>> getAllActiveByFishingTrainerIdId(@PathVariable("fishingTrainerId") Long id) {
		return new ResponseEntity<>(fishingReservationService.findAllActiveByFishingTrainerId(id), HttpStatus.OK);
	}

	@GetMapping("/passed/trainer/{fishingTrainerId}")
	public ResponseEntity<Set<FishingReservationDTO>> getAllPassedByFishingTrainerIdId(@PathVariable("fishingTrainerId") Long id) {
		return new ResponseEntity<>(fishingReservationService.findAllPastByFishingTrainerId(id), HttpStatus.OK);
	}

	@PostMapping("/owner")
	public ResponseEntity<FishingReservationDTO> reserveOwner(@RequestBody FishingReservationDTO fishingReservationDTO,
			HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		FishingReservationDTO fishingReservationReturnDTO = fishingReservationService
				.reserveFishingOwner(fishingReservationDTO, ProjectUtil.getSiteURL(request));
		if (fishingReservationReturnDTO == null)
			return new ResponseEntity<>(fishingReservationReturnDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(fishingReservationReturnDTO, HttpStatus.CREATED);
	}

	@PostMapping("/customer")
	public ResponseEntity<FishingReservationDTO> reserveCustomer(
			@RequestBody FishingReservationDTO fishingReservationDTO) {
		FishingReservationDTO fishingReservationReturnDTO = fishingReservationService
				.reserveCustomer(fishingReservationDTO);
		if (fishingReservationReturnDTO == null)
			return new ResponseEntity<>(fishingReservationReturnDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(fishingReservationReturnDTO, HttpStatus.CREATED);
	}

	@GetMapping("/confirm/{id}")
	public ResponseEntity<FishingReservationDTO> confirmReservation(@PathVariable("id") Long id) {
		FishingReservationDTO fishingReservationDTO = fishingReservationService.confirmReservation(id);
		if (fishingReservationDTO == null)
			return new ResponseEntity<>(fishingReservationDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(fishingReservationDTO, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<FishingReservationDTO> deleteById(@PathVariable("id") Long id) {
		FishingReservationDTO fishingReservationDTO = fishingReservationService.deleteById(id);
		return new ResponseEntity<>(fishingReservationDTO, HttpStatus.OK);
	}

}
