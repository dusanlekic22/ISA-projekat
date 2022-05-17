package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.UserDeletionRequest;

public interface UserDeletionRequestRepository extends JpaRepository<UserDeletionRequest, Long> {

}
