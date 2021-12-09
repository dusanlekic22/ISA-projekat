package isaproject.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import isaproject.model.Customer;

public interface CustomerService {
	
	 public void register(Customer user, String siteURL)throws UnsupportedEncodingException, MessagingException;
	     
	 public void sendVerificationEmail(Customer user, String siteURL)throws UnsupportedEncodingException, MessagingException;
	    
	 public boolean verify(String verificationCode);
}
