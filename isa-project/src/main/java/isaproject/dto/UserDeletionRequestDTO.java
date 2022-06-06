package isaproject.dto;

import isaproject.model.RequestStatus;

public class UserDeletionRequestDTO {
	
	private Long id;
	private RequestStatus accepted;
	private String deletionExplanation;
	private String userEmail;
	private String answer;

	public UserDeletionRequestDTO() {
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
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
