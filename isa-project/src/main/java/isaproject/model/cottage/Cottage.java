package isaproject.model.cottage;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import isaproject.model.AdditionalService;
import isaproject.model.Address;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.model.Grade;
import isaproject.model.RequestStatus;

@Entity(name = "cottage")
public class Cottage implements Serializable {

	private static final long serialVersionUID = 1L;

	public Cottage() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Address address;
	private String promoDescription;
	private Integer bedCount;
	private Integer roomCount;
	private String cottageRules;
	private Double pricePerHour;
	@OneToMany(mappedBy = "cottage", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<CottageQuickReservation> cottageQuickReservation = new HashSet<>();
	@OneToMany(mappedBy = "cottage", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<CottageImage> cottageImage = new HashSet<>();
	@JsonManagedReference("cottageReservation")
	@OneToMany(mappedBy = "cottage", fetch = FetchType.EAGER)
	private Set<CottageReservation> cottageReservation = new HashSet<>();
	@OneToMany(mappedBy = "cottage", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference(value = "cottage")
	private Set<AdditionalService> additionalService = new HashSet<>();
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cottageOwner_id", referencedColumnName = "id")
	private CottageOwner cottageOwner;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "cottage_available_date_spans", joinColumns = @JoinColumn(name = "cottage_id"), foreignKey = @ForeignKey(name = "date_spans_cottage"))
	private Set<DateTimeSpan> availableReservationDateSpan = new HashSet<DateTimeSpan>();
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "cottage_unavailable_date_spans", joinColumns = @JoinColumn(name = "cottage_id"), foreignKey = @ForeignKey(name = "no_date_spans_cottage"))
	private Set<DateTimeSpan> unavailableReservationDateSpan = new HashSet<DateTimeSpan>();
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "cottage_subscribers", joinColumns = @JoinColumn(name = "cottage_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> subscribers = new HashSet<>();
	@OneToMany(mappedBy = "cottage", fetch = FetchType.EAGER)
	@JsonManagedReference(value = "cottageGrades")
	private Set<Grade> grades = new HashSet<Grade>();
	@SuppressWarnings("unused")
	private Double averageGrade = 0.0;
	@Column(columnDefinition = "boolean default false")
	private Boolean deleted = false;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String param) {
		this.name = param;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address param) {
		this.address = param;
	}

	public String getPromoDescription() {
		return promoDescription;
	}

	public void setPromoDescription(String param) {
		this.promoDescription = param;
	}

	public String getCottageRules() {
		return cottageRules;
	}

	public void setCottageRules(String param) {
		this.cottageRules = param;
	}

	public Set<DateTimeSpan> getAvailableReservationDateSpan() {
		return availableReservationDateSpan;
	}

	public void setAvailableReservationDateSpan(Set<DateTimeSpan> availableReservationDateSpan) {
		this.availableReservationDateSpan = availableReservationDateSpan;
	}

	public Set<CottageQuickReservation> getCottageQuickReservation() {
		return cottageQuickReservation;
	}

	public void setCottageQuickReservation(Set<CottageQuickReservation> param) {
		this.cottageQuickReservation = param;
	}

	public Set<CottageImage> getCottageImage() {
		return cottageImage;
	}

	public void setCottageImage(Set<CottageImage> param) {
		this.cottageImage = param;
	}

	public void addCottageImage(CottageImage param) {
		param.setCottage(this);
		cottageImage.add(param);
	}

	public Set<CottageReservation> getCottageReservation() {
		return cottageReservation;
	}

	public void setCottageReservation(Set<CottageReservation> param) {
		this.cottageReservation = param;
	}

	public Set<AdditionalService> getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(Set<AdditionalService> param) {
		this.additionalService = param;
	}

	public CottageOwner getCottageOwner() {
		return cottageOwner;
	}

	public void setCottageOwner(CottageOwner param) {
		this.cottageOwner = param;
	}

	public Integer getBedCount() {
		return bedCount;
	}

	public void setBedCount(Integer bedCount) {
		this.bedCount = bedCount;
	}

	public Integer getRoomCount() {
		return roomCount;
	}

	public void setRoomCount(Integer roomCount) {
		this.roomCount = roomCount;
	}

	public Set<Customer> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Set<Customer> subscribers) {
		this.subscribers = subscribers;
	}

	public Double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public Set<DateTimeSpan> getUnavailableReservationDateSpan() {
		return unavailableReservationDateSpan;
	}

	public void setUnavailableReservationDateSpan(Set<DateTimeSpan> unavailableReservationDateSpan) {
		this.unavailableReservationDateSpan = unavailableReservationDateSpan;
	}

	public Set<Grade> getGrades() {
		return grades;
	}

	public void addGrade(Grade grade) {
		grades.add(grade);
		setAverageGrade();
	}

	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}

	public Double getAverageGrade() {
		Double sum = 0.0;
		if (grades.size() > 0) {
			Double count = 0.0;
			for (Grade grade : grades) {
				if (grade.getIsAccepted() == RequestStatus.Accepted) {
					count += 1;
					sum += grade.getValue();
				}
			}
			return sum / count;
		} else {
			return sum;
		}
	}

	public void setAverageGrade() {
		Double sum = 0.0;
		if (grades.size() > 0) {
			Double count = 0.0;
			for (Grade grade : grades) {
				if (grade.getIsAccepted() == RequestStatus.Accepted) {
					count += 1;
					sum += grade.getValue();
				}
			}
			averageGrade = sum / count;
		} else {
			averageGrade = sum;
		}
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

}