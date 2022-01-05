package isaproject.dto;

public class BusinessOwnerRegistrationRequestDTO {
	
	private long id;
	private Boolean accepted;
	private String registrationExplanation;
	private String declineReason;
	private String userEmail;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Boolean getAccepted() {
		return accepted;
	}
	
	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	public String getRegistrationExplanation() {
		return registrationExplanation;
	}

	public void setRegistrationExplanation(String registrationExplanation) {
		this.registrationExplanation = registrationExplanation;
	}

	public String getDeclineReason() {
		return declineReason;
	}

	public void setDeclineReason(String declineReason) {
		this.declineReason = declineReason;
	}
	
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
