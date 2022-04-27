package isaproject.model;

import java.io.Serializable;
import java.util.Set;

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

@Entity
@Table(name = "CottageQuickReservation")
public class CottageQuickReservation implements Serializable {

	private static final long serialVersionUID = 1L;

	public CottageQuickReservation() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Embedded
	private DateSpan dateSpan;
	private String guestCapacity;
	private Integer price;
	@OneToMany(mappedBy = "cottageQuickReservation",fetch = FetchType.EAGER)
	private Set<AdditionalService> additionalService;
	@ManyToOne(fetch = FetchType.EAGER,targetEntity = Cottage.class)
	@JsonBackReference
	@JoinColumn(name = "cottage_id")
	private Cottage cottage;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	public DateSpan getDateSpan() {
		return dateSpan;
	}

	public void setDateSpan(DateSpan dateSpan) {
		this.dateSpan = dateSpan;
	}
	
	public Cottage getCottage() {
	    return cottage;
	}

	public void setCottage(Cottage param) {
	    this.cottage = param;
	}

}