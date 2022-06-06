package isaproject.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "FishingTrainer")
public class FishingTrainer extends User {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "fishingTrainer", fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<FishingCourse> fishingCourse = new HashSet<FishingCourse>();

	private String biography = "";

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "fishing_trainer_available_date_spans", joinColumns = @JoinColumn(name = "fishing_trainer_id"), foreignKey = @ForeignKey(name = "date_spans_fishing_trainer"))
	private Set<DateTimeSpan> availableReservationDateSpan = new HashSet<DateTimeSpan>();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "fishing_trainer_unavailable_date_spans", joinColumns = @JoinColumn(name = "fishing_trainer_id"), foreignKey = @ForeignKey(name = "no_date_spans_fishing_trainer"))
	private Set<DateTimeSpan> unavailableReservationDateSpan = new HashSet<DateTimeSpan>();

	@OneToMany(mappedBy = "fishingTrainer", fetch = FetchType.EAGER)
	@JsonManagedReference(value = "fishingTrainerGrades")
	private Set<Grade> grades = new HashSet<Grade>();
	@SuppressWarnings("unused")
	private Double averageGrade = 0.0;

	@Embedded
	private LoyaltyProgram loyaltyProgram;

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
			for (Grade grade : grades) {
				if (grade.getIsAccepted())
					sum += grade.getValue();
			}
			return sum / grades.size();
		} else {
			return sum;
		}
	}

	public void setAverageGrade() {
		Double sum = 0.0;
		if (grades.size() > 0) {
			for (Grade grade : grades) {
				if (grade.getIsAccepted())
					sum += grade.getValue();
			}
			averageGrade = sum / grades.size();
		} else {
			averageGrade = sum;
		}
	}

	public LoyaltyProgram getLoyaltyProgram() {
		return loyaltyProgram;
	}

	public void setLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
		this.loyaltyProgram = loyaltyProgram;
	}

}