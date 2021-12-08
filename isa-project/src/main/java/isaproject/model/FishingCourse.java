package isaproject.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import isaproject.model.FishingTrainer;

import javax.persistence.ManyToOne;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.JoinColumn;

@Entity
public class FishingCourse implements Serializable {

	private static final long serialVersionUID = 1L;

	public FishingCourse() {
	}

	@Id
	private long id;
	private String name;
	@OneToOne
	private Address address;
	private String promoDescription;
	private String trainerBiography;
	private String capacity;
	private String fishingRules;
	private String fisingEquipment;
	@OneToMany(mappedBy = "fishingCourse")
	private Set<FishingQuickReservation> fishingQuickReservation;
	@OneToMany(mappedBy = "fishingCourse")
	private Set<FishingImage> fishingImage;
	@OneToMany(mappedBy = "fishingCourse")
	private Collection<AdditionalService> additionalService;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "fishingTrainer_id", referencedColumnName = "id")
	private FishingTrainer fishingTrainer;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address param) {
		this.address = param;
	}

	public String getPromoDescription() {
		return promoDescription;
	}

	public void setPromoDescription(String param) {
		this.promoDescription = param;
	}

	public String getTrainerBiography() {
		return trainerBiography;
	}

	public void setTrainerBiography(String param) {
		this.trainerBiography = param;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String param) {
		this.capacity = param;
	}

	public String getFishingRules() {
		return fishingRules;
	}

	public void setFishingRules(String param) {
		this.fishingRules = param;
	}

	public String getFisingEquipment() {
		return fisingEquipment;
	}

	public void setFisingEquipment(String param) {
		this.fisingEquipment = param;
	}

	public Set<FishingQuickReservation> getFishingQuickReservation() {
	    return fishingQuickReservation;
	}

	public void setFishingQuickReservation(Set<FishingQuickReservation> param) {
	    this.fishingQuickReservation = param;
	}

	public Set<FishingImage> getFishingImage() {
	    return fishingImage;
	}

	public void setFishingImage(Set<FishingImage> param) {
	    this.fishingImage = param;
	}

	public Collection<AdditionalService> getAdditionalService() {
	    return additionalService;
	}

	public void setAdditionalService(Collection<AdditionalService> param) {
	    this.additionalService = param;
	}

	public FishingTrainer getFishingTrainer() {
	    return fishingTrainer;
	}

	public void setFishingTrainer(FishingTrainer param) {
	    this.fishingTrainer = param;
	}

}