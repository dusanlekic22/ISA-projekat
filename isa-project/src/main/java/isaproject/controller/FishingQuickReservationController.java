package isaproject.controller;

import java.util.Set;

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

import isaproject.dto.FishingQuickReservationDTO;
import isaproject.dto.FishingReservationDTO;
import isaproject.service.FishingQuickReservationService;
import isaproject.util.ProjectUtil;

@RestController
@RequestMapping(value = "/fishingQuickReservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class FishingQuickReservationController {

	FishingQuickReservationService fishingQuickReservationService;

	@Autowired
	public FishingQuickReservationController(FishingQuickReservationService fishingQuickReservationService) {
		super();
		this.fishingQuickReservationService = fishingQuickReservationService;
	}

	@GetMapping
	public ResponseEntity<Set<FishingQuickReservationDTO>> getAll() {
		return new ResponseEntity<>(fishingQuickReservationService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/notReserved")
	public ResponseEntity<Set<FishingQuickReservationDTO>> getIsReservedFalse() {
		return new ResponseEntity<>(fishingQuickReservationService.findByIsReservedFalse(), HttpStatus.OK);
	}

	@GetMapping("/fishing/{id}/notReserved")
	public ResponseEntity<Set<FishingQuickReservationDTO>> getNotReservedAndByFishingId(@PathVariable("id") Long id) {
		return new ResponseEntity<>(fishingQuickReservationService.findByIsReservedFalseAndFishingCourseId(id),
				HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<FishingQuickReservationDTO> save(
			@RequestBody FishingQuickReservationDTO fishingQuickReservationDTO, HttpServletRequest request) {
		FishingQuickReservationDTO fishingQuickReservationReturnDTO = fishingQuickReservationService
				.save(fishingQuickReservationDTO, ProjectUtil.getSiteURL(request));
		if (fishingQuickReservationReturnDTO == null)
			return new ResponseEntity<>(fishingQuickReservationDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(fishingQuickReservationDTO, HttpStatus.CREATED);
	}

	@GetMapping("/appoint/{reservationId}/user/{id}")
	public ResponseEntity<FishingReservationDTO> appointQuickFishingReservation(
			@PathVariable("reservationId") Long reservationId, @PathVariable("id") Long userId) {
		FishingReservationDTO fishingReservationReturnDTO = fishingQuickReservationService
				.appointQuickReservation(reservationId, userId);
		if (fishingReservationReturnDTO == null)
			return new ResponseEntity<>(fishingReservationReturnDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(fishingReservationReturnDTO, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<FishingQuickReservationDTO> deleteById(@PathVariable("id") Long id) {
		FishingQuickReservationDTO fishingQuickReservationDTO = fishingQuickReservationService.deleteById(id);
		return new ResponseEntity<>(fishingQuickReservationDTO, HttpStatus.OK);
	}
	
	@GetMapping("/fishingTrainer/{id}")
	public ResponseEntity<Set<FishingQuickReservationDTO>> getAllByFishingTrainerId(@PathVariable("id") Long id) {
		return new ResponseEntity<>(fishingQuickReservationService.findByFishingTrainerId(id),
				HttpStatus.OK);
	}

}
