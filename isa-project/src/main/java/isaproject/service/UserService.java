package isaproject.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import isaproject.model.User;

public interface UserService extends UserDetailsService {
	public Boolean loadUserByUsernames(String username);
}