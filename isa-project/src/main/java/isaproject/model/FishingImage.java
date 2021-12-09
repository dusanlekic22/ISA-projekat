package isaproject.model;

import java.io.Serializable;
import javax.persistence.*;

import isaproject.model.FishingCourse;

import static javax.persistence.FetchType.LAZY;

@Entity
public class FishingImage implements Serializable {

	private static final long serialVersionUID = 1L;

	public FishingImage() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String image;
	@ManyToOne(fetch = LAZY)
	private FishingCourse fishingCourse;

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

	public FishingCourse getFishingCourse() {
	    return fishingCourse;
	}

	public void setFishingCourse(FishingCourse param) {
	    this.fishingCourse = param;
	}

}