package isaproject.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "NavigationEquipment")
public class NavigationEquipment implements Serializable {

	private static final long serialVersionUID = 1L;

	public NavigationEquipment() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
	@JsonBackReference
	private Boat boat;
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Boat getBoat() {
		return boat;
	}

	public void setBoat(Boat param) {
		this.boat = param;
	}

	public String getName() {
		return name;
	}

	public void setName(String param) {
		this.name = param;
	}

}