package isaproject.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Set;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "CottageQuickReservation")
public class CottageQuickReservation implements Serializable {

	private static final long serialVersionUID = 1L;

	public CottageQuickReservation() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Temporal(TIMESTAMP)
	private Date startDate;
	@Temporal(TIMESTAMP)
	private Date endDate;
	private String guestCapacity;
	private Integer price;
	@OneToMany(mappedBy = "cottageQuickReservation")
	private Set<AdditionalService> additionalService;
	@ManyToOne(fetch = LAZY)
	private Cottage cottage;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date param) {
		this.startDate = param;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getGuestCapacity() {
		return guestCapacity;
	}

	public void setGuestCapacity(String param) {
		this.guestCapacity = param;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer param) {
		this.price = param;
	}

	public Set<AdditionalService> getAdditionalService() {
	    return additionalService;
	}

	public void setAdditionalService(Set<AdditionalService> param) {
	    this.additionalService = param;
	}
	
	public Cottage getCottage() {
	    return cottage;
	}

	public void setCottage(Cottage param) {
	    this.cottage = param;
	}

}