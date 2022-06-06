package isaproject.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isaproject.dto.GradeDTO;
import isaproject.mapper.GradeMapper;
import isaproject.model.FishingCourse;
import isaproject.model.FishingTrainer;
import isaproject.model.Grade;
import isaproject.model.Mail;
import isaproject.model.RequestStatus;
import isaproject.model.User;
import isaproject.model.boat.Boat;
import isaproject.model.boat.BoatOwner;
import isaproject.model.cottage.Cottage;
import isaproject.model.cottage.CottageOwner;
import isaproject.repository.FishingCourseRepository;
import isaproject.repository.FishingTrainerRepository;
import isaproject.repository.GradeRepository;
import isaproject.repository.UserRepository;
import isaproject.repository.boat.BoatOwnerRepository;
import isaproject.repository.boat.BoatRepository;
import isaproject.repository.cottage.CottageOwnerRepository;
import isaproject.repository.cottage.CottageRepository;
import isaproject.service.GradeService;
import isaproject.service.SendMailService;

@Service
public class GradeServiceImpl implements GradeService {

	private GradeRepository gradeRepository;
	private SendMailService mailService;
	private UserRepository userRepository;
	private FishingCourseRepository courseRepository;
	private FishingTrainerRepository fishingTrainerRepository;
	private CottageRepository cottageRepository;
	private CottageOwnerRepository cottageOwnerRepository;
	private BoatRepository boatRepository;
	private BoatOwnerRepository boatOwnerRepository;

	@Autowired
	public GradeServiceImpl(GradeRepository gradeRepository, SendMailService mailService, UserRepository userRepository,
			FishingCourseRepository courseRepository, FishingTrainerRepository fishingTrainerRepository,
			CottageRepository cottageRepository, CottageOwnerRepository cottageOwnerRepository,
			BoatRepository boatRepository, BoatOwnerRepository boatOwnerRepository) {
		super();
		this.gradeRepository = gradeRepository;
		this.mailService = mailService;
		this.userRepository = userRepository;
		this.courseRepository = courseRepository;
		this.fishingTrainerRepository = fishingTrainerRepository;
		this.cottageRepository = cottageRepository;
		this.cottageOwnerRepository = cottageOwnerRepository;
		this.boatRepository = boatRepository;
		this.boatOwnerRepository = boatOwnerRepository;
	}

	@Transactional
	@Override
	public GradeDTO approve(GradeDTO gradeDTO) {
		Grade grade = gradeRepository.findById(gradeDTO.getId()).get();
		if (grade.getIsAccepted() != RequestStatus.Waiting)
			return GradeMapper.GradeToGradeDTO(grade);
		grade.setIsAccepted(RequestStatus.Accepted);
		sendAnswerEmailOwner(getOwnerAndUpdateAverageGrade(gradeDTO).getEmail());
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

	@Transactional
	@Override
	public GradeDTO decline(GradeDTO gradeDTO) {
		Grade grade = gradeRepository.findById(gradeDTO.getId()).get();
		if (grade.getIsAccepted() != RequestStatus.Waiting)
			return GradeMapper.GradeToGradeDTO(grade);
		grade.setIsAccepted(RequestStatus.Declined);
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

	private User getOwnerAndUpdateAverageGrade(GradeDTO grade) {
		User user = null;
		if (grade.getFishingCourse() != null) {
			user = userRepository.findById(grade.getFishingCourse().getFishingTrainer().getId()).get();
			FishingCourse fishingCourse = grade.getFishingCourse();
			fishingCourse.setAverageGrade();
			courseRepository.save(fishingCourse);
		} else if (grade.getFishingTrainer() != null) {
			user = userRepository.findById(grade.getFishingTrainer().getId()).get();
			FishingTrainer fishingTrainer = grade.getFishingTrainer();
			fishingTrainer.setAverageGrade();
			fishingTrainerRepository.save(fishingTrainer);
		} else if (grade.getCottage() != null) {
			user = userRepository.findById(grade.getCottage().getCottageOwner().getId()).get();
			Cottage cottage = grade.getCottage();
			cottage.setAverageGrade();
			cottageRepository.save(cottage);
		} else if (grade.getCottageOwner() != null) {
			user = userRepository.findById(grade.getCottageOwner().getId()).get();
			CottageOwner cottageOwner = grade.getCottageOwner();
			cottageOwner.setAverageGrade();
			cottageOwnerRepository.save(cottageOwner);
		} else if (grade.getBoat() != null) {
			user = userRepository.findById(grade.getBoat().getBoatOwner().getId()).get();
			Boat boat = grade.getBoat();
			boat.setAverageGrade();
			boatRepository.save(boat);
		} else if (grade.getBoatOwner() != null) {
			user = userRepository.findById(grade.getBoatOwner().getId()).get();
			BoatOwner boatOwner = grade.getBoatOwner();
			boatOwner.setAverageGrade();
			boatOwnerRepository.save(boatOwner);
		}
		return user;
	}
}
