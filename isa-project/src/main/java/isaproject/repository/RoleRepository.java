package isaproject.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Set<Role> findByName(String name);
}
