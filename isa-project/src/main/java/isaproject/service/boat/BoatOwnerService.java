package isaproject.service.boat;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.model.boat.BoatOwner;

public interface BoatOwnerService {

	BoatOwner registerBoatOwner(BusinessOwnerDTO businessOwnerDTO);
}
