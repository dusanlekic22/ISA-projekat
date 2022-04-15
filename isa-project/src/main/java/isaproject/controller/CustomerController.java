package isaproject.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.CustomerDTO;
import isaproject.exception.MethodPathParamterNotValidException;
import isaproject.exception.ResourceConflictException;
import isaproject.model.User;
import isaproject.service.CustomerService;
import isaproject.service.UserService;

@RestController
@RequestMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {
	
	@Autowired
    private CustomerService customerService;
	@Autowired
	private UserService userService;
     
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
            return new ResponseEntity<>("verify_success",HttpStatus.OK);
        } else {
            throw new MethodPathParamterNotValidException("Verification code not valid");
        }
    }
     
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }  
}
