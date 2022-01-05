package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUsername(String username);
	User findByVerificationCode(String code);
	User findByEmail(String userEmail);
}
