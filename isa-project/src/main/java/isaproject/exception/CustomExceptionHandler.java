package isaproject.exception;

import java.security.InvalidParameterException;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
class CustomExceptionHandler extends BaseExceptionHandler {

	public CustomExceptionHandler() {
		registerMapping(UsernameNotFoundException.class, HttpStatus.NOT_FOUND);
		registerMapping(DisabledException.class, HttpStatus.UNAUTHORIZED);
		registerMapping(InvalidParameterException.class, HttpStatus.BAD_REQUEST);
		registerMapping(AccessDeniedException.class, HttpStatus.UNAUTHORIZED);
		registerMapping(EntityNotFoundException.class, HttpStatus.NOT_FOUND);
	}
}