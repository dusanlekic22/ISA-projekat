package isaproject.mapper;

import isaproject.dto.ReservationCountDTO;
import isaproject.model.ReservationCount;

public class ReservationCountMapper {
	
	public static ReservationCount ReservationCountDTOToReservationCount(ReservationCountDTO reservationCountDTO) {
		ReservationCount reservationCount = new ReservationCount();
		reservationCount.setWeeklySum(reservationCountDTO.getWeeklySum());
		reservationCount.setMonthlySum(reservationCountDTO.getMonthlySum());
		reservationCount.setYearlySum(reservationCountDTO.getYearlySum());
		return reservationCount;
	}
	
	public static ReservationCountDTO ReservationCountToReservationCountDTO(ReservationCount reservationCount) {
		ReservationCountDTO reservationCountDTO = new ReservationCountDTO();
		reservationCountDTO.setYearlySum(reservationCount.getYearlySum());
		reservationCountDTO.setWeeklySum(reservationCount.getWeeklySum());
		reservationCountDTO.setMonthlySum(reservationCount.getMonthlySum());
		return reservationCountDTO;
	}

}
