package isaproject.dto;

import java.util.Set;

import isaproject.model.FishingCourse;

public class FishingTrainerDTO extends UserDTO {

	private Set<FishingCourse> fishingCourse;
	private String biography;

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

}
