package isaproject.mapper;

import isaproject.dto.ReservationCountDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.model.ReservationCount;
import isaproject.model.SortType;

public class SortTypeMapper {

	
	public static SortType SortTypeDTOToSortType(SortTypeDTO sortTypeDTO) {
		SortType sortType = new SortType();
		sortType.setField(sortTypeDTO.getField());
		sortType.setDirection(sortTypeDTO.getDirection());
		return sortType;
	}
	
	public static SortTypeDTO SortTypeToSortTypeDTO(SortType sortType) {
		SortTypeDTO sortTypeDTO = new SortTypeDTO();
		sortTypeDTO.setField(sortType.getField());
		sortTypeDTO.setDirection(sortType.getDirection());
		return sortTypeDTO;
	}
}
