package isaproject.service.boat;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.boat.BoatOwnerDTO;
import isaproject.model.DateTimeSpan;
import isaproject.model.boat.BoatOwner;

public interface BoatOwnerService {

	BoatOwner registerBoatOwner(BusinessOwnerDTO businessOwnerDTO);
	
	BoatOwnerDTO updateUnavailableTerms(Long id, DateTimeSpan dateTimeSpan);

	BoatOwnerDTO findByUsername(String username);
}
