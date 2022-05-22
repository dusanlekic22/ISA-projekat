package isaproject.service;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.model.CottageOwner;

public interface CottageOwnerService {
	
	CottageOwner registerCottageOwner(BusinessOwnerDTO businessOwnerDTO);

}
