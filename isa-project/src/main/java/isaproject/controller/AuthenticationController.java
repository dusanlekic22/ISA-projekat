package isaproject.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.CustomerDTO;
import isaproject.dto.JwtAuthenticationRequest;
import isaproject.dto.UserDTO;
import isaproject.dto.UserTokenState;
import isaproject.exception.ResourceConflictException;
import isaproject.model.User;
import isaproject.security.authority.JWToken;
import isaproject.service.CustomerService;
import isaproject.service.UserService;

//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	private JWToken jwToken;
	private AuthenticationManager authenticationManager;
	private UserService userService;
	private CustomerService customerService;
	

	@Autowired
	public AuthenticationController(JWToken jwToken, UserService userService,
			AuthenticationManager authenticationManager, CustomerService customerService) {
		this.jwToken = jwToken;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.customerService = customerService;
	}
	
	// Prvi endpoint koji pogadja korisnik kada se loguje.
	// Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
	@PostMapping("/login")
	public ResponseEntity<UserTokenState> createAuthenticationToken(
			@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

		// Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
		// AuthenticationException
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		// Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
		// kontekst
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token za tog korisnika
		User user = (User) authentication.getPrincipal();
		String jwt = jwToken.generateToken(user.getUsername());
		int expiresIn = jwToken.getExpiredIn();

		// Vrati token kao odgovor na uspesnu autentifikaciju
		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
	}

	// Endpoint za registraciju novog korisnika
	@PostMapping("/signup")
	public ResponseEntity<UserDTO> addUser(@RequestBody CustomerDTO userRequest, HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {
		
		User existUser = this.userService.findByUsername(userRequest.getUsername());
		
		if (existUser != null) {
			throw new ResourceConflictException(existUser.getId(), "Username already exists");
		}
		
		this.customerService.register(userRequest, getSiteURL(request));
		return new ResponseEntity<>(userRequest, HttpStatus.CREATED);
	}

	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code) {
		if (this.customerService.verify(code)) {
			return "verify_success";
		} else {
			return "verify_fail";
		}
	}

	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}

}
