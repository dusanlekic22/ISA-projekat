package isaproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserDeletionRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private RequestStatus accepted;
	private String deletionExplanation;
	private String userEmail;
	
	public UserDeletionRequest() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequestStatus getAccepted() {
		return accepted;
	}

	public void setAccepted(RequestStatus accepted) {
		this.accepted = accepted;
	}

	public String getDeletionExplanation() {
		return deletionExplanation;
	}

	public void setDeletionExplanation(String deletionExplanation) {
		this.deletionExplanation = deletionExplanation;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
}
