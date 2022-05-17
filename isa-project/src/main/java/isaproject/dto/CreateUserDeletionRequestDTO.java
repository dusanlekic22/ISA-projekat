package isaproject.dto;

public class CreateUserDeletionRequestDTO {

	private String deletionExplanation;
	private String userEmail;
	
	public CreateUserDeletionRequestDTO() {
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
