package isaproject.dto;

import isaproject.model.FishingReservation;
import isaproject.model.ReservationReportStatus;

public class FishingReservationReportDTO {

	private Long id;
	private Boolean userPenalized;
	private String comment;
	private ReservationReportStatus reservationReportStatus;
	private FishingReservation fishingReservation;
	private String answerCustomer;
	private String answerOwner;

	public FishingReservationReportDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getUserPenalized() {
		return userPenalized;
	}

	public void setUserPenalized(Boolean accepted) {
		this.userPenalized = accepted;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ReservationReportStatus getReservationReportStatus() {
		return reservationReportStatus;
	}

	public void setReservationReportStatus(ReservationReportStatus reservationReportStatus) {
		this.reservationReportStatus = reservationReportStatus;
	}

	public FishingReservation getFishingReservation() {
		return fishingReservation;
	}

	public void setFishingReservation(FishingReservation fishingReservation) {
		this.fishingReservation = fishingReservation;
	}

	public String getAnswerCustomer() {
		return answerCustomer;
	}

	public void setAnswerCustomer(String answerCustomer) {
		this.answerCustomer = answerCustomer;
	}

	public String getAnswerOwner() {
		return answerOwner;
	}

	public void setAnswerOwner(String answerOwner) {
		this.answerOwner = answerOwner;
	}

}
