package isaproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import isaproject.config.PasswordEncoderService;
import isaproject.model.User;
import isaproject.repository.UserRepository;
import isaproject.service.RoleService;
import isaproject.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private PasswordEncoderService passwordEncoderService;
	private RoleService roleService;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoderService passwordEncoderService, RoleService roleService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoderService = passwordEncoderService;
		this.roleService = roleService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		return user;
	}
	
	@Override
	public Boolean loadUserByUsernames(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) return false;
		return true;
	}
	
}
