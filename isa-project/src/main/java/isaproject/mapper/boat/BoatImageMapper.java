package isaproject.mapper.boat;

import isaproject.dto.boat.BoatImageDTO;
import isaproject.model.boat.BoatImage;

public class BoatImageMapper {
	public static BoatImage BoatImageDTOToBoatImage(BoatImageDTO boatImageDTO) {
		BoatImage boatImage = new BoatImage();
		boatImage.setId(boatImageDTO.getId());
		boatImage.setImage(boatImageDTO.getImage());
		boatImage.setBoat(boatImageDTO.getBoat());
		return boatImage;
	}
	
	public static BoatImageDTO BoatImageToBoatImageDTO(BoatImage boatImage) {
		BoatImageDTO boatImageDTO = new BoatImageDTO();
		boatImageDTO.setId(boatImage.getId());
		boatImageDTO.setImage(boatImage.getImage());
		boatImageDTO.setBoat(boatImage.getBoat());
		return boatImageDTO;
	}

}
