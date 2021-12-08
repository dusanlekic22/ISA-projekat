package org.persistence;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.persistence.FishingCourse;
import java.util.Collection;
import javax.persistence.OneToMany;

@Entity
@Table(name = "FishingTrainer")
public class FishingTrainer extends User {

	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy = "fishingTrainer")
	private Collection<FishingCourse> fishingCourse;
	public FishingTrainer() {
	}
	public Collection<FishingCourse> getFishingCourse() {
	    return fishingCourse;
	}
	public void setFishingCourse(Collection<FishingCourse> param) {
	    this.fishingCourse = param;
	}
}