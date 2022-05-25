package isaproject.service.cottage;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.model.cottage.CottageOwner;

public interface CottageOwnerService {
	
	CottageOwner registerCottageOwner(BusinessOwnerDTO businessOwnerDTO);

}
