package isaproject.model;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	private DateSpan dateSpan;
	private String guestCapacity;
	private Integer price;
	@ManyToOne
	private Cottage cottage;
	@ManyToOne(fetch = LAZY)
	private Customer customer;
	@OneToMany(mappedBy = "cottageReservation")
	private Set<AdditionalService> additionalService;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGuestCapacity() {
		return guestCapacity;
	}

	public DateSpan getDateSpan() {
		return dateSpan;
	}

	public void setDateSpan(DateSpan dateSpan) {
		this.dateSpan = dateSpan;
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

	public Cottage getCottage() {
	    return cottage;
	}

	public void setCottage(Cottage param) {
	    this.cottage = param;
	}

	public Customer getCustomer() {
	    return customer;
	}

	public void setCustomer(Customer param) {
	    this.customer = param;
	}

	public Set<AdditionalService> getAdditionalService() {
	    return additionalService;
	}

	public void setAdditionalService(Set<AdditionalService> param) {
	    this.additionalService = param;
	}

}