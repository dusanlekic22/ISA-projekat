package isaproject.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import isaproject.model.boat.Boat;
import isaproject.model.boat.BoatOwner;
import isaproject.model.cottage.Cottage;
import isaproject.model.cottage.CottageOwner;

@Entity(name = "grade")
public class Grade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double value;

	@OneToOne(fetch = FetchType.EAGER)
	private User user;

	private String review;

	private Boolean isAccepted;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference(value = "cottageGrades")
	private Cottage cottage;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference(value = "cottageOwnerGrades")
	private CottageOwner cottageOwner;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference(value = "boatGrades")
	private Boat boat;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference(value = "boatOwnerGrades")
	private BoatOwner boatOwner;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference(value = "fishingCourseGrades")
	private FishingCourse fishingCourse;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference(value = "fishingTrainerGrades")
	private FishingTrainer fishingTrainer;

	public Grade() {
	}

	public Grade(Long id, Double value, User user, String review, Boolean isAccepted, Cottage cottage,
			CottageOwner cottageOwner, Boat boat, BoatOwner boatOwner, FishingCourse fishingCourse,
			FishingTrainer fishingTrainer) {
		super();
		this.id = id;
		this.value = value;
		this.user = user;
		this.review = review;
		this.isAccepted = isAccepted;
		this.cottage = cottage;
		this.cottageOwner = cottageOwner;
		this.boat = boat;
		this.boatOwner = boatOwner;
		this.fishingCourse = fishingCourse;
		this.fishingTrainer = fishingTrainer;
	}

	public Grade(Grade grade) {
		super();
		this.id = grade.id;
		this.value = grade.value;
		this.user = grade.user;
		this.review = grade.review;
		this.isAccepted = grade.isAccepted;
		validate();
	}

	private boolean validate() {
		return value >= 1 && value <= 5;
	}

	public FishingCourse getFishingCourse() {
		return fishingCourse;
	}

	public void setFishingCourse(FishingCourse fishingCourse) {
		this.fishingCourse = fishingCourse;
	}

	public Double getValue() {
		return value;
	}

	public User getUser() {
		return user;
	}

	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public void setUser(User user) {
		this.user = user;
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

	public FishingTrainer getFishingTrainer() {
		return fishingTrainer;
	}

	public void setFishingTrainer(FishingTrainer fishingTrainer) {
		this.fishingTrainer = fishingTrainer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isAccepted == null) ? 0 : isAccepted.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grade other = (Grade) obj;
		if (isAccepted == null) {
			if (other.isAccepted != null)
				return false;
		} else if (!isAccepted.equals(other.isAccepted))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
