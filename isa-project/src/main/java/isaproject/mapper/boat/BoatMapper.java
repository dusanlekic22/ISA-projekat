package isaproject.mapper.boat;

import isaproject.dto.boat.BoatDTO;
import isaproject.model.boat.Boat;

public class BoatMapper {
	public static Boat BoatDTOToBoat(BoatDTO boatDTO) {
		Boat boat = new Boat();
		boat.setId(boatDTO.getId());
		boat.setName(boatDTO.getName());
		boat.setAddress(boatDTO.getAddress());
		boat.setLength(boatDTO.getLength());
		boat.setType(boatDTO.getType());
		boat.setTopSpeed(boatDTO.getTopSpeed());
		boat.setEngineNumber(boatDTO.getEngineNumber());
		boat.setEnginePower(boatDTO.getEnginePower());
		boat.setDescription(boatDTO.getDescription());
		boat.setCapacity(boatDTO.getCapacity());
		boat.setCancelCondition(boatDTO.getCancelCondition());
		boat.setPricePerHour(boatDTO.getPricePerHour());
		boat.setAdditionalService(boatDTO.getAdditionalService());
		boat.setBoatImage(boatDTO.getBoatImage());
		boat.setBoatOwner(boatDTO.getBoatOwner());
		boat.setBoatQuickReservation(boatDTO.getBoatQuickReservation());
		boat.setBoatReservation(boatDTO.getBoatReservation());
		boat.setBoatRules(boatDTO.getBoatRules());
		boat.setAvailableReservationDateSpan(boatDTO.getAvailableReservationDateSpan());
		boat.setBoatOwner(boatDTO.getBoatOwner());
		boat.setSubscribers(boatDTO.getSubscribers());
		return boat;
	}
	
	public static BoatDTO BoatToBoatDTO(Boat boat) {
		BoatDTO boatDTO = new BoatDTO();
		boatDTO.setId(boat.getId());
		boatDTO.setName(boat.getName());
		boatDTO.setAddress(boat.getAddress());
		boatDTO.setLength(boat.getLength());
		boatDTO.setType(boat.getType());
		boatDTO.setTopSpeed(boat.getTopSpeed());
		boatDTO.setEngineNumber(boat.getEngineNumber());
		boatDTO.setEnginePower(boat.getEnginePower());
		boatDTO.setDescription(boat.getDescription());
		boatDTO.setCapacity(boat.getCapacity());
		boatDTO.setCancelCondition(boat.getCancelCondition());
		boatDTO.setPricePerHour(boat.getPricePerHour());
		boatDTO.setAdditionalService(boat.getAdditionalService());
		boatDTO.setBoatImage(boat.getBoatImage());
		boatDTO.setBoatOwner(boat.getBoatOwner());
		boatDTO.setBoatQuickReservation(boat.getBoatQuickReservation());
		boatDTO.setBoatReservation(boat.getBoatReservation());
		boatDTO.setBoatRules(boat.getBoatRules());
		boatDTO.setAvailableReservationDateSpan(boat.getAvailableReservationDateSpan());
		boatDTO.setBoatOwner(boat.getBoatOwner());
		boatDTO.setSubscribers(boat.getSubscribers());
		return boatDTO;
	}
}
