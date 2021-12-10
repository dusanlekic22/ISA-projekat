package isaproject.model;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Cottage implements Serializable {

	private static final long serialVersionUID = 1L;

	public Cottage() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@OneToOne
	private Address address;
	private String promoDescription;
	private Integer bedCount;
	private Integer roomCount;
	private String cottageRules;
	@OneToMany(mappedBy = "cottage",fetch = FetchType.EAGER)
	private Set<CottageQuickReservation> cottageQuickReservation = new HashSet<>();
	@OneToMany(mappedBy = "cottage",fetch = FetchType.EAGER)
	private Set<CottageImage> cottageImage = new HashSet<>();
	@OneToMany(mappedBy = "cottage",fetch = FetchType.EAGER)
	private Set<CottageReservation> cottageReservation = new HashSet<>();
	@OneToMany(mappedBy = "cottage",fetch = FetchType.EAGER)
	private Set<AdditionalService> additionalService = new HashSet<>();
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "cottageOwner_id", referencedColumnName = "id")
	private CottageOwner cottageOwner;
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