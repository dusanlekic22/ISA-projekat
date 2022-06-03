package isaproject.model;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

@Embeddable
@MappedSuperclass
public class Grade {

	private Double value;

	private User user;

	private Boolean isAccepted;

	public Grade() {
		super();
		validate();
	}

	public Grade(Double value, User user, Boolean isAccepted) {
		super();
		this.value = value;
		this.user = user;
		this.isAccepted = isAccepted;
		validate();
	}
	
	public Grade(Grade grade) {
		super();
		this.value = grade.value;
		this.user = grade.user;
		this.isAccepted = grade.isAccepted;
		validate();
	}

	private boolean validate() {
		return value >= 1 && value <= 5;
	}

	public Double getValue() {
		return value;
	}

	public User getUser() {
		return user;
	}

	public Boolean isAccepted() {
		return isAccepted;
	}

	public void accept() {
		if (isAccepted == null) {
			isAccepted = true;
		}
	}

	public void decline() {
		if (isAccepted == null) {
			isAccepted = true;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isAccepted == null) ? 0 : isAccepted.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grade other = (Grade) obj;
		if (isAccepted == null) {
			if (other.isAccepted != null)
				return false;
		} else if (!isAccepted.equals(other.isAccepted))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
