package isaproject.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.FishingTrainerDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.boat.BoatDTO;
import isaproject.model.DateTimeSpan;
import isaproject.model.FishingTrainer;

public interface FishingTrainerService {
	
	Page<FishingTrainerDTO> findAllPagination(List<SortTypeDTO> sortTypeDTO,Pageable paging );

	FishingTrainer registerFishingTrainer(BusinessOwnerDTO businessOwnerDTO);

	FishingTrainerDTO findByUsername(String name);

	FishingTrainerDTO updateAvailableTerms(Long id, DateTimeSpan dateTimeSpan);

	FishingTrainerDTO updateUnavailableTerms(Long id, DateTimeSpan dateTimeSpan);

}