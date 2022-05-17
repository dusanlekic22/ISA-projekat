package isaproject.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
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
	private DateSpan duration;
	@Embedded
	@AttributeOverride(name="startDate", column=@Column(name="valid_start_date"))
	@AttributeOverride(name="endDate", column=@Column(name="valid_end_date"))
	private DateSpan validSpan;
	private String guestCapacity;
	private Integer price;
	@OneToMany(mappedBy = "cottageQuickReservation", fetch = FetchType.EAGER)
	private Set<AdditionalService> additionalService;
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Cottage.class)
	@JoinColumn(name = "cottage_id")
	@JsonBackReference
	private Cottage cottage;

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
	
	public DateSpan getValidSpan() {
		return validSpan;
	}

	public void setValidSpan(DateSpan validSpan) {
		this.validSpan = validSpan;
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

	public Set<AdditionalService> getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(Set<AdditionalService> additionalService) {
		this.additionalService = additionalService;
	}

	public Cottage getCottage() {
		return cottage;
	}

	public void setCottage(Cottage cottage) {
		this.cottage = cottage;
	}

}