package isaproject.service.boat;

import isaproject.dto.boat.BoatImageDTO;

public interface BoatImageService {

	BoatImageDTO findById(Long id);

	BoatImageDTO save(BoatImageDTO BoatImage);

	BoatImageDTO deleteById(Long id);

}
