package isaproject.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.CreateUserDeletionRequestDTO;
import isaproject.dto.UserDeletionRequestDTO;
import isaproject.mapper.RequestMapper;
import isaproject.model.UserDeletionRequest;
import isaproject.service.UserDeletionService;

@RestController
@RequestMapping(value = "/userDeletion", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserDeletionContoller {

	private UserDeletionService deletionService;

	@PostMapping()
	@PreAuthorize("hasAnyRole('COTTAGE_OWNER', 'CUSTOMER', 'FISHING_TRAINER')")
	public ResponseEntity<UserDeletionRequestDTO> create(@RequestBody CreateUserDeletionRequestDTO deletionRequestDTO) {
		UserDeletionRequest deletionRequest = deletionService.create(deletionRequestDTO);
		UserDeletionRequestDTO requestDTO = RequestMapper.UserDeletionRequestToDTO(deletionRequest);
		return new ResponseEntity<UserDeletionRequestDTO>(requestDTO, HttpStatus.OK);
	}
	
	@PostMapping("/accept")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDeletionRequestDTO> approve(@RequestBody UserDeletionRequestDTO deletionRequestDTO) {
		UserDeletionRequest deletionRequest = deletionService.approve(deletionRequestDTO, deletionRequestDTO.getAnswer());
		UserDeletionRequestDTO requestDTO = RequestMapper.UserDeletionRequestToDTO(deletionRequest);
		return new ResponseEntity<UserDeletionRequestDTO>(requestDTO, HttpStatus.OK);
	}

	@PostMapping("/decline")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDeletionRequestDTO> decline(@RequestBody UserDeletionRequestDTO deletionRequestDTO) {
		UserDeletionRequest deletionRequest = deletionService.decline(deletionRequestDTO, deletionRequestDTO.getAnswer());
		UserDeletionRequestDTO requestDTO = RequestMapper.UserDeletionRequestToDTO(deletionRequest);
		return new ResponseEntity<UserDeletionRequestDTO>(requestDTO, HttpStatus.OK);
	}

	@GetMapping("/deletionRequests")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserDeletionRequestDTO>> getUserDeletionRequests() {
		List<UserDeletionRequestDTO> deletionRequests = deletionService.getAllUserDeletionRequests();
		return new ResponseEntity<List<UserDeletionRequestDTO>>(deletionRequests, HttpStatus.OK);
	}
	
}
