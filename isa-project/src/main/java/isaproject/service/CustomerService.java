package isaproject.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import isaproject.dto.CustomerDTO;
import isaproject.model.Customer;
import isaproject.model.boat.BoatQuickReservation;
import isaproject.model.boat.BoatReservation;
import isaproject.model.cottage.CottageQuickReservation;
import isaproject.model.cottage.CottageReservation;

public interface CustomerService {

	public CustomerDTO getCustomer(long customerId);

	public void register(CustomerDTO customerDTO, String siteURL)
			throws UnsupportedEncodingException, MessagingException;

	public void sendVerificationEmail(Customer user, String siteURL)
			throws UnsupportedEncodingException, MessagingException;

	public void sendNewQuickReservationEmail(Customer user, String siteURL, CottageQuickReservation cottageQuickReservation)
			throws UnsupportedEncodingException, MessagingException;
	
	public void sendNewQuickReservationEmail(Customer user, String siteURL, BoatQuickReservation boatQuickReservation)
			throws UnsupportedEncodingException, MessagingException;
	
	public void sendReservationConfirmationEmail(String siteURL, CottageReservation cottageReservation)
			throws UnsupportedEncodingException, MessagingException;
	
	public void sendReservationConfirmationEmail(String siteURL, BoatReservation boatReservation)
			throws UnsupportedEncodingException, MessagingException;


	public boolean verify(String verificationCode);
}
