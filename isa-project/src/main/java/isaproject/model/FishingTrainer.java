package isaproject.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "FishingTrainer")
public class FishingTrainer extends User {

	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy = "fishingTrainer")
	private Set<FishingCourse> fishingCourse = new HashSet<FishingCourse>();
	
	public FishingTrainer() {
	}
	
	public Set<FishingCourse> getFishingCourse() {
	    return fishingCourse;
	}
	public void setFishingCourse(Set<FishingCourse> param) {
	    this.fishingCourse = param;
	}
}