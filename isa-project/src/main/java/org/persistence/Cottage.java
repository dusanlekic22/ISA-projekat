package org.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.persistence.CottageOwner;
import javax.persistence.ManyToOne;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.JoinColumn;

@Entity
public class Cottage implements Serializable {

	private static final long serialVersionUID = 1L;

	public Cottage() {
	}

	@Id
	private long id;
	private String name;
	@OneToOne
	private Address address;
	private String promoDescription;
	private Integer bedCount;
	private Integer roomCount;
	private String cottageRules;
	@OneToMany
	private Set<CottageQuickReservation> cottageQuickReservation;
	@OneToMany(mappedBy = "cottage")
	private Set<CottageImage> cottageImage;
	@OneToMany(mappedBy = "cottage")
	private Set<CottageReservation> cottageReservation;
	@OneToMany(mappedBy = "cottage")
	private Collection<AdditionalService> additionalService;
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

	public Collection<AdditionalService> getAdditionalService() {
	    return additionalService;
	}

	public void setAdditionalService(Collection<AdditionalService> param) {
	    this.additionalService = param;
	}

	public CottageOwner getCottageOwner() {
	    return cottageOwner;
	}

	public void setCottageOwner(CottageOwner param) {
	    this.cottageOwner = param;
	}

}