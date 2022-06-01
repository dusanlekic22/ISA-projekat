package isaproject.repository.boat;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.boat.BoatOwner;

public interface BoatOwnerRepository extends JpaRepository<BoatOwner, Long>{

	BoatOwner findByUsername(String username);

}
