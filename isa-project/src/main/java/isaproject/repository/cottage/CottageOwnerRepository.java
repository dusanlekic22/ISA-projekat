package isaproject.repository.cottage;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.cottage.CottageOwner;

public interface CottageOwnerRepository extends JpaRepository<CottageOwner, Long>{

	CottageOwner findByUsername(String username);
	
}
