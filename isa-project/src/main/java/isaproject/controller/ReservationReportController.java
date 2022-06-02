package isaproject.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.ReservationReportDTO;
import isaproject.service.ReservationReportService;

@RestController
@RequestMapping(value = "/reservationReport", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationReportController {

	ReservationReportService reservationReportService;

	@Autowired
	public ReservationReportController(ReservationReportService reservationReportService) {
		super();
		this.reservationReportService = reservationReportService;
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('FISHING_TRAINER', 'COTTAGE_OWNER', 'BOAT_OWNER')")
	public ResponseEntity<ReservationReportDTO> create(
			@RequestBody ReservationReportDTO reservationReportDTO) {
		ReservationReportDTO reservationReport = reservationReportService.create(reservationReportDTO);
		if (reservationReport == null) {
			return new ResponseEntity<>(reservationReport, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(reservationReport, HttpStatus.OK);
	}

	@PostMapping("/approve")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ReservationReportDTO> approve(
			@RequestBody ReservationReportDTO reservationReportDTO) {
		ReservationReportDTO reservationReport = reservationReportService.approve(reservationReportDTO,
				reservationReportDTO.getAnswerCustomer(), reservationReportDTO.getAnswerOwner());
		return new ResponseEntity<>(reservationReport, HttpStatus.OK);
	}

	@PostMapping("/decline")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ReservationReportDTO> decline(
			@RequestBody ReservationReportDTO reservationReportDTO) {
		ReservationReportDTO reservationReport = reservationReportService.decline(reservationReportDTO,
				reservationReportDTO.getAnswerCustomer(), reservationReportDTO.getAnswerOwner());
		return new ResponseEntity<>(reservationReport, HttpStatus.OK);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Set<ReservationReportDTO>> getAllReservationReports() {
		Set<ReservationReportDTO> reservationReports = reservationReportService
				.getAllReservationReports();
		return new ResponseEntity<Set<ReservationReportDTO>>(reservationReports, HttpStatus.OK);
	}
	
	@GetMapping("/fishingReservation/{id}")
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<Boolean> isReportedByFishingTrainer(@PathVariable("id") Long reservationId) {
		Boolean reported = reservationReportService.isReportedByFishingTrainer(reservationId);
		return new ResponseEntity<Boolean>(reported, HttpStatus.OK);
	}
	
	@GetMapping("/cottageReservation/{id}")
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<Boolean> isReportedByCottageOwner(@PathVariable("id") Long reservationId) {
		Boolean reported = reservationReportService.isReportedByCottageOwner(reservationId);
		return new ResponseEntity<Boolean>(reported, HttpStatus.OK);
	}
	
	@GetMapping("/boatReservation/{id}")
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Boolean> isReportedByBoatOwner(@PathVariable("id") Long reservationId) {
		Boolean reported = reservationReportService.isReportedByBoatOwner(reservationId);
		return new ResponseEntity<Boolean>(reported, HttpStatus.OK);
	}

}
