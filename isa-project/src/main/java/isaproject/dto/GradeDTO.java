package isaproject.dto;

import isaproject.model.FishingCourse;
import isaproject.model.FishingTrainer;
import isaproject.model.User;
import isaproject.model.boat.Boat;
import isaproject.model.boat.BoatOwner;
import isaproject.model.cottage.Cottage;
import isaproject.model.cottage.CottageOwner;

public class GradeDTO {
	private Double value;

	private User user;

	private Boolean isAccepted;

	private String review;

	private Cottage cottage;

	private CottageOwner cottageOwner;

	private Boat boat;

	private BoatOwner boatOwner;

	private FishingCourse fishingCourse;

	private FishingTrainer fishingTrainer;

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public Cottage getCottage() {
		return cottage;
	}

	public void setCottage(Cottage cottage) {
		this.cottage = cottage;
	}

	public CottageOwner getCottageOwner() {
		return cottageOwner;
	}

	public void setCottageOwner(CottageOwner cottageOwner) {
		this.cottageOwner = cottageOwner;
	}

	public Boat getBoat() {
		return boat;
	}

	public void setBoat(Boat boat) {
		this.boat = boat;
	}

	public BoatOwner getBoatOwner() {
		return boatOwner;
	}

	public void setBoatOwner(BoatOwner boatOwner) {
		this.boatOwner = boatOwner;
	}

	public FishingCourse getFishingCourse() {
		return fishingCourse;
	}

	public void setFishingCourse(FishingCourse fishingCourse) {
		this.fishingCourse = fishingCourse;
	}

	public FishingTrainer getFishingTrainer() {
		return fishingTrainer;
	}

	public void setFishingTrainer(FishingTrainer fishingTrainer) {
		this.fishingTrainer = fishingTrainer;
	}

}
