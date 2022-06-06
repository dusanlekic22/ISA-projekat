package isaproject.model;

import static javax.persistence.FetchType.EAGER;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import isaproject.model.boat.BoatReservation;
import isaproject.model.cottage.CottageReservation;

@Entity
@Table(name = "Complaint")
public class Complaint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String text;

	@OneToOne(fetch = EAGER)
	@JoinColumn(name = "fishingReservation_id", referencedColumnName = "id")
	private FishingReservation fishingReservation;

	@OneToOne(fetch = EAGER)
	@JoinColumn(name = "cottageReservation_id", referencedColumnName = "id")
	private CottageReservation cottageReservation;

	@OneToOne(fetch = EAGER)
	@JoinColumn(name = "boatReservation_id", referencedColumnName = "id")
	private BoatReservation boatReservation;

	public Complaint() {
		super();
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

}
