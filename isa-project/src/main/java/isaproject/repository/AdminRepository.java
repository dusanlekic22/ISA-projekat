package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin findByUsername(String username);
}
