package isaproject.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import isaproject.model.BoatImage;
import isaproject.model.BoatOwner;

import javax.persistence.ManyToOne;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "Boat")
public class Boat implements Serializable {

	private static final long serialVersionUID = 1L;

	public Boat() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String type;
	private String length;
	private String engineNumber;
	private String topSpeed;
	private String enginePower;
	@OneToOne
	private Address address;
	private String description;
	private String capacity;
	private String boatRules;
	@Enumerated(EnumType.STRING)
	private CancelConditionEnum cancelCondition;
	private String fishingEquipment;
	private Integer pricePerHour;
	@OneToMany(mappedBy = "boat")
	private Set<BoatQuickReservation> boatQuickReservation;
	@OneToMany(mappedBy = "boat")
	private Set<NavigationEquipment> navigationEquipment;
	@OneToMany(mappedBy = "boat")
	private Set<AdditionalService> additionalService;
	@OneToMany(mappedBy = "boat")
	private Set<BoatImage> boatImage;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "boatOwner_id", referencedColumnName = "id")
	private BoatOwner boatOwner;

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

	public String getType() {
		return type;
	}

	public void setType(String param) {
		this.type = param;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String param) {
		this.length = param;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String param) {
		this.engineNumber = param;
	}

	public String getTopSpeed() {
		return topSpeed;
	}

	public void setTopSpeed(String param) {
		this.topSpeed = param;
	}

	public String getEnginePower() {
		return enginePower;
	}

	public void setEnginePower(String param) {
		this.enginePower = param;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address param) {
		this.address = param;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String param) {
		this.description = param;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String param) {
		this.capacity = param;
	}

	public String getBoatRules() {
		return boatRules;
	}

	public void setBoatRules(String param) {
		this.boatRules = param;
	}

	public CancelConditionEnum getCancelCondition() {
		return cancelCondition;
	}

	public void setCancelCondition(CancelConditionEnum param) {
		this.cancelCondition = param;
	}

	public String getFishingEquipment() {
		return fishingEquipment;
	}

	public void setFishingEquipment(String param) {
		this.fishingEquipment = param;
	}

	public Integer getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Integer param) {
		this.pricePerHour = param;
	}

	public Set<BoatQuickReservation> getBoatQuickReservation() {
	    return boatQuickReservation;
	}

	public void setBoatQuickReservation(Set<BoatQuickReservation> param) {
	    this.boatQuickReservation = param;
	}

	public Set<NavigationEquipment> getNavigationEquipment() {
	    return navigationEquipment;
	}

	public void setNavigationEquipment(Set<NavigationEquipment> param) {
	    this.navigationEquipment = param;
	}

	public Set<AdditionalService> getAdditionalService() {
	    return additionalService;
	}

	public void setAdditionalService(Set<AdditionalService> param) {
	    this.additionalService = param;
	}

	public Set<BoatImage> getBoatImage() {
	    return boatImage;
	}

	public void setBoatImage(Set<BoatImage> param) {
	    this.boatImage = param;
	}

	public BoatOwner getBoatOwner() {
	    return boatOwner;
	}

	public void setBoatOwner(BoatOwner param) {
	    this.boatOwner = param;
	}

}