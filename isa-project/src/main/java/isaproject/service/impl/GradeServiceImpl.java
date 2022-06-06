package isaproject.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import isaproject.dto.GradeDTO;
import isaproject.mapper.GradeMapper;
import isaproject.model.Grade;
import isaproject.model.Mail;
import isaproject.model.User;
import isaproject.repository.GradeRepository;
import isaproject.repository.UserRepository;
import isaproject.service.GradeService;
import isaproject.service.SendMailService;

public class GradeServiceImpl implements GradeService {

	private GradeRepository gradeRepository;
	private SendMailService mailService;
	private UserRepository userRepository;

	@Autowired
	public GradeServiceImpl(GradeRepository gradeRepository, SendMailService mailService,
			UserRepository userRepository) {
		super();
		this.gradeRepository = gradeRepository;
		this.mailService = mailService;
		this.userRepository = userRepository;
	}

	@Override
	public GradeDTO approve(GradeDTO gradeDTO) {
		Grade grade = gradeRepository.findById(gradeDTO.getId()).get();
		if (grade.getIsAccepted() != null)
			return GradeMapper.GradeToGradeDTO(grade);
		grade.setIsAccepted(true);
		sendAnswerEmailOwner(getOwner(gradeDTO).getEmail());
		return GradeMapper.GradeToGradeDTO(grade);
	}

	private void sendAnswerEmailOwner(String email) {
		User user = userRepository.findByEmail(email);
		String subject = "Reservation report";
		StringBuilder content = new StringBuilder("");
		content.append("Hi ").append(user.getFirstName()).append(",<br><br>")
				.append("You have been sent this email because customer graded you.<br><br>")
				.append("<br><br>Thank you!<br>Your company name.");

		Mail mail = new Mail(email, subject, content.toString());
		mailService.sendMailHTML(mail);
	}

	@Override
	public GradeDTO decline(GradeDTO gradeDTO) {
		Grade grade = gradeRepository.findById(gradeDTO.getId()).get();
		if (grade.getIsAccepted() != null)
			return GradeMapper.GradeToGradeDTO(grade);
		grade.setIsAccepted(false);
		return GradeMapper.GradeToGradeDTO(grade);
	}

	@Override
	public Set<GradeDTO> getAllGrades() {
		Set<GradeDTO> dtos = new HashSet<GradeDTO>();
		for (Grade grade : gradeRepository.findAll()) {
			dtos.add(GradeMapper.GradeToGradeDTO(grade));
		}
		return dtos;
	}

	private User getOwner(GradeDTO grade) {
		User user = null;
		if (grade.getFishingCourse() != null) {
			user = userRepository.findById(grade.getFishingCourse().getFishingTrainer().getId()).get();
		} else if (grade.getFishingTrainer() != null) {
			user = userRepository.findById(grade.getFishingTrainer().getId()).get();
		} else if (grade.getCottage() != null) {
			user = userRepository.findById(grade.getCottage().getCottageOwner().getId()).get();
		} else if (grade.getCottageOwner() != null) {
			user = userRepository.findById(grade.getCottageOwner().getId()).get();
		} else if (grade.getBoat() != null) {
			user = userRepository.findById(grade.getBoat().getBoatOwner().getId()).get();
		} else if (grade.getBoatOwner() != null) {
			user = userRepository.findById(grade.getBoatOwner().getId()).get();
		}
		return user;
	}
}
