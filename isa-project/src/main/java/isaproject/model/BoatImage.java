package isaproject.model;

import java.io.Serializable;
import javax.persistence.*;

import isaproject.model.Boat;

import static javax.persistence.FetchType.LAZY;

@Entity
public class BoatImage implements Serializable {

	private static final long serialVersionUID = 1L;

	public BoatImage() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String image;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "boat_id", referencedColumnName = "id")
	private Boat boat;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String param) {
		this.image = param;
	}

	public Boat getBoat() {
	    return boat;
	}

	public void setBoat(Boat param) {
	    this.boat = param;
	}

}