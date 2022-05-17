package isaproject.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.UserChangePasswordDTO;
import isaproject.dto.UserDTO;
import isaproject.exception.ResourceConflictException;
import isaproject.mapper.UserMapper;
import isaproject.model.Admin;
import isaproject.model.User;
import isaproject.service.AdminService;
import isaproject.service.UserService;

@RestController
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

	private AdminService adminService;
	private UserService userService;

	public AdminController(AdminService adminService, UserService userService) {
		super();
		this.adminService = adminService;
		this.userService = userService;
	}

	@PostMapping()
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDTO> addAdmin(@RequestBody UserDTO adminDTO) {
		User existUser = this.userService.findByUsername(adminDTO.getUsername());
		
		if (existUser != null) {
			throw new ResourceConflictException(existUser.getId(), "Username already exists");
		}

		Admin user = this.adminService.registerAdmin(adminDTO);
		
		return new ResponseEntity<>(UserMapper.UserToDTO(user), HttpStatus.CREATED);
	}
	
	@PutMapping("/changePassword")
	@PreAuthorize("hasRole('ADMIN')")
	public  ResponseEntity<?> changePassword(@RequestBody UserChangePasswordDTO adminDTO, Principal user) {
		User existUser = this.userService.findByUsername(user.getName());
		
		if (existUser.getId() != adminDTO.getId()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		this.adminService.changePassword(adminDTO);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/firstTimeLoggedIn")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean changePassword(Principal user) {
		Admin admin = this.adminService.findByUsername(user.getName());
		return admin.getFirstTimeLoggedIn();
	}
	
}
