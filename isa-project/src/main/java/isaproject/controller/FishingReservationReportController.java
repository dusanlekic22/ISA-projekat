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

import isaproject.dto.FishingReservationReportDTO;
import isaproject.service.FishingReservationReportService;

@RestController
@RequestMapping(value = "/fishingReservationReport", produces = MediaType.APPLICATION_JSON_VALUE)
public class FishingReservationReportController {

	FishingReservationReportService fishingReservationReportService;

	@Autowired
	public FishingReservationReportController(FishingReservationReportService fishingReservationReportService) {
		super();
		this.fishingReservationReportService = fishingReservationReportService;
	}

	@PostMapping()
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<FishingReservationReportDTO> create(
			@RequestBody FishingReservationReportDTO reservationReportDTO) {
		FishingReservationReportDTO reservationReport = fishingReservationReportService.create(reservationReportDTO);
		return new ResponseEntity<>(reservationReport, HttpStatus.OK);
	}

	@PostMapping("/approve")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<FishingReservationReportDTO> approve(
			@RequestBody FishingReservationReportDTO reservationReportDTO) {
		FishingReservationReportDTO reservationReport = fishingReservationReportService.approve(reservationReportDTO,
				reservationReportDTO.getAnswerCustomer(), reservationReportDTO.getAnswerOwner());
		return new ResponseEntity<>(reservationReport, HttpStatus.OK);
	}

	@PostMapping("/decline")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<FishingReservationReportDTO> decline(
			@RequestBody FishingReservationReportDTO reservationReportDTO) {
		FishingReservationReportDTO reservationReport = fishingReservationReportService.decline(reservationReportDTO,
				reservationReportDTO.getAnswerCustomer(), reservationReportDTO.getAnswerOwner());
		return new ResponseEntity<>(reservationReport, HttpStatus.OK);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Set<FishingReservationReportDTO>> getAllReservationReports() {
		Set<FishingReservationReportDTO> reservationReports = fishingReservationReportService
				.getAllFishingReservationReports();
		return new ResponseEntity<Set<FishingReservationReportDTO>>(reservationReports, HttpStatus.OK);
	}

}
