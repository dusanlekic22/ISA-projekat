package isaproject.dto;

import java.util.HashSet;
import java.util.Set;

import isaproject.model.AdditionalService;
import isaproject.model.Address;
import isaproject.model.Customer;
import isaproject.model.FishingImage;
import isaproject.model.FishingQuickReservation;
import isaproject.model.FishingReservation;
import isaproject.model.FishingTrainer;
import isaproject.model.Grade;

public class FishingCourseDTO {

	private Long id;
	private String name;
	private String promoDescription;
	private Double grade;
	private Integer capacity;
	private String fishingRules;
	private String fishingEquipment;
	private Double price;
	private Double cancellationPercentageKeep;
	private Address address;
	private Set<FishingImage> fishingImage;
	private Set<FishingQuickReservation> fishingQuickReservation;
	private Set<AdditionalService> additionalService;
	private Set<FishingReservation> fishingReservation;
	private FishingTrainer fishingTrainer;
	private Double averageGrade;
	private Set<Grade> grades = new HashSet<Grade>();
	private Set<Customer> subscribers = new HashSet<>();
	
	

	public Set<Customer> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Set<Customer> subscribers) {
		this.subscribers = subscribers;
	}

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

	public String getPromoDescription() {
		return promoDescription;
	}

	public void setPromoDescription(String promoDescription) {
		this.promoDescription = promoDescription;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
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

	public void setFishingEquipment(String fishingEquipment) {
		this.fishingEquipment = fishingEquipment;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<FishingImage> getFishingImage() {
		return fishingImage;
	}

	public void setFishingImage(Set<FishingImage> fishingImage) {
		this.fishingImage = fishingImage;
	}

	public Set<FishingQuickReservation> getFishingQuickReservation() {
		return fishingQuickReservation;
	}

	public void setFishingQuickReservation(Set<FishingQuickReservation> fishingQuickReservation) {
		this.fishingQuickReservation = fishingQuickReservation;
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

	public FishingTrainer getFishingTrainer() {
		return fishingTrainer;
	}

	public void setFishingTrainer(FishingTrainer fishingTrainer) {
		this.fishingTrainer = fishingTrainer;
	}

	public Double getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(Double averageGrade) {
		this.averageGrade = averageGrade;
	}

	public Set<Grade> getGrades() {
		return grades;
	}

	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}

}
