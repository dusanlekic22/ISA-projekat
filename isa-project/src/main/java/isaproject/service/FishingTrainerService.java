package isaproject.service;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.model.FishingTrainer;

public interface FishingTrainerService {

	FishingTrainer registerFishingTrainer(BusinessOwnerDTO businessOwnerDTO);

}