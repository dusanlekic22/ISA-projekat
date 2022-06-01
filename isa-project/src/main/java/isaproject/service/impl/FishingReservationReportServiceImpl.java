package isaproject.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isaproject.dto.FishingReservationReportDTO;
import isaproject.mapper.ReservationReportMapper;
import isaproject.model.Customer;
import isaproject.model.FishingReservationReport;
import isaproject.model.Mail;
import isaproject.model.ReservationReportStatus;
import isaproject.model.User;
import isaproject.repository.CustomerRepository;
import isaproject.repository.FishingReservationReportRepository;
import isaproject.repository.UserRepository;
import isaproject.service.FishingReservationReportService;
import isaproject.service.SendMailService;

@Service
public class FishingReservationReportServiceImpl implements FishingReservationReportService {

	FishingReservationReportRepository fishingReservationReportRepository;
	CustomerRepository customerRepository;
	UserRepository userRepository;
	SendMailService mailService;

	@Autowired
	public FishingReservationReportServiceImpl(FishingReservationReportRepository fishingReservationReportRepository,
			CustomerRepository customerRepository, UserRepository userRepository, SendMailService mailService) {
		super();
		this.fishingReservationReportRepository = fishingReservationReportRepository;
		this.customerRepository = customerRepository;
		this.userRepository = userRepository;
		this.mailService = mailService;
	}

	@Override
	public FishingReservationReportDTO create(FishingReservationReportDTO fishingReservationReportDTO) {
		FishingReservationReport fishingReservationReport = ReservationReportMapper
				.DTOToFishingReservationReport(fishingReservationReportDTO);
		if (fishingReservationReport.getReservationReportStatus() == ReservationReportStatus.Positive) {
			fishingReservationReport.setUserPenalized(false);
		} else if (fishingReservationReport.getReservationReportStatus() == ReservationReportStatus.NoCustomer) {
			fishingReservationReport.setUserPenalized(true);
			penalizedCustomer(fishingReservationReport);
		} else if (fishingReservationReport.getReservationReportStatus() == ReservationReportStatus.Negative) {
			fishingReservationReport.setUserPenalized(null);
		}
		return ReservationReportMapper
				.FishingReservationReportToDTO(fishingReservationReportRepository.save(fishingReservationReport));
	}

	private void penalizedCustomer(FishingReservationReport fishingReservationReport) {
		Customer customer = customerRepository
				.findById(fishingReservationReport.getFishingReservation().getCustomer().getId()).get();
		customer.setPenalties(customer.getPenalties() + 1);
		customerRepository.save(customer);
	}

	@Override
	public Set<FishingReservationReportDTO> getAllFishingReservationReports() {
		Set<FishingReservationReportDTO> dtos = new HashSet<FishingReservationReportDTO>();
		for (FishingReservationReport fishingReservationReport : fishingReservationReportRepository.findAll()) {
			dtos.add(ReservationReportMapper.FishingReservationReportToDTO(fishingReservationReport));
		}
		return dtos;
	}

	@Transactional
	@Override
	public FishingReservationReportDTO approve(FishingReservationReportDTO fishingReservationReportDTO,
			String answerCutomer, String answerOwner) {
		FishingReservationReport report = fishingReservationReportRepository
				.findById(fishingReservationReportDTO.getId()).get();
		if (report.getUserPenalized() != null)
			return ReservationReportMapper.FishingReservationReportToDTO(report);
		report.setUserPenalized(true);
		penalizedCustomer(report);
		sendApproveEmailCustomer(report.getFishingReservation().getFishingCourse().getFishingTrainer().getEmail(),
				answerCutomer);
		sendApproveEmailOwner(report.getFishingReservation().getCustomer().getEmail(), answerOwner);
		return ReservationReportMapper.FishingReservationReportToDTO(report);
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
	public FishingReservationReportDTO decline(FishingReservationReportDTO fishingReservationReportDTO,
			String answerCutomer, String answerOwner) {
		FishingReservationReport report = fishingReservationReportRepository
				.findById(fishingReservationReportDTO.getId()).get();
		if (report.getUserPenalized() != null)
			return ReservationReportMapper.FishingReservationReportToDTO(report);
		report.setUserPenalized(false);
		sendDeclineEmailCustomer(report.getFishingReservation().getFishingCourse().getFishingTrainer().getEmail(),
				answerCutomer);
		sendDeclineEmailOwner(report.getFishingReservation().getCustomer().getEmail(), answerOwner);
		return ReservationReportMapper.FishingReservationReportToDTO(report);
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

}
