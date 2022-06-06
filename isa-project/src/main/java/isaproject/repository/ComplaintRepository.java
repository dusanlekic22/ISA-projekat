package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

}
