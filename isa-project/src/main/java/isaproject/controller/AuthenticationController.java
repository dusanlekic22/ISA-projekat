package isaproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.exception.ResourceConflictException;
import isaproject.model.User;
import isaproject.security.authority.JWToken;
import isaproject.service.UserService;

//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	private JWToken jwToken;
	private AuthenticationManager authenticationManager;
	private UserService userService;
	
	@Autowired
	public AuthenticationController(JWToken jwToken, UserService userService, AuthenticationManager authenticationManager) {
		this.jwToken = jwToken;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}
	
	// Prvi endpoint koji pogadja korisnik kada se loguje.
	// Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
//	@PostMapping("/login")
//	public ResponseEntity<UserTokenState> createAuthenticationToken(
//			@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {
//
//		// Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
//		// AuthenticationException
//		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//				authenticationRequest.getUsername(), authenticationRequest.getPassword()));
//
//		// Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
//		// kontekst
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		// Kreiraj token za tog korisnika
//		User user = (User) authentication.getPrincipal();
//		String jwt = tokenUtils.generateToken(user.getUsername());
//		int expiresIn = tokenUtils.getExpiredIn();
//
//		// Vrati token kao odgovor na uspesnu autentifikaciju
//		return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
//	}

}
