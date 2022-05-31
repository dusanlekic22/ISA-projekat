package isaproject.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "FishingTrainer")
public class FishingTrainer extends User {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "fishingTrainer", fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<FishingCourse> fishingCourse = new HashSet<FishingCourse>();

	private String biography = "";

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "fishing_trainer_available_date_spans",
					 joinColumns = @JoinColumn(name = "fishing_trainer_id"),
					 foreignKey = @ForeignKey(name = "date_spans_fishing_trainer"))
	private Set<DateTimeSpan> availableReservationDateSpan = new HashSet<DateTimeSpan>();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "fishing_trainer_unavailable_date_spans",
					 joinColumns = @JoinColumn(name = "fishing_trainer_id"),
					 foreignKey = @ForeignKey(name = "no_date_spans_fishing_trainer"))
	private Set<DateTimeSpan> unavailableReservationDateSpan = new HashSet<DateTimeSpan>();

	public FishingTrainer() {
	}

	public Set<FishingCourse> getFishingCourse() {
		return fishingCourse;
	}

	public void setFishingCourse(Set<FishingCourse> param) {
		this.fishingCourse = param;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public Set<DateTimeSpan> getAvailableReservationDateSpan() {
		return availableReservationDateSpan;
	}

	public void setAvailableReservationDateSpan(Set<DateTimeSpan> availableReservationDateSpan) {
		this.availableReservationDateSpan = availableReservationDateSpan;
	}

	public Set<DateTimeSpan> getUnavailableReservationDateSpan() {
		return unavailableReservationDateSpan;
	}

	public void setUnavailableReservationDateSpan(Set<DateTimeSpan> unavailableReservationDateSpan) {
		this.unavailableReservationDateSpan = unavailableReservationDateSpan;
	}

}