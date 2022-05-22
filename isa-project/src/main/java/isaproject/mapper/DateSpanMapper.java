package isaproject.mapper;

import isaproject.dto.DateSpanDTO;
import isaproject.model.Customer;
import isaproject.model.DateSpan;

public class DateSpanMapper {

	
	public static DateSpan dateSpanDTOtoDateSpan(DateSpanDTO dateSpanDTO) {
		DateSpan dateSpan = new DateSpan(dateSpanDTO.getStartDate(),dateSpanDTO.getEndDate());
		return dateSpan;
}
	}
