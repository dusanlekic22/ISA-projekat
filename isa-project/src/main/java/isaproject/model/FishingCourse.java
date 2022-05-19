package isaproject.model;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class FishingCourse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;

	private String promoDescription;

	@OneToMany(mappedBy = "fishingCourse", fetch = FetchType.EAGER, cascade = { PERSIST, MERGE })
	private Set<FishingImage> fishingImage = new HashSet<FishingImage>();

	private Integer capacity;

	@OneToMany(mappedBy = "fishingCourse", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<FishingQuickReservation> fishingQuickReservation = new HashSet<FishingQuickReservation>();

	private String fishingRules;

	private String fisingEquipment;

	@OneToMany(mappedBy = "fishingCourse", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<AdditionalService> additionalService = new HashSet<AdditionalService>();

	@OneToMany(mappedBy = "fishingCourse", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<FishingReservation> fishingReservation = new HashSet<FishingReservation>();

	private Double price = 0.0;

	private Double cancellationPercentageKeep = 0.0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fishingTrainer_id", referencedColumnName = "id")
	private FishingTrainer fishingTrainer;

	public FishingCourse() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPromoDescription() {
		return promoDescription;
	}

	public void setPromoDescription(String promoDescription) {
		this.promoDescription = promoDescription;
	}

	public Set<FishingImage> getFishingImage() {
		return fishingImage;
	}

	public void setFishingImage(Set<FishingImage> fishingImage) {
		this.fishingImage = fishingImage;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Set<FishingQuickReservation> getFishingQuickReservation() {
		return fishingQuickReservation;
	}

	public void setFishingQuickReservation(Set<FishingQuickReservation> fishingQuickReservation) {
		this.fishingQuickReservation = fishingQuickReservation;
	}

	public String getFishingRules() {
		return fishingRules;
	}

	public void setFishingRules(String fishingRules) {
		this.fishingRules = fishingRules;
	}

	public String getFisingEquipment() {
		return fisingEquipment;
	}

	public void setFisingEquipment(String fisingEquipment) {
		this.fisingEquipment = fisingEquipment;
	}

	public Set<AdditionalService> getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(Set<AdditionalService> additionalService) {
		this.additionalService = additionalService;
	}

	public Set<FishingReservation> getFishingReservation() {
		return fishingReservation;
	}

	public void setFishingReservation(Set<FishingReservation> fishingReservation) {
		this.fishingReservation = fishingReservation;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getCancellationPercentageKeep() {
		return cancellationPercentageKeep;
	}

	public void setCancellationPercentageKeep(Double cancellationPercentageKeep) {
		this.cancellationPercentageKeep = cancellationPercentageKeep;
	}

	public FishingTrainer getFishingTrainer() {
		return fishingTrainer;
	}

	public void setFishingTrainer(FishingTrainer fishingTrainer) {
		this.fishingTrainer = fishingTrainer;
	}

}