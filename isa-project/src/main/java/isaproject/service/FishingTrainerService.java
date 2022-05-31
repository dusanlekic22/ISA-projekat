package isaproject.service;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.FishingTrainerDTO;
import isaproject.model.DateTimeSpan;
import isaproject.model.FishingTrainer;

public interface FishingTrainerService {

	FishingTrainer registerFishingTrainer(BusinessOwnerDTO businessOwnerDTO);

	FishingTrainerDTO findByUsername(String name);

	FishingTrainerDTO updateAvailableTerms(Long id, DateTimeSpan dateTimeSpan);

	FishingTrainerDTO updateUnavailableTerms(Long id, DateTimeSpan dateTimeSpan);

}