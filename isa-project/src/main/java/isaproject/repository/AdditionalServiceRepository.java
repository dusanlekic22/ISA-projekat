package isaproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.AdditionalService;

public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Long> {

	List<AdditionalService> findByCottageIsNull();

	List<AdditionalService> findByCottageIdIs(Long id);

	List<AdditionalService> findByFishingCourseId(Long id);
}
