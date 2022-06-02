package isaproject.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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

	@PostMapping()
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<ReservationReportDTO> create(
			@RequestBody ReservationReportDTO reservationReportDTO) {
		ReservationReportDTO reservationReport = reservationReportService.create(reservationReportDTO);
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

}
