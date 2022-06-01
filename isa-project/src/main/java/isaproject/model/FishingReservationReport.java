package isaproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import static javax.persistence.FetchType.EAGER;

@Entity
public class FishingReservationReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Boolean userPenalized;

	private String comment;

	private ReservationReportStatus reservationReportStatus;

	@OneToOne(fetch = EAGER)
	@JoinColumn(name = "fishingReservation_id", referencedColumnName = "id")
	private FishingReservation fishingReservation;

	public FishingReservationReport() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getUserPenalized() {
		return userPenalized;
	}

	public void setUserPenalized(Boolean accepted) {
		this.userPenalized = accepted;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ReservationReportStatus getReservationReportStatus() {
		return reservationReportStatus;
	}

	public void setReservationReportStatus(ReservationReportStatus reservationReportStatus) {
		this.reservationReportStatus = reservationReportStatus;
	}

	public FishingReservation getFishingReservation() {
		return fishingReservation;
	}

	public void setFishingReservation(FishingReservation fishingReservation) {
		this.fishingReservation = fishingReservation;
	}

}
