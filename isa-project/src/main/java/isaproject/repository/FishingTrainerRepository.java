package isaproject.repository;

import java.util.Set;

import org.springframework.data.repository.PagingAndSortingRepository;

import isaproject.model.FishingTrainer;
import isaproject.model.boat.Boat;

public interface FishingTrainerRepository extends PagingAndSortingRepository<FishingTrainer, Long> {

	public Set<FishingTrainer> findAll();
	FishingTrainer findByUsername(String username);

}
