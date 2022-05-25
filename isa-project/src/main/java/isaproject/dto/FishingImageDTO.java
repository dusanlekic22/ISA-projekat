package isaproject.dto;

import isaproject.model.FishingCourse;

public class FishingImageDTO {
	
	private Long id;
	private String image;
	private FishingCourse fishingCourse;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}

	public FishingCourse getFishingCourse() {
		return fishingCourse;
	}

	public void setFishingCourse(FishingCourse fishingCourse) {
		this.fishingCourse = fishingCourse;
	}

}
