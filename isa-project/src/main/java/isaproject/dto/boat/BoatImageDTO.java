package isaproject.dto.boat;

import isaproject.model.boat.Boat;

public class BoatImageDTO {
	private long id;
	private String image;
	private Boat boat;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Boat getBoat() {
		return boat;
	}

	public void setBoat(Boat boat) {
		this.boat = boat;
	}

}
