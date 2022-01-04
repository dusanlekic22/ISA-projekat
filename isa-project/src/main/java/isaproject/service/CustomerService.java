package isaproject.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import isaproject.dto.UserDTO;
import isaproject.model.User;

public interface CustomerService {
	
	 public void register(UserDTO user, String siteURL)throws UnsupportedEncodingException, MessagingException;
	     
	 public void sendVerificationEmail(User user, String siteURL)throws UnsupportedEncodingException, MessagingException;
	    
	 public boolean verify(String verificationCode);
}
