package isaproject.repository;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.Cottage;

public interface CottageRepository extends JpaRepository<Cottage, Long> {
	 Set<Cottage> findByName(String name);
	

}
