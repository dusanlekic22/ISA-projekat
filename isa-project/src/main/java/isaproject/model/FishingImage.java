package isaproject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class FishingImage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(columnDefinition="text", length=10485760)
	private String image;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fishingCourse_id", referencedColumnName = "id")
	@JsonBackReference
	private FishingCourse fishingCourse;

	public FishingImage() {
	}

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