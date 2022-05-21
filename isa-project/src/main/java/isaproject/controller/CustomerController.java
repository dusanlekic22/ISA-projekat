package isaproject.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.security.Principal;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.CustomerDTO;
import isaproject.exception.MethodPathParamterNotValidException;
import isaproject.exception.UserAlreadyExistAuthenticationException;
import isaproject.model.User;
import isaproject.service.CustomerService;
import isaproject.service.UserService;

@RestController
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<CustomerDTO> getCustomer(@PathVariable(value = "id") Long customerId,Principal user,
			HttpServletRequest request) {

		CustomerDTO customer = this.customerService.getCustomer(customerId);
		if(!user.getName().equals(customer.getUsername())) {
			throw new InvalidParameterException();
		}
		return new ResponseEntity<>(customer, HttpStatus.CREATED);
	}

	// Endpoint za registraciju novog korisnika
	@PostMapping("/signup")
	public ResponseEntity<CustomerDTO> addUser(@RequestBody CustomerDTO userRequest, HttpServletRequest request)
			throws UnsupportedEncodingException, MessagingException {

		User existUser = this.userService.findByUsername(userRequest.getUsername());

		if (existUser != null) {
			throw new UserAlreadyExistAuthenticationException("Username already exists");
		}

		this.customerService.register(userRequest, getSiteURL(request));
		return new ResponseEntity<>(userRequest, HttpStatus.CREATED);
	}

	@GetMapping("/verify")
	public ResponseEntity<String> verifyUser(@Param("code") String code) {
		if (customerService.verify(code)) {
			return new ResponseEntity<>("verify_success", HttpStatus.OK);
		} else {
			throw new MethodPathParamterNotValidException("Verification code not valid");
		}
	}

	private String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}
}
