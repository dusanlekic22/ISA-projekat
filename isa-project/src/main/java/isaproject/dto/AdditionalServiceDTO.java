package isaproject.dto;

import isaproject.model.boat.Boat;
import isaproject.model.boat.BoatQuickReservation;
import isaproject.model.boat.BoatReservation;
import isaproject.model.cottage.Cottage;
import isaproject.model.cottage.CottageQuickReservation;
import isaproject.model.cottage.CottageReservation;
import isaproject.model.FishingCourse;

public class AdditionalServiceDTO {
	
	private long id;
	private String name;
	private Double price;
	private Boat boat;
	private Cottage cottage;
	private FishingCourse fishingCourse;
	private CottageReservation cottageReservation;
	private CottageQuickReservation cottageQuickReservation;
	private BoatReservation boatReservation;
	private BoatQuickReservation boatQuickReservation;
	
	public AdditionalServiceDTO() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Cottage getCottage() {
		return cottage;
	}

	public void setCottage(Cottage cottage) {
		this.cottage = cottage;
	}

	public FishingCourse getFishingCourse() {
		return fishingCourse;
	}

	public void setFishingCourse(FishingCourse fishingCourse) {
		this.fishingCourse = fishingCourse;
	}

	public Boat getBoat() {
		return boat;
	}
	public void setBoat(Boat boat) {
		this.boat = boat;
	}

	public CottageReservation getCottageReservation() {
		return cottageReservation;
	}

	public void setCottageReservation(CottageReservation cottageReservation) {
		this.cottageReservation = cottageReservation;
	}

	public CottageQuickReservation getCottageQuickReservation() {
		return cottageQuickReservation;
	}

	public void setCottageQuickReservation(CottageQuickReservation cottageQuickReservation) {
		this.cottageQuickReservation = cottageQuickReservation;
	}

	public BoatReservation getBoatReservation() {
		return boatReservation;
	}

	public void setBoatReservation(BoatReservation boatReservation) {
		this.boatReservation = boatReservation;
	}

	public BoatQuickReservation getBoatQuickReservation() {
		return boatQuickReservation;
	}

	public void setBoatQuickReservation(BoatQuickReservation boatQuickReservation) {
		this.boatQuickReservation = boatQuickReservation;
	}
	
}
