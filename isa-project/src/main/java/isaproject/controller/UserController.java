package isaproject.controller;

import org.springframework.beans.factory.annotation.Autowired;

import isaproject.service.UserService;

public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	
}
