package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.LoyaltySettings;

public interface LoyaltySettingsRepository extends JpaRepository<LoyaltySettings, Long> {

}
