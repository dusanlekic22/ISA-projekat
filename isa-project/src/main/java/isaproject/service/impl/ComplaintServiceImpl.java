package isaproject.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isaproject.dto.ComplaintDTO;
import isaproject.mapper.ComplaintMapper;
import isaproject.model.Complaint;
import isaproject.model.Customer;
import isaproject.model.Mail;
import isaproject.model.User;
import isaproject.repository.ComplaintRepository;
import isaproject.repository.CustomerRepository;
import isaproject.repository.UserRepository;
import isaproject.service.ComplaintService;
import isaproject.service.SendMailService;

@Service
public class ComplaintServiceImpl implements ComplaintService {

	private ComplaintRepository complaintRepository;
	private SendMailService mailService;
	private CustomerRepository customerRepository;
	private UserRepository userRepository;

	@Autowired
	public ComplaintServiceImpl(ComplaintRepository complaintRepository, SendMailService mailService,
			CustomerRepository customerRepository, UserRepository userRepository) {
		super();
		this.complaintRepository = complaintRepository;
		this.mailService = mailService;
		this.customerRepository = customerRepository;
		this.userRepository = userRepository;
	}

	@Override
	public ComplaintDTO create(ComplaintDTO complaintDTO) {
		Complaint complaint = ComplaintMapper.DTOToComplaint(complaintDTO);
		return ComplaintMapper.ComplaintToDTO(complaintRepository.save(complaint));
	}

	@Override
	public ComplaintDTO answer(ComplaintDTO complaintDTO, String answerCustomer, String answerOwner) {
		complaintRepository.deleteById(complaintDTO.getId());
		sendAnswerEmailCustomer(getReportedCustomer(complaintDTO).getEmail(), answerCustomer);
		sendAnswerEmailOwner(getReportOwner(complaintDTO).getEmail(), answerOwner);
		return complaintDTO;
	}

	private void sendAnswerEmailOwner(String email, String answerOwner) {
		User user = userRepository.findByEmail(email);
		String subject = "Reservation report";
		StringBuilder content = new StringBuilder("");
		content.append("Hi ").append(user.getFirstName()).append(",<br><br>")
				.append("You have been sent this email because customer complainted.<br><br>").append(answerOwner)
				.append("<br><br>Thank you!<br>Your company name.");

		Mail mail = new Mail(email, subject, content.toString());
		mailService.sendMailHTML(mail);
	}

	private void sendAnswerEmailCustomer(String email, String answerCustomer) {
		User user = userRepository.findByEmail(email);
		String subject = "Reservation report";
		StringBuilder content = new StringBuilder("");
		content.append("Hi ").append(user.getFirstName()).append(",<br><br>")
				.append("You have been sent this email because you sent us complaint.<br><br>").append(answerCustomer)
				.append("<br><br>Thank you!<br>Your company name.");

		Mail mail = new Mail(email, subject, content.toString());
		mailService.sendMailHTML(mail);
	}

	@Override
	public Set<ComplaintDTO> getAllComplaints() {
		Set<ComplaintDTO> dtos = new HashSet<ComplaintDTO>();
		for (Complaint complaint : complaintRepository.findAll()) {
			dtos.add(ComplaintMapper.ComplaintToDTO(complaint));
		}
		return dtos;
	}

	private Customer getReportedCustomer(ComplaintDTO complaint) {
		Customer customer = null;
		if (complaint.getFishingReservation() != null) {
			customer = customerRepository.findById(complaint.getFishingReservation().getCustomer().getId()).get();
		} else if (complaint.getCottageReservation() != null) {
			customer = customerRepository.findById(complaint.getCottageReservation().getCustomer().getId()).get();
		} else if (complaint.getBoatReservation() != null) {
			customer = customerRepository.findById(complaint.getBoatReservation().getCustomer().getId()).get();
		}
		return customer;
	}

	private User getReportOwner(ComplaintDTO complaint) {
		User user = null;
		if (complaint.getFishingReservation() != null) {
			user = userRepository
					.findById(complaint.getFishingReservation().getFishingCourse().getFishingTrainer().getId()).get();
		} else if (complaint.getCottageReservation() != null) {
			user = userRepository.findById(complaint.getCottageReservation().getCottage().getCottageOwner().getId())
					.get();
		} else if (complaint.getBoatReservation() != null) {
			user = userRepository.findById(complaint.getBoatReservation().getBoat().getBoatOwner().getId()).get();
		}
		return user;
	}
}
