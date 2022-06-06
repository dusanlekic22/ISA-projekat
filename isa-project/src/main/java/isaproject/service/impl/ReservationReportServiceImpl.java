package isaproject.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isaproject.dto.ReservationReportDTO;
import isaproject.mapper.ReservationReportMapper;
import isaproject.model.Customer;
import isaproject.model.Mail;
import isaproject.model.RequestStatus;
import isaproject.model.ReservationReport;
import isaproject.model.ReservationReportStatus;
import isaproject.model.User;
import isaproject.repository.CustomerRepository;
import isaproject.repository.ReservationReportRepository;
import isaproject.repository.UserRepository;
import isaproject.service.ReservationReportService;
import isaproject.service.SendMailService;

@Service
public class ReservationReportServiceImpl implements ReservationReportService {

	ReservationReportRepository reservationReportRepository;
	CustomerRepository customerRepository;
	UserRepository userRepository;
	SendMailService mailService;

	@Autowired
	public ReservationReportServiceImpl(ReservationReportRepository reservationReportRepository,
			CustomerRepository customerRepository, UserRepository userRepository, SendMailService mailService) {
		super();
		this.reservationReportRepository = reservationReportRepository;
		this.customerRepository = customerRepository;
		this.userRepository = userRepository;
		this.mailService = mailService;
	}

	@Override
	public ReservationReportDTO create(ReservationReportDTO reservationReportDTO) {
		ReservationReport reservationReport = ReservationReportMapper.DTOToReservationReport(reservationReportDTO);
		if (isReported(reservationReport)) return null; 
		if (reservationReport.getReservationReportStatus() == ReservationReportStatus.Positive) {
			reservationReport.setUserPenalized(RequestStatus.Declined);
		} else if (reservationReport.getReservationReportStatus() == ReservationReportStatus.NoCustomer) {
			reservationReport.setUserPenalized(RequestStatus.Accepted);
			penalizedCustomer(reservationReport);
		} else if (reservationReport.getReservationReportStatus() == ReservationReportStatus.Negative) {
			reservationReport.setUserPenalized(RequestStatus.Waiting);
		}
		return ReservationReportMapper.ReservationReportToDTO(reservationReportRepository.save(reservationReport));
	}

	private boolean isReported(ReservationReport reservationReport) {
		if (reservationReport.getFishingReservation() != null) return isReportedByFishingTrainer(reservationReport.getFishingReservation().getId());
		if (reservationReport.getCottageReservation() != null) return isReportedByCottageOwner(reservationReport.getCottageReservation().getId());
		if (reservationReport.getBoatReservation() != null) return isReportedByBoatOwner(reservationReport.getBoatReservation().getId());
		return false;
	}

	private void penalizedCustomer(ReservationReport reservationReport) {
		Customer customer = getReportedCustomer(reservationReport);
		customer.setPenalties(customer.getPenalties() + 1);
		customerRepository.save(customer);
	}

	@Override
	public Set<ReservationReportDTO> getAllReservationReports() {
		Set<ReservationReportDTO> dtos = new HashSet<ReservationReportDTO>();
		for (ReservationReport fishingReservationReport : reservationReportRepository.findAll()) {
			dtos.add(ReservationReportMapper.ReservationReportToDTO(fishingReservationReport));
		}
		return dtos;
	}

	@Transactional
	@Override
	public ReservationReportDTO approve(ReservationReportDTO reservationReportDTO, String answerCutomer,
			String answerOwner) {
		ReservationReport report = reservationReportRepository.findById(reservationReportDTO.getId()).get();
		if (report.getUserPenalized() != null)
			return ReservationReportMapper.ReservationReportToDTO(report);
		report.setUserPenalized(RequestStatus.Accepted);
		penalizedCustomer(report);
		sendApproveEmailCustomer(getReportedCustomer(report).getEmail(), answerCutomer);
		sendApproveEmailOwner(getReportOwner(report).getEmail(), answerOwner);
		return ReservationReportMapper.ReservationReportToDTO(report);
	}

	private void sendApproveEmailOwner(String email, String answerOwner) {
		User user = userRepository.findByEmail(email);
		String subject = "Reservation report";
		StringBuilder content = new StringBuilder("");
		content.append("Hi ").append(user.getFirstName()).append(",<br><br>")
				.append("You have been sent this email because your customer report is approved.<br><br>")
				.append(answerOwner).append("<br><br>Thank you!<br>Your company name.");

		Mail mail = new Mail(email, subject, content.toString());
		mailService.sendMailHTML(mail);
	}

	private void sendApproveEmailCustomer(String email, String answerCutomer) {
		User user = userRepository.findByEmail(email);
		String subject = "Reservation report";
		StringBuilder content = new StringBuilder("");
		content.append("Hi ").append(user.getFirstName()).append(",<br><br>")
				.append("You have been sent this email because your account is penalized by one point.<br><br>")
				.append(answerCutomer).append("<br><br>Thank you!<br>Your company name.");

		Mail mail = new Mail(email, subject, content.toString());
		mailService.sendMailHTML(mail);
	}

	@Transactional
	@Override
	public ReservationReportDTO decline(ReservationReportDTO reservationReportDTO, String answerCutomer,
			String answerOwner) {
		ReservationReport report = reservationReportRepository.findById(reservationReportDTO.getId()).get();
		if (report.getUserPenalized() != null)
			return ReservationReportMapper.ReservationReportToDTO(report);
		report.setUserPenalized(RequestStatus.Accepted);
		sendDeclineEmailCustomer(getReportedCustomer(report).getEmail(), answerCutomer);
		sendDeclineEmailOwner(getReportOwner(report).getEmail(), answerOwner);
		return ReservationReportMapper.ReservationReportToDTO(report);
	}

	private void sendDeclineEmailOwner(String email, String answerOwner) {
		User user = userRepository.findByEmail(email);
		String subject = "Reservation report";
		StringBuilder content = new StringBuilder("");
		content.append("Hi ").append(user.getFirstName()).append(",<br><br>")
				.append("You have been sent this email because your customer report is declined.<br><br>")
				.append(answerOwner).append("<br><br>Thank you!<br>Your company name.");

		Mail mail = new Mail(email, subject, content.toString());
		mailService.sendMailHTML(mail);
	}

	private void sendDeclineEmailCustomer(String email, String answerCutomer) {
		User user = userRepository.findByEmail(email);
		String subject = "Reservation report";
		StringBuilder content = new StringBuilder("");
		content.append("Hi ").append(user.getFirstName()).append(",<br><br>").append(
				"You have been sent this email because your account was reported, but the report was declined.<br><br>")
				.append(answerCutomer).append("<br><br>Thank you!<br>Your company name.");

		Mail mail = new Mail(email, subject, content.toString());
		mailService.sendMailHTML(mail);
	}

	private Customer getReportedCustomer(ReservationReport report) {
		Customer customer = null;
		if (report.getFishingReservation() != null) {
			customer = customerRepository.findById(report.getFishingReservation().getCustomer().getId()).get();
		} else if (report.getCottageReservation() != null) {
			customer = customerRepository.findById(report.getCottageReservation().getCustomer().getId()).get();
		} else if (report.getBoatReservation() != null) {
			customer = customerRepository.findById(report.getBoatReservation().getCustomer().getId()).get();
		}
		return customer;
	}

	private User getReportOwner(ReservationReport report) {
		User user = null;
		if (report.getFishingReservation() != null) {
			user = userRepository
					.findById(report.getFishingReservation().getFishingCourse().getFishingTrainer().getId()).get();
		} else if (report.getCottageReservation() != null) {
			user = userRepository.findById(report.getCottageReservation().getCottage().getCottageOwner().getId()).get();
		} else if (report.getBoatReservation() != null) {
			user = userRepository.findById(report.getBoatReservation().getBoat().getBoatOwner().getId()).get();
		}
		return user;
	}

	@Override
	public Boolean isReportedByFishingTrainer(Long reservationId) {
		return reservationReportRepository.existsByFishingReservationId(reservationId);
	}
	
	@Override
	public Boolean isReportedByCottageOwner(Long reservationId) {
		return reservationReportRepository.existsByCottageReservationId(reservationId);
	}
	
	@Override
	public Boolean isReportedByBoatOwner(Long reservationId) {
		return reservationReportRepository.existsByBoatReservationId(reservationId);
	}
}
