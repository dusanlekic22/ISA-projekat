package isaproject.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import isaproject.dto.CustomerDTO;
import isaproject.model.CottageQuickReservation;
import isaproject.model.CottageReservation;
import isaproject.model.Customer;
import isaproject.model.FishingQuickReservation;
import isaproject.model.FishingReservation;

public interface CustomerService {

	public CustomerDTO getCustomer(long customerId);

	public void register(CustomerDTO customerDTO, String siteURL)
			throws UnsupportedEncodingException, MessagingException;

	public void sendVerificationEmail(Customer user, String siteURL)
			throws UnsupportedEncodingException, MessagingException;

	public void sendNewQuickReservationEmail(Customer user, String siteURL, CottageQuickReservation cottageQuickReservation)
			throws UnsupportedEncodingException, MessagingException;
	
	public void sendReservationConfirmationEmail(String siteURL, CottageReservation cottageReservationReturn)
			throws UnsupportedEncodingException, MessagingException;

	public boolean verify(String verificationCode);

	public void sendNewQuickReservationEmail(Customer customer, String siteUrl,
			FishingQuickReservation fishingQuickReservationReturn);

	public void sendReservationConfirmationEmail(String siteUrl, FishingReservation fishingReservationReturn);
}
