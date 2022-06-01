package isaproject.model.boat;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import isaproject.model.AdditionalService;
import isaproject.model.Address;
import isaproject.model.CancelConditionEnum;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.model.NavigationEquipment;

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
	private Integer length;
	private Integer engineNumber;
	private Integer topSpeed;
	private Integer enginePower;
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Address address;
	private String description;
	private Double grade;
	private Integer capacity;
	private String boatRules;
	@Enumerated(EnumType.STRING)
	private CancelConditionEnum cancelCondition;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "boat_fishing_equipment", joinColumns = @JoinColumn(name = "boat_id"), foreignKey = @ForeignKey(name = "equipment_boat"))
	private Set<String> fishingEquipment;
	private Integer pricePerHour;
	@JsonManagedReference
	@OneToMany(mappedBy = "boat", fetch = FetchType.EAGER)
	private Set<BoatQuickReservation> boatQuickReservation = new HashSet<>();
	@JsonManagedReference("boatReservation")
	@OneToMany(mappedBy = "boat", fetch = FetchType.EAGER)
	private Set<BoatReservation> boatReservation = new HashSet<>();
	@JsonManagedReference
	@OneToMany(mappedBy = "boat", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<NavigationEquipment> navigationEquipment = new HashSet<>();
	@JsonBackReference(value = "boat")
	@OneToMany(mappedBy = "boat", fetch = FetchType.EAGER)
	private Set<AdditionalService> additionalService = new HashSet<>();
	@OneToMany(mappedBy = "boat", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<BoatImage> boatImage;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "boatOwner_id", referencedColumnName = "id")
	private BoatOwner boatOwner;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "boat_available_date_spans", joinColumns = @JoinColumn(name = "boat_id"), foreignKey = @ForeignKey(name = "date_spans_boat"))
	private Set<DateTimeSpan> availableReservationDateSpan = new HashSet<DateTimeSpan>();
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "boat_subscribers", joinColumns = @JoinColumn(name = "boat_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
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

	public void setName(String param) {
		this.name = param;
	}

	public String getType() {
		return type;
	}

	public void setType(String param) {
		this.type = param;
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

	public void setAddress(Address param) {
		this.address = param;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String param) {
		this.description = param;
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

	public void setCapacity(Integer param) {
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

	public Set<String> getFishingEquipment() {
		return fishingEquipment;
	}

	public void setFishingEquipment(Set<String> fishingEquipment) {
		this.fishingEquipment = fishingEquipment;
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

	public Set<BoatReservation> getBoatReservation() {
		return boatReservation;
	}

	public void setBoatReservation(Set<BoatReservation> boatReservation) {
		this.boatReservation = boatReservation;
	}

}