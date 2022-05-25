package isaproject.mapper.boat;

import java.util.HashSet;

import isaproject.dto.boat.BoatQuickReservationDTO;
import isaproject.dto.boat.BoatReservationDTO;
import isaproject.model.boat.BoatQuickReservation;
import isaproject.model.boat.BoatReservation;

public class BoatQuickReservationMapper {
	public static BoatQuickReservation BoatQuickReservationDTOToBoatQuickReservation(BoatQuickReservationDTO boatQuickReservationDTO) {
		BoatQuickReservation boatQuickReservation = new BoatQuickReservation();
		boatQuickReservation.setId(boatQuickReservationDTO.getId());
		boatQuickReservation.setDuration(boatQuickReservationDTO.getDuration());
		boatQuickReservation.setGuestCapacity(boatQuickReservationDTO.getGuestCapacity());
		boatQuickReservation.setPrice(boatQuickReservationDTO.getPrice());
		boatQuickReservation.setAdditionalService(boatQuickReservationDTO.getAdditionalService());
		boatQuickReservation.setBoat(boatQuickReservationDTO.getBoat());
		boatQuickReservation.setReserved(boatQuickReservationDTO.isReserved());
		return boatQuickReservation;
	}
	
	public static BoatQuickReservationDTO BoatQuickReservationToBoatQuickReservationDTO(BoatQuickReservation boatQuickReservation) {
		BoatQuickReservationDTO boatQuickReservationDTO = new BoatQuickReservationDTO();
		boatQuickReservationDTO.setId(boatQuickReservation.getId());
		boatQuickReservationDTO.setDuration(boatQuickReservation.getDuration());
		boatQuickReservationDTO.setGuestCapacity(boatQuickReservation.getGuestCapacity());
		boatQuickReservationDTO.setPrice(boatQuickReservation.getPrice());
		boatQuickReservationDTO.setAdditionalService(boatQuickReservation.getAdditionalService());
		boatQuickReservationDTO.setBoat(boatQuickReservation.getBoat());
		boatQuickReservationDTO.setReserved(boatQuickReservation.isReserved());
		return boatQuickReservationDTO;
	}
	
	public static BoatQuickReservation BoatReservationDTOToBoatQuickReservation(BoatReservationDTO boatReservationDTO) {
		BoatQuickReservation boatQuickReservation = new BoatQuickReservation();
		boatQuickReservation.setId(boatReservationDTO.getId());
		boatQuickReservation.setDuration(boatReservationDTO.getDuration());
		boatQuickReservation.setGuestCapacity(boatReservationDTO.getGuestCapacity());
		boatQuickReservation.setPrice(boatReservationDTO.getPrice());
		boatQuickReservation.setAdditionalService(boatReservationDTO.getAdditionalService());
		boatQuickReservation.setBoat(boatReservationDTO.getBoat());
		return boatQuickReservation;
	}
	
	public static BoatReservation BoatQuickReservationToBoatReservation(BoatQuickReservation boatQuickReservation) {
		BoatReservation boatReservation = new BoatReservation();
		boatReservation.setDuration(boatQuickReservation.getDuration());
		boatReservation.setGuestCapacity(boatQuickReservation.getGuestCapacity());
		boatReservation.setPrice(boatQuickReservation.getPrice());
		boatReservation.setAdditionalService(new HashSet<>(boatQuickReservation.getAdditionalService()));
		boatReservation.setBoat(boatQuickReservation.getBoat());
		return boatReservation;
	}
}
