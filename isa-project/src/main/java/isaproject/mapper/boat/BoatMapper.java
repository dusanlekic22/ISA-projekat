package isaproject.mapper.boat;

import java.util.function.Function;

import org.springframework.data.domain.Page;

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
		boat.setUnavailableReservationDateSpan(boatDTO.getUnavailableReservationDateSpan());
		boat.setBoatOwner(boatDTO.getBoatOwner());
		boat.setSubscribers(boatDTO.getSubscribers());
		boat.setGrades(boatDTO.getGrades());
		boat.setAverageGrade();
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
		boatDTO.setUnavailableReservationDateSpan(boat.getUnavailableReservationDateSpan());
		boatDTO.setBoatOwner(boat.getBoatOwner());
		boatDTO.setSubscribers(boat.getSubscribers());
		boatDTO.setGrades(boat.getGrades());
		boatDTO.setAverageGrade(boat.getAverageGrade());
		return boatDTO;
	}
	
	public static BoatDTO BoatToBoatDTOWithPrice(Boat boat, int hours) {
		boat.setPricePerHour(boat.getPricePerHour()*hours);
		BoatDTO boatDTO = BoatToBoatDTO(boat);
		return boatDTO;
	}
	
	public static Page<BoatDTO> pageBoatToPageBoatDTO(Page<Boat> boatPages) {
		Page<BoatDTO> boatDTOPages = boatPages.map(new Function<Boat, BoatDTO>() {
			@Override
			public BoatDTO apply(Boat entity) {
				BoatDTO dto = BoatMapper.BoatToBoatDTO(entity);
				return dto;
			}
		});
		return boatDTOPages;
	}
}
