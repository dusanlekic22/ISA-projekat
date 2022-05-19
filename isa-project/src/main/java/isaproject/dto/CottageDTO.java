package isaproject.dto;

import java.util.Set;

import isaproject.model.AdditionalService;
import isaproject.model.Address;
import isaproject.model.CottageImage;
import isaproject.model.CottageOwner;
import isaproject.model.CottageQuickReservation;
import isaproject.model.CottageReservation;
import isaproject.model.Customer;
import isaproject.model.DateSpan;

public class CottageDTO {

	private long id;
	private String name;

	private Address address;
	private String promoDescription;
	private Integer bedCount;
	private Integer roomCount;
	private String cottageRules;

	private Set<CottageQuickReservation> cottageQuickReservation;
	private Set<CottageImage> cottageImage;
	private Set<CottageReservation> cottageReservation;
	private Set<AdditionalService> additionalService;
	private Set<DateSpan> availableReservationDateSpan;
	private Set<Customer> subscribers;

	private CottageOwner cottageOwner;

	public CottageDTO() {
		super();
		// TODO Auto-generated constructor stub
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

	public Integer getBedCount() {
		return bedCount;
	}

	public void setBedCount(Integer bedCount) {
		this.bedCount = bedCount;
	}

	public Integer getRoomCount() {
		return roomCount;
	}

	public void setRoomCount(Integer roomCount) {
		this.roomCount = roomCount;
	}

	public String getCottageRules() {
		return cottageRules;
	}

	public void setCottageRules(String cottageRules) {
		this.cottageRules = cottageRules;
	}

	public Set<CottageQuickReservation> getCottageQuickReservation() {
		return cottageQuickReservation;
	}

	public void setCottageQuickReservation(Set<CottageQuickReservation> cottageQuickReservation) {
		this.cottageQuickReservation = cottageQuickReservation;
	}

	public Set<CottageImage> getCottageImage() {
		return cottageImage;
	}

	public void setCottageImage(Set<CottageImage> cottageImage) {
		this.cottageImage = cottageImage;
	}

	public Set<DateSpan> getAvailableReservationDateSpan() {
		return availableReservationDateSpan;
	}

	public void setAvailableReservationDateSpan(Set<DateSpan> availableReservationDateSpan) {
		this.availableReservationDateSpan = availableReservationDateSpan;
	}

	public Set<CottageReservation> getCottageReservation() {
		return cottageReservation;
	}

	public void setCottageReservation(Set<CottageReservation> cottageReservation) {
		this.cottageReservation = cottageReservation;
	}

	public Set<AdditionalService> getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(Set<AdditionalService> additionalService) {
		this.additionalService = additionalService;
	}

	public CottageOwner getCottageOwner() {
		return cottageOwner;
	}

	public void setCottageOwner(CottageOwner cottageOwner) {
		this.cottageOwner = cottageOwner;
	}

	public Set<Customer> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Set<Customer> subscribers) {
		this.subscribers = subscribers;
	}

}
