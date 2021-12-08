package isaproject.model;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
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
	private long id;
	@Temporal(TIMESTAMP)
	private Date startDate;
	@Temporal(TIMESTAMP)
	private Date endDate;
	private String guestCapacity;
	private String additionalService;
	private Integer price;
	@OneToMany(mappedBy = "cottageQuickReservation")
	private Collection<AdditionalService> additionalService_1;
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

	public String getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(String param) {
		this.additionalService = param;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer param) {
		this.price = param;
	}

	public Collection<AdditionalService> getAdditionalService_1() {
	    return additionalService_1;
	}

	public void setAdditionalService_1(Collection<AdditionalService> param) {
	    this.additionalService_1 = param;
	}

}