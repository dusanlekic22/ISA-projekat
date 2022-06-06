package isaproject.dto;

import isaproject.model.FishingReservation;
import isaproject.model.RequestStatus;
import isaproject.model.ReservationReportStatus;
import isaproject.model.boat.BoatReservation;
import isaproject.model.cottage.CottageReservation;

public class ReservationReportDTO {

	private Long id;
	private RequestStatus userPenalized;
	private String comment;
	private ReservationReportStatus reservationReportStatus;
	private FishingReservation fishingReservation;
	private CottageReservation cottageReservation;
	private BoatReservation boatReservation;
	private String answerCustomer;
	private String answerOwner;

	public ReservationReportDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequestStatus getUserPenalized() {
		return userPenalized;
	}

	public void setUserPenalized(RequestStatus accepted) {
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

	public CottageReservation getCottageReservation() {
		return cottageReservation;
	}

	public void setCottageReservation(CottageReservation cottageReservation) {
		this.cottageReservation = cottageReservation;
	}

	public BoatReservation getBoatReservation() {
		return boatReservation;
	}

	public void setBoatReservation(BoatReservation boatReservation) {
		this.boatReservation = boatReservation;
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
