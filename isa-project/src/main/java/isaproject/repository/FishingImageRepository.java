package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.FishingImage;

public interface FishingImageRepository extends JpaRepository<FishingImage, Long> {

}
