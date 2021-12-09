package isaproject.model;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import isaproject.model.BoatReservation;

@Entity
@Table(name = "AdditionalService")
public class AdditionalService implements Serializable {

	private static final long serialVersionUID = 1L;

	public AdditionalService() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String price;
	@ManyToOne(fetch = LAZY)
	private Boat boat;
	@ManyToOne(fetch = LAZY)
	private CottageQuickReservation cottageQuickReservation;
	@ManyToOne(fetch = LAZY)
	private Cottage cottage;
	@ManyToOne(fetch = LAZY)
	private FishingCourse fishingCourse;
	@ManyToOne(fetch = LAZY)
	private FishingReservation fishingReservation;
	@ManyToOne(fetch = LAZY)
	private FishingQuickReservation fishingQuickReservation;
	@ManyToOne(fetch = LAZY)
	private CottageReservation cottageReservation;
	@ManyToOne(fetch = LAZY)
	private BoatQuickReservation boatQuickReservation;
	@ManyToOne(fetch = LAZY)
	private BoatReservation boatReservation;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String param) {
		this.name = param;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String param) {
		this.price = param;
	}

	public Boat getBoat() {
	    return boat;
	}

	public void setBoat(Boat param) {
	    this.boat = param;
	}

	public CottageQuickReservation getCottageQuickReservation() {
	    return cottageQuickReservation;
	}

	public void setCottageQuickReservation(CottageQuickReservation param) {
	    this.cottageQuickReservation = param;
	}

	public Cottage getCottage() {
	    return cottage;
	}

	public void setCottage(Cottage param) {
	    this.cottage = param;
	}

	public FishingCourse getFishingCourse() {
	    return fishingCourse;
	}

	public void setFishingCourse(FishingCourse param) {
	    this.fishingCourse = param;
	}

	public FishingReservation getFishingReservation() {
	    return fishingReservation;
	}

	public void setFishingReservation(FishingReservation param) {
	    this.fishingReservation = param;
	}

	public FishingQuickReservation getFishingQuickReservation() {
	    return fishingQuickReservation;
	}

	public void setFishingQuickReservation(FishingQuickReservation param) {
	    this.fishingQuickReservation = param;
	}

	public CottageReservation getCottageReservation() {
	    return cottageReservation;
	}

	public void setCottageReservation(CottageReservation param) {
	    this.cottageReservation = param;
	}

	public BoatQuickReservation getBoatQuickReservation() {
	    return boatQuickReservation;
	}

	public void setBoatQuickReservation(BoatQuickReservation param) {
	    this.boatQuickReservation = param;
	}

	public BoatReservation getBoatReservation() {
	    return boatReservation;
	}

	public void setBoatReservation(BoatReservation param) {
	    this.boatReservation = param;
	}

}