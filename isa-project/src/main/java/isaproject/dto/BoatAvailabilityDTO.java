package isaproject.dto;

import java.util.List;

import isaproject.model.SortType;

public class BoatAvailabilityDTO {
	
	public BoatAvailabilityDTO() {
	}

	private String name;
	private int bedCapacity;
	private DateSpanDTO dateSpan;
	private float price;
	private Double grade;
	private Double longitude;
	private Double latitude;	
	private List<SortType> sortBy;
	private List<String> freeAdditionalServices;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBedCapacity() {
		return bedCapacity;
	}

	public void setBedCapacity(int bedCapacity) {
		this.bedCapacity = bedCapacity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}
	

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public List<SortType> getSortBy() {
		return sortBy;
	}

	public void setSortBy(List<SortType> sortBy) {
		this.sortBy = sortBy;
	}

	public List<String> getFreeAdditionalServices() {
		return freeAdditionalServices;
	}

	public void setFreeAdditionalServices(List<String> freeAdditionalServices) {
		this.freeAdditionalServices = freeAdditionalServices;
	}

	public DateSpanDTO getDateSpan() {
		return dateSpan;
	}

	public void setDateSpan(DateSpanDTO dateSpan) {
		this.dateSpan = dateSpan;
	}
}
