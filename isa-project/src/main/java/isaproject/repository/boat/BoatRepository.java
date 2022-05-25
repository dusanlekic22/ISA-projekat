package isaproject.repository.boat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.boat.Boat;

public interface BoatRepository extends JpaRepository<Boat, Long> {
	List<Boat> findByName(String name);

	List<Boat> findByBoatOwnerId(Long id);
}