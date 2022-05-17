package isaproject.model;

import javax.persistence.Entity;

@Entity
public class Admin extends User {

	private static final long serialVersionUID = 1L;
	
    private Boolean firstTimeLoggedIn;

	public Boolean getFirstTimeLoggedIn() {
		return firstTimeLoggedIn;
	}

	public void setFirstTimeLoggedIn(Boolean firstTimeLoggedIn) {
		this.firstTimeLoggedIn = firstTimeLoggedIn;
	}

}
