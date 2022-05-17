package isaproject.dto;

import java.util.Set;

import isaproject.model.AdditionalService;
import isaproject.model.Cottage;
import isaproject.model.DateSpan;

public class CottageQuickReservationDTO {

	private long id;
	private DateSpan duration;
	private DateSpan validSpan;
	private String guestCapacity;
	private Integer price;
	private Set<AdditionalService> additionalService;
	private Cottage cottage;

	public CottageQuickReservationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

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
