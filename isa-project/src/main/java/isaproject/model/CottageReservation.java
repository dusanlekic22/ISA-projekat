package isaproject.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	private DateSpan duration;
	private String guestCapacity;
	private Integer price;
	@ManyToOne
	@JsonBackReference
	private Cottage cottage;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	private Customer customer;
	@OneToMany(mappedBy = "cottageReservation", fetch = FetchType.EAGER)
	private Set<AdditionalService> additionalService;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DateSpan getDuration() {
		return duration;
	}

	public void setDuration(DateSpan duration) {
		this.duration = duration;
	}

	public String getGuestCapacity() {
		return guestCapacity;
	}

	public void setGuestCapacity(String guestCapacity) {
		this.guestCapacity = guestCapacity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
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