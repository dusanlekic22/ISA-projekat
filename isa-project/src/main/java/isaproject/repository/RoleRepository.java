package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
