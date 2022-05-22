package isaproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.UserDTO;
import isaproject.exception.ResourceConflictException;
import isaproject.mapper.UserMapper;
import isaproject.model.CottageOwner;
import isaproject.model.User;
import isaproject.service.CottageOwnerService;
import isaproject.service.UserService;

@RestController
@RequestMapping(value = "/cottageOwner", produces = MediaType.APPLICATION_JSON_VALUE)
public class CottageOwnerController {
	
	private CottageOwnerService cottageOwnerService;
	private UserService userService;

	public CottageOwnerController(CottageOwnerService cottageOwnerService, UserService userService) {
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

}
