package isaproject.controller.boat;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.UserDTO;
import isaproject.dto.boat.BoatOwnerDTO;
import isaproject.exception.ResourceConflictException;
import isaproject.mapper.UserMapper;
import isaproject.model.DateTimeSpan;
import isaproject.model.User;
import isaproject.model.boat.BoatOwner;
import isaproject.service.CustomerService;
import isaproject.service.UserService;
import isaproject.service.boat.BoatOwnerService;

@RestController
@RequestMapping(value = "/boatOwner", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoatOwnerController {
	
	private BoatOwnerService boatOwnerService;
	private UserService userService;

	@Autowired
	public BoatOwnerController(BoatOwnerService boatOwnerService, UserService userService, CustomerService customerService) {
		super();
		this.boatOwnerService = boatOwnerService;
		this.userService = userService;
	}


	@PostMapping("/signup")
	public ResponseEntity<UserDTO> addUser(@RequestBody BusinessOwnerDTO businessOwnerDTO) {
		User existUser = this.userService.findByUsername(businessOwnerDTO.getUsername());
		
		if (existUser != null) {
			throw new ResourceConflictException(existUser.getId(), "Username already exists");
		}
		
		BoatOwner user = this.boatOwnerService.registerBoatOwner(businessOwnerDTO);
		
		return new ResponseEntity<>(UserMapper.UserToDTO(user), HttpStatus.CREATED);
	}

	@GetMapping
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatOwnerDTO> get(Principal user) {
		BoatOwnerDTO boatOwnerDTO = boatOwnerService.findByUsername(user.getName());
		return new ResponseEntity<>(boatOwnerDTO, HttpStatus.OK);
	}
	
	@PutMapping("/unavailableTerms/{id}")
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatOwnerDTO> updateUnavailableTerms(@PathVariable("id") Long id,
			@RequestBody DateTimeSpan dateTimeSpan) {
		BoatOwnerDTO boatOwnerDTO = boatOwnerService.updateUnavailableTerms(id, dateTimeSpan);
		if (boatOwnerDTO == null)
			return new ResponseEntity<>(boatOwnerDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(boatOwnerDTO, HttpStatus.OK);
	}
	
}
