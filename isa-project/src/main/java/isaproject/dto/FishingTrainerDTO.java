package isaproject.dto;

import java.util.Set;

import isaproject.model.DateTimeSpan;
import isaproject.model.FishingCourse;

public class FishingTrainerDTO extends UserDTO {

	private Set<FishingCourse> fishingCourse;
	private String biography;
	private Set<DateTimeSpan> availableReservationDateSpan;
	private Set<DateTimeSpan> unavailableReservationDateSpan;

	public FishingTrainerDTO() {
	}

	public Set<FishingCourse> getFishingCourse() {
		return fishingCourse;
	}

	public void setFishingCourse(Set<FishingCourse> fishingCourse) {
		this.fishingCourse = fishingCourse;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public Set<DateTimeSpan> getAvailableReservationDateSpan() {
		return availableReservationDateSpan;
	}

	public void setAvailableReservationDateSpan(Set<DateTimeSpan> availableReservationDateSpan) {
		this.availableReservationDateSpan = availableReservationDateSpan;
	}

	public Set<DateTimeSpan> getUnavailableReservationDateSpan() {
		return unavailableReservationDateSpan;
	}

	public void setUnavailableReservationDateSpan(Set<DateTimeSpan> unavailableReservationDateSpan) {
		this.unavailableReservationDateSpan = unavailableReservationDateSpan;
	}

}
