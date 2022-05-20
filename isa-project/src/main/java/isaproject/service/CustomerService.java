package isaproject.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import isaproject.dto.CustomerDTO;
import isaproject.model.CottageQuickReservation;
import isaproject.model.CottageReservation;
import isaproject.model.Customer;

public interface CustomerService {

	public void register(CustomerDTO customerDTO, String siteURL)
			throws UnsupportedEncodingException, MessagingException;

	public void sendVerificationEmail(Customer user, String siteURL)
			throws UnsupportedEncodingException, MessagingException;

	public void sendNewQuickReservationEmail(Customer user, String siteURL, CottageQuickReservation cottageQuickReservation)
			throws UnsupportedEncodingException, MessagingException;
	
	public void sendReservationConfirmationEmail(String siteURL, CottageReservation cottageReservation)
			throws UnsupportedEncodingException, MessagingException;

	public boolean verify(String verificationCode);
}
