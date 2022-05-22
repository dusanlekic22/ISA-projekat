package isaproject.mapper;

import isaproject.dto.DateSpanDTO;
import isaproject.model.DateTimeSpan;

public class DateSpanMapper {

	
	public static DateTimeSpan dateSpanDTOtoDateSpan(DateSpanDTO dateSpanDTO) {
		DateTimeSpan dateSpan = new DateTimeSpan(dateSpanDTO.getStartDate(),dateSpanDTO.getEndDate());
		return dateSpan;
}
	}
