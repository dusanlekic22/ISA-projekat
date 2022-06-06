package isaproject.model;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

@Entity
public class FishingCourse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String promoDescription;

	private Integer capacity;

	private String fishingRules;

	private String fishingEquipment;

	private Double price = 0.0;

	private Double cancellationPercentageKeep = 0.0;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;

	@OneToMany(mappedBy = "fishingCourse", fetch = FetchType.EAGER, cascade = { PERSIST, MERGE })
	private Set<FishingImage> fishingImage = new HashSet<FishingImage>();

	@OneToMany(mappedBy = "fishingCourse", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<FishingQuickReservation> fishingQuickReservation = new HashSet<FishingQuickReservation>();

	@OneToMany(mappedBy = "fishingCourse", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference(value = "fishingCourse")
	private Set<AdditionalService> additionalService = new HashSet<AdditionalService>();

	@OneToMany(mappedBy = "fishingCourse", fetch = FetchType.EAGER)
	@JsonManagedReference("fishingReservation")
	private Set<FishingReservation> fishingReservation = new HashSet<FishingReservation>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fishingTrainer_id", referencedColumnName = "id")
	private FishingTrainer fishingTrainer;

	@ManyToMany(fetch = EAGER)
	@JoinTable(name = "fishing_course_subscribers", joinColumns = @JoinColumn(name = "fishingCourse_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> subscribers = new HashSet<>();

	@OneToMany(mappedBy = "fishingCourse", fetch = FetchType.EAGER)
	@JsonManagedReference(value = "fishingCourseGrades")
	private Set<Grade> grades = new HashSet<Grade>();
	@SuppressWarnings("unused")
	private Double averageGrade = 0.0;

	@Column(columnDefinition = "boolean default false")
	private Boolean deleted = false;
	
	public FishingCourse() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPromoDescription() {
		return promoDescription;
	}

	public void setPromoDescription(String promoDescription) {
		this.promoDescription = promoDescription;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getFishingRules() {
		return fishingRules;
	}

	public void setFishingRules(String fishingRules) {
		this.fishingRules = fishingRules;
	}

	public String getFishingEquipment() {
		return fishingEquipment;
	}

	public void setFishingEquipment(String fishingEquipment) {
		this.fishingEquipment = fishingEquipment;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getCancellationPercentageKeep() {
		return cancellationPercentageKeep;
	}

	public void setCancellationPercentageKeep(Double cancellationPercentageKeep) {
		this.cancellationPercentageKeep = cancellationPercentageKeep;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<FishingImage> getFishingImage() {
		return fishingImage;
	}

	public void setFishingImage(Set<FishingImage> fishingImage) {
		this.fishingImage = fishingImage;
	}

	public Set<FishingQuickReservation> getFishingQuickReservation() {
		return fishingQuickReservation;
	}

	public void setFishingQuickReservation(Set<FishingQuickReservation> fishingQuickReservation) {
		this.fishingQuickReservation = fishingQuickReservation;
	}

	public Set<AdditionalService> getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(Set<AdditionalService> additionalService) {
		this.additionalService = additionalService;
	}

	public Set<FishingReservation> getFishingReservation() {
		return fishingReservation;
	}

	public void setFishingReservation(Set<FishingReservation> fishingReservation) {
		this.fishingReservation = fishingReservation;
	}

	public FishingTrainer getFishingTrainer() {
		return fishingTrainer;
	}

	public void setFishingTrainer(FishingTrainer fishingTrainer) {
		this.fishingTrainer = fishingTrainer;
	}

	public Set<Customer> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Set<Customer> subscribers) {
		this.subscribers = subscribers;
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