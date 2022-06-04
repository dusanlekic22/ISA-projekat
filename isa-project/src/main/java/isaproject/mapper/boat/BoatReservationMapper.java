package isaproject.mapper.boat;

import java.util.function.Function;

import org.springframework.data.domain.Page;

import isaproject.dto.boat.BoatReservationDTO;
import isaproject.model.DateTimeSpan;
import isaproject.model.boat.BoatReservation;

public class BoatReservationMapper {
	public static BoatReservation BoatReservationDTOToBoatReservation(BoatReservationDTO boatReservationDTO) {
		BoatReservation boatReservation = new BoatReservation();
		boatReservation.setId(boatReservationDTO.getId());
		boatReservation.setDuration(new DateTimeSpan(boatReservationDTO.getDuration()));
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
		boatReservationDTO.setDuration(new DateTimeSpan(boatReservation.getDuration()));
		boatReservationDTO.setGuestCapacity(boatReservation.getGuestCapacity());
		boatReservationDTO.setPrice(boatReservation.getPrice());
		boatReservationDTO.setAdditionalService(boatReservation.getAdditionalService());
		boatReservationDTO.setBoat(boatReservation.getBoat());
		boatReservationDTO.setCustomer(boatReservation.getCustomer());
		boatReservationDTO.setConfirmed(boatReservation.isConfirmed());
		return boatReservationDTO;
	}

		public static Page<BoatReservationDTO> pageBoatReservationToPageBoatReservationDTO(
			Page<BoatReservation> boatReservationPages) {
			Page<BoatReservationDTO> boatReservationDTOPages = boatReservationPages.map(new Function<BoatReservation, BoatReservationDTO>() {
				@Override
				public BoatReservationDTO apply(BoatReservation entity) {
					BoatReservationDTO dto = BoatReservationMapper.BoatReservationToBoatReservationDTO(entity);
					return dto;
				}
			});
			return boatReservationDTOPages;
	}
		
}
