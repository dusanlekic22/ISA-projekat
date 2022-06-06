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

import isaproject.dto.ComplaintDTO;
import isaproject.service.ComplaintService;

@RestController
@RequestMapping(value = "/complaint", produces = MediaType.APPLICATION_JSON_VALUE)
public class ComplaintController {

	private ComplaintService complaintService;

	@Autowired
	public ComplaintController(ComplaintService complaintService) {
		super();
		this.complaintService = complaintService;
	}

	@PostMapping
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<ComplaintDTO> create(@RequestBody ComplaintDTO complaintDTO) {
		ComplaintDTO complaint = complaintService.create(complaintDTO);
		return new ResponseEntity<>(complaint, HttpStatus.OK);
	}

	@PostMapping("/answer")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ComplaintDTO> answer(@RequestBody ComplaintDTO complaintDTO) {
		ComplaintDTO complaint = complaintService.answer(complaintDTO, complaintDTO.getAnswerCustomer(),
				complaintDTO.getAnswerOwner());
		return new ResponseEntity<>(complaint, HttpStatus.OK);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Set<ComplaintDTO>> getAllComplaints() {
		Set<ComplaintDTO> complaint = complaintService.getAllComplaints();
		return new ResponseEntity<Set<ComplaintDTO>>(complaint, HttpStatus.OK);
	}

}
