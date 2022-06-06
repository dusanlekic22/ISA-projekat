package isaproject.model.boat;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import isaproject.model.DateTimeSpan;
import isaproject.model.Grade;
import isaproject.model.LoyaltyProgram;
import isaproject.model.RequestStatus;
import isaproject.model.User;

@Entity
public class BoatOwner extends User {

	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy = "boatOwner", fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<Boat> boat;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "boat_owner_unavailable_date_spans", joinColumns = @JoinColumn(name = "boat_owner_id"), foreignKey = @ForeignKey(name = "no_date_spans_boat_owner"))
	private Set<DateTimeSpan> unavailableReservationDateSpan = new HashSet<DateTimeSpan>();

	@Embedded
	private LoyaltyProgram loyaltyProgram;

	@OneToMany(mappedBy = "boatOwner", fetch = FetchType.EAGER)
	@JsonManagedReference(value = "boatOwnerGrades")
	private Set<Grade> grades = new HashSet<Grade>();
	@SuppressWarnings("unused")
	private Double averageGrade = 0.0;

	public BoatOwner() {
	}

	public Set<Boat> getBoat() {
		return boat;
	}

	public void setBoat(Set<Boat> param) {
		this.boat = param;
	}

	public Set<DateTimeSpan> getUnavailableReservationDateSpan() {
		return unavailableReservationDateSpan;
	}

	public void setUnavailableReservationDateSpan(Set<DateTimeSpan> unavailableReservationDateSpan) {
		this.unavailableReservationDateSpan = unavailableReservationDateSpan;
	}

	public LoyaltyProgram getLoyaltyProgram() {
		return loyaltyProgram;
	}

	public void setLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
		this.loyaltyProgram = loyaltyProgram;
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
				if (grade.getIsAccepted() == RequestStatus.Accepted)
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
				if (grade.getIsAccepted() == RequestStatus.Accepted)
					sum += grade.getValue();
			}
			averageGrade = sum / grades.size();
		} else {
			averageGrade = sum;
		}
	}

}