package org.persistence;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.persistence.AdditionalService;
import java.util.Collection;
import javax.persistence.OneToMany;

@Entity
@Table(name = "BoatQuickReservation")
public class BoatQuickReservation implements Serializable {

	private static final long serialVersionUID = 1L;

	public BoatQuickReservation() {
	}

	@Id
	private long id;
	@Temporal(TIMESTAMP)
	private Date endDate;
	private Integer price;
	private String guestCapacity;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "boat_id", referencedColumnName = "id")
	private Boat boat;
	@Temporal(TIMESTAMP)
	private Date startDate;
	@OneToMany(mappedBy = "boatQuickReservation")
	private Collection<AdditionalService> additionalService;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setDuration(Date param) {
		this.endDate = param;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer param) {
		this.price = param;
	}

	public String getGuestCapacity() {
		return guestCapacity;
	}

	public void setGuestCapacity(String param) {
		this.guestCapacity = param;
	}

	public Boat getBoat() {
		return boat;
	}

	public void setBoat(Boat param) {
		this.boat = param;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date param) {
		this.startDate = param;
	}

	public Collection<AdditionalService> getAdditionalService() {
	    return additionalService;
	}

	public void setAdditionalService(Collection<AdditionalService> param) {
	    this.additionalService = param;
	}

}