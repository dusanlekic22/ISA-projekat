package isaproject.controller.cottage;

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
import isaproject.dto.cottage.CottageOwnerDTO;
import isaproject.exception.ResourceConflictException;
import isaproject.mapper.UserMapper;
import isaproject.model.DateTimeSpan;
import isaproject.model.User;
import isaproject.model.cottage.CottageOwner;
import isaproject.service.CustomerService;
import isaproject.service.UserService;
import isaproject.service.cottage.CottageOwnerService;

@RestController
@RequestMapping(value = "/cottageOwner", produces = MediaType.APPLICATION_JSON_VALUE)
public class CottageOwnerController {
	
	private CottageOwnerService cottageOwnerService;
	private UserService userService;

	@Autowired
	public CottageOwnerController(CottageOwnerService cottageOwnerService, UserService userService, CustomerService customerService) {
		super();
		this.cottageOwnerService = cottageOwnerService;
		this.userService = userService;
	}


	@PostMapping("/signup")
	public ResponseEntity<UserDTO> addUser(@RequestBody BusinessOwnerDTO businessOwnerDTO) {
		User existUser = this.userService.findByUsername(businessOwnerDTO.getUsername());
		
		if (existUser != null) {
			throw new ResourceConflictException(existUser.getId(), "Username already exists");
		}
		
		CottageOwner user = this.cottageOwnerService.registerCottageOwner(businessOwnerDTO);
		
		return new ResponseEntity<>(UserMapper.UserToDTO(user), HttpStatus.CREATED);
	}
	
	@GetMapping
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageOwnerDTO> get(Principal user) {
		CottageOwnerDTO cottageOwnerDTO = cottageOwnerService.findByUsername(user.getName());
		return new ResponseEntity<>(cottageOwnerDTO, HttpStatus.OK);
	}
	
	@PutMapping("/unavailableTerms/{id}")
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageOwnerDTO> updateUnavailableTerms(@PathVariable("id") Long id,
			@RequestBody DateTimeSpan dateTimeSpan) {
		CottageOwnerDTO cottageOwnerDTO = cottageOwnerService.updateUnavailableTerms(id, dateTimeSpan);
		if (cottageOwnerDTO == null)
			return new ResponseEntity<>(cottageOwnerDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(cottageOwnerDTO, HttpStatus.OK);
	}

}
