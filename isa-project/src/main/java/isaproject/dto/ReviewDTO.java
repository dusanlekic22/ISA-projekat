package isaproject.dto;

import isaproject.dto.boat.BoatReservationDTO;
import isaproject.dto.cottage.CottageReservationDTO;

public class ReviewDTO {

	private Long customerId;
	
	private String text;
	
	private Double ownerGrade;
	
	private Double entityGrade;
	
	private CottageReservationDTO cottageReservation;
	
	private BoatReservationDTO boatReservation;
	
	private FishingReservationDTO fishingReservation;
	

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Double getOwnerGrade() {
		return ownerGrade;
	}

	public void setOwnerGrade(Double ownerGrade) {
		this.ownerGrade = ownerGrade;
	}

	public Double getEntityGrade() {
		return entityGrade;
	}

	public void setEntityGrade(Double entityGrade) {
		this.entityGrade = entityGrade;
	}

	public CottageReservationDTO getCottageReservation() {
		return cottageReservation;
	}

	public void setCottageReservation(CottageReservationDTO cottageReservation) {
		this.cottageReservation = cottageReservation;
	}

	public BoatReservationDTO getBoatReservation() {
		return boatReservation;
	}

	public void setBoatReservation(BoatReservationDTO boatReservation) {
		this.boatReservation = boatReservation;
	}

	public FishingReservationDTO getFishingReservation() {
		return fishingReservation;
	}

	public void setFishingReservation(FishingReservationDTO fishingReservation) {
		this.fishingReservation = fishingReservation;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
	
	
}
