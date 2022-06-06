package isaproject.model.cottage;

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
import isaproject.model.User;

@Entity
public class CottageOwner extends User {

	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy = "cottageOwner", fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<Cottage> cottage;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "cottage_owner_unavailable_date_spans", joinColumns = @JoinColumn(name = "cottage_owner_id"), foreignKey = @ForeignKey(name = "no_date_spans_cottage_owner"))
	private Set<DateTimeSpan> unavailableReservationDateSpan = new HashSet<DateTimeSpan>();

	@Embedded
	private LoyaltyProgram loyaltyProgram;

	@OneToMany(mappedBy = "cottageOwner", fetch = FetchType.EAGER)
	@JsonManagedReference(value = "cottageOwnerGrades")
	private Set<Grade> grades = new HashSet<Grade>();
	@SuppressWarnings("unused")
	private Double averageGrade = 0.0;

	public CottageOwner() {
	}

	public Set<Cottage> getCottage() {
		return cottage;
	}

	public void setCottage(Set<Cottage> param) {
		this.cottage = param;
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
}