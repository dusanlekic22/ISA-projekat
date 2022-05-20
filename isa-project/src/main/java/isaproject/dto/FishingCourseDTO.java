package isaproject.dto;

import java.util.Set;

import isaproject.model.AdditionalService;
import isaproject.model.Address;
import isaproject.model.FishingImage;
import isaproject.model.FishingQuickReservation;
import isaproject.model.FishingReservation;
import isaproject.model.FishingTrainer;

public class FishingCourseDTO {

	private Long id;
	private String name;
	private Address address;
	private String promoDescription;
	private Set<FishingImage> fishingImage;
	private Integer capacity;
	private Set<FishingQuickReservation> fishingQuickReservation;
	private String fishingRules;
	private String fishingEquipment;
	private Set<AdditionalService> additionalService;
	private Set<FishingReservation> fishingReservation;
	private Double price;
	private Double cancellationPercentageKeep;
	private FishingTrainer fishingTrainer;
	
	public FishingCourseDTO() {
		super();
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

	public String getFishingEquipment() {
		return fishingEquipment;
	}

	public void setFishingEquipment(String fisingEquipment) {
		this.fishingEquipment = fisingEquipment;
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
