package isaproject.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "CottageQuickReservation")
public class CottageQuickReservation implements Serializable {

	private static final long serialVersionUID = 1L;

	public CottageQuickReservation() {
	}
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Temporal(TemporalType.DATE)
	private Date endDate;
	private String guestCapacity;
	private Integer price;
	@OneToMany(mappedBy = "cottageQuickReservation",fetch = FetchType.EAGER)
	private Set<AdditionalService> additionalService;
	@ManyToOne(fetch = FetchType.EAGER,targetEntity = Cottage.class)
	@JoinColumn(name = "cottage_id")
	@JsonBackReference
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