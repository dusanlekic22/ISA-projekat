package isaproject.dto;

import isaproject.model.FishingReservation;
import isaproject.model.boat.BoatReservation;
import isaproject.model.cottage.CottageReservation;

public class ComplaintDTO {

	private Long id;
	private String text;
	private FishingReservation fishingReservation;
	private CottageReservation cottageReservation;
	private BoatReservation boatReservation;
	private String answerCustomer;
	private String answerOwner;

	public ComplaintDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
