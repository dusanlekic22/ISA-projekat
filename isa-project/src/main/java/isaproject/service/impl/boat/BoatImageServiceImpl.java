package isaproject.service.impl.boat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.boat.BoatImageDTO;
import isaproject.mapper.boat.BoatImageMapper;
import isaproject.model.boat.BoatImage;
import isaproject.repository.boat.BoatImageRepository;
import isaproject.service.boat.BoatImageService;

@Service
public class BoatImageServiceImpl implements BoatImageService {

	@Autowired
	private BoatImageRepository BoatImageRepository;


	public BoatImageDTO findById(Long id) {
		BoatImage BoatImage = BoatImageRepository.getById(id);
		return BoatImageMapper.BoatImageToBoatImageDTO(BoatImage);
	}

	@Transactional
	@Override
	public BoatImageDTO save(BoatImageDTO BoatImageDTO) {
		BoatImage BoatImage = BoatImageMapper.BoatImageDTOToBoatImage(BoatImageDTO);
		return BoatImageMapper.BoatImageToBoatImageDTO(BoatImageRepository.save(BoatImage));
	}

	@Transactional
	@Override
	public BoatImageDTO deleteById(Long id) {
		BoatImageDTO BoatImageDTO = findById(id);
		BoatImageRepository.deleteById(id);
		return BoatImageDTO;
	}
}
