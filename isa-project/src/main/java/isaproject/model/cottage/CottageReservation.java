package isaproject.model.cottage;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import isaproject.model.AdditionalService;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;

@Entity
@Table(name = "CottageReservation")
public class CottageReservation implements Serializable {

	private static final long serialVersionUID = 1L;

	public CottageReservation() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Embedded
	private DateTimeSpan duration;
	private Integer guestCapacity;
	private Double price;
	private Double ownerIncome;
	private Double siteIncome;
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Cottage.class)
	@JoinColumn(name = "cottage_id")
	@JsonBackReference("cottageReservation")
	private Cottage cottage;
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference("cottageReservationCustomer")
	private Customer customer;
	@OneToMany(mappedBy = "cottageReservation", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JsonManagedReference(value = "cottageReservationService")
	private Set<AdditionalService> additionalService;
	private boolean confirmed;
	private boolean isCancelled;

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DateTimeSpan getDuration() {
		return duration;
	}

	public void setDuration(DateTimeSpan duration) {
		this.duration = duration;
	}

	public Integer getGuestCapacity() {
		return guestCapacity;
	}

	public void setGuestCapacity(Integer guestCapacity) {
		this.guestCapacity = guestCapacity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getOwnerIncome() {
		return ownerIncome;
	}

	public void setOwnerIncome(Double ownerIncome) {
		this.ownerIncome = ownerIncome;
	}

	public Double getSiteIncome() {
		return siteIncome;
	}

	public void setSiteIncome(Double siteIncome) {
		this.siteIncome = siteIncome;
	}

	public Cottage getCottage() {
		return cottage;
	}

	public void setCottage(Cottage cottage) {
		this.cottage = cottage;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<AdditionalService> getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(Set<AdditionalService> additionalService) {
		this.additionalService = additionalService;
	}

}