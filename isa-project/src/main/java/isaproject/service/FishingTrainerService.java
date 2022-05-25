package isaproject.service;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.FishingTrainerDTO;
import isaproject.model.FishingTrainer;

public interface FishingTrainerService {

	FishingTrainer registerFishingTrainer(BusinessOwnerDTO businessOwnerDTO);

	FishingTrainerDTO findByUsername(String name);

}