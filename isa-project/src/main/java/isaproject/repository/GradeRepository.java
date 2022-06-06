package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.Grade;

public interface GradeRepository extends JpaRepository<Grade, Long> {

}
