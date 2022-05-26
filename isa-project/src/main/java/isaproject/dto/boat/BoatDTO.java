package isaproject.dto.boat;

import java.util.HashSet;
import java.util.Set;

import isaproject.model.AdditionalService;
import isaproject.model.Address;
import isaproject.model.CancelConditionEnum;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.model.NavigationEquipment;
import isaproject.model.boat.BoatImage;
import isaproject.model.boat.BoatOwner;
import isaproject.model.boat.BoatQuickReservation;
import isaproject.model.boat.BoatReservation;

public class BoatDTO {
	private long id;
	private String name;
	private String type;
	private Integer length;
	private Integer engineNumber;
	private Integer topSpeed;
	private Integer enginePower;
	private Address address;
	private String description;
	private Integer capacity;
	private String boatRules;
	private CancelConditionEnum cancelCondition;
	private Set<String> fishingEquipment;
	private Integer pricePerHour;
	private Set<BoatQuickReservation> boatQuickReservation = new HashSet<>();
	private Set<BoatReservation> boatReservation = new HashSet<>();
	private Set<NavigationEquipment> navigationEquipment = new HashSet<>();
	private Set<AdditionalService> additionalService = new HashSet<>();
	private Set<BoatImage> boatImage;
	private BoatOwner boatOwner;
	private Set<DateTimeSpan> availableReservationDateSpan = new HashSet<DateTimeSpan>();
	private Set<Customer> subscribers = new HashSet<>();

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(Integer engineNumber) {
		this.engineNumber = engineNumber;
	}

	public Integer getTopSpeed() {
		return topSpeed;
	}

	public void setTopSpeed(Integer topSpeed) {
		this.topSpeed = topSpeed;
	}

	public Integer getEnginePower() {
		return enginePower;
	}

	public void setEnginePower(Integer enginePower) {
		this.enginePower = enginePower;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getBoatRules() {
		return boatRules;
	}

	public void setBoatRules(String boatRules) {
		this.boatRules = boatRules;
	}

	public CancelConditionEnum getCancelCondition() {
		return cancelCondition;
	}

	public void setCancelCondition(CancelConditionEnum cancelCondition) {
		this.cancelCondition = cancelCondition;
	}

	public Set<String> getFishingEquipment() {
		return fishingEquipment;
	}

	public void setFishingEquipment(Set<String> fishingEquipment) {
		this.fishingEquipment = fishingEquipment;
	}

	public Integer getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Integer pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public Set<BoatQuickReservation> getBoatQuickReservation() {
		return boatQuickReservation;
	}

	public void setBoatQuickReservation(Set<BoatQuickReservation> boatQuickReservation) {
		this.boatQuickReservation = boatQuickReservation;
	}

	public Set<BoatReservation> getBoatReservation() {
		return boatReservation;
	}

	public void setBoatReservation(Set<BoatReservation> boatReservation) {
		this.boatReservation = boatReservation;
	}

	public Set<NavigationEquipment> getNavigationEquipment() {
		return navigationEquipment;
	}

	public void setNavigationEquipment(Set<NavigationEquipment> navigationEquipment) {
		this.navigationEquipment = navigationEquipment;
	}

	public Set<AdditionalService> getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(Set<AdditionalService> additionalService) {
		this.additionalService = additionalService;
	}

	public Set<BoatImage> getBoatImage() {
		return boatImage;
	}

	public void setBoatImage(Set<BoatImage> boatImage) {
		this.boatImage = boatImage;
	}

	public BoatOwner getBoatOwner() {
		return boatOwner;
	}

	public void setBoatOwner(BoatOwner boatOwner) {
		this.boatOwner = boatOwner;
	}

	public Set<DateTimeSpan> getAvailableReservationDateSpan() {
		return availableReservationDateSpan;
	}

	public void setAvailableReservationDateSpan(Set<DateTimeSpan> availableReservationDateSpan) {
		this.availableReservationDateSpan = availableReservationDateSpan;
	}

	public Set<Customer> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Set<Customer> subscribers) {
		this.subscribers = subscribers;
	}

}
