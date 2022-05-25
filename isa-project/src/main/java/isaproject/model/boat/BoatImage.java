package isaproject.model.boat;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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