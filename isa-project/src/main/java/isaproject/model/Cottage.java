package isaproject.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Cottage implements Serializable {

	private static final long serialVersionUID = 1L;

	public Cottage() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@OneToOne(fetch = FetchType.EAGER)
	private Address address;
	private String promoDescription;
	private Integer bedCount;
	private Integer roomCount;
	private String cottageRules;
	@OneToMany(mappedBy = "cottage", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<CottageQuickReservation> cottageQuickReservation = new HashSet<>();
	@OneToMany(mappedBy = "cottage", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<CottageImage> cottageImage = new HashSet<>();
	@JsonManagedReference
	@OneToMany(mappedBy = "cottage", fetch = FetchType.EAGER)
	private Set<CottageReservation> cottageReservation = new HashSet<>();
	@JsonManagedReference
	@OneToMany(mappedBy = "cottage", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<AdditionalService> additionalService = new HashSet<>();
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cottageOwner_id", referencedColumnName = "id")
	@JsonBackReference
	private CottageOwner cottageOwner;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "cottage_available_date_spans", joinColumns = @JoinColumn(name = "cottage_id"), foreignKey = @ForeignKey(name = "date_spans_cottage"))
	private Set<DateSpan> availableReservationDateSpan = new HashSet<DateSpan>();

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

	public String getCottageRules() {
		return cottageRules;
	}

	public void setCottageRules(String param) {
		this.cottageRules = param;
	}

	public Set<DateSpan> getAvailableReservationDateSpan() {
		return availableReservationDateSpan;
	}

	public void setAvailableReservationDateSpan(Set<DateSpan> availableReservationDateSpan) {
		this.availableReservationDateSpan = availableReservationDateSpan;
	}

	public Set<CottageQuickReservation> getCottageQuickReservation() {
		return cottageQuickReservation;
	}

	public void setCottageQuickReservation(Set<CottageQuickReservation> param) {
		this.cottageQuickReservation = param;
	}

	public Set<CottageImage> getCottageImage() {
		return cottageImage;
	}

	public void setCottageImage(Set<CottageImage> param) {
		this.cottageImage = param;
	}

	public void addCottageImage(CottageImage param) {
		param.setCottage(this);
		cottageImage.add(param);
	}

	public Set<CottageReservation> getCottageReservation() {
		return cottageReservation;
	}

	public void setCottageReservation(Set<CottageReservation> param) {
		this.cottageReservation = param;
	}

	public Set<AdditionalService> getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(Set<AdditionalService> param) {
		this.additionalService = param;
	}

	public CottageOwner getCottageOwner() {
		return cottageOwner;
	}

	public void setCottageOwner(CottageOwner param) {
		this.cottageOwner = param;
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

}