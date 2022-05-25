package isaproject.model.cottage;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import isaproject.model.User;

@Entity
public class CottageOwner extends User {

	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy = "cottageOwner",fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<Cottage> cottage;
	public CottageOwner() {
	}
	public Set<Cottage> getCottage() {
	    return cottage;
	}
	public void setCottage(Set<Cottage> param) {
	    this.cottage = param;
	}
}