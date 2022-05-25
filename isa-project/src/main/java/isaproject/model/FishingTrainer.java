package isaproject.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "FishingTrainer")
public class FishingTrainer extends User {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "fishingTrainer", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<FishingCourse> fishingCourse = new HashSet<FishingCourse>();

	private String biography = "";

	public FishingTrainer() {
	}

	public Set<FishingCourse> getFishingCourse() {
		return fishingCourse;
	}

	public void setFishingCourse(Set<FishingCourse> param) {
		this.fishingCourse = param;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

}