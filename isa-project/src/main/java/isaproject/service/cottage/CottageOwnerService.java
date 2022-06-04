package isaproject.service.cottage;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.cottage.CottageOwnerDTO;
import isaproject.model.DateTimeSpan;
import isaproject.model.cottage.CottageOwner;

public interface CottageOwnerService {
	
	CottageOwner registerCottageOwner(BusinessOwnerDTO businessOwnerDTO);

	CottageOwnerDTO updateUnavailableTerms(Long id, DateTimeSpan dateTimeSpan);

	CottageOwnerDTO findByUsername(String username);

	void promoteLoyaltyCottageOwner(CottageOwner owner);

}
