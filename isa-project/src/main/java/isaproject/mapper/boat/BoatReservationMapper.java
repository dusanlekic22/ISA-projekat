package isaproject.mapper.boat;

import isaproject.dto.boat.BoatReservationDTO;
import isaproject.model.boat.BoatReservation;

public class BoatReservationMapper {
	public static BoatReservation BoatReservationDTOToBoatReservation(BoatReservationDTO boatReservationDTO) {
		BoatReservation boatReservation = new BoatReservation();
		boatReservation.setId(boatReservationDTO.getId());
		boatReservation.setDuration(boatReservationDTO.getDuration());
		boatReservation.setGuestCapacity(boatReservationDTO.getGuestCapacity());
		boatReservation.setPrice(boatReservationDTO.getPrice());
		boatReservation.setAdditionalService(boatReservationDTO.getAdditionalService());
		boatReservation.setBoat(boatReservationDTO.getBoat());
		boatReservation.setCustomer(boatReservationDTO.getCustomer());
		boatReservation.setConfirmed(boatReservationDTO.isConfirmed());
		return boatReservation;
	}
	
	public static BoatReservationDTO BoatReservationToBoatReservationDTO(BoatReservation boatReservation) {
		BoatReservationDTO boatReservationDTO = new BoatReservationDTO();
		boatReservationDTO.setId(boatReservation.getId());
		boatReservationDTO.setDuration(boatReservation.getDuration());
		boatReservationDTO.setGuestCapacity(boatReservation.getGuestCapacity());
		boatReservationDTO.setPrice(boatReservation.getPrice());
		boatReservationDTO.setAdditionalService(boatReservation.getAdditionalService());
		boatReservationDTO.setBoat(boatReservation.getBoat());
		boatReservationDTO.setCustomer(boatReservation.getCustomer());
		boatReservationDTO.setConfirmed(boatReservation.isConfirmed());
		return boatReservationDTO;
	}
}
