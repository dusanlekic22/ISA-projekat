package isaproject.repository.cottage;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.cottage.Cottage;

public interface CottageRepository extends JpaRepository<Cottage, Long> {
	Set<Cottage> findByName(String name);

	List<Cottage> findByCottageOwnerId(Long id);

}
