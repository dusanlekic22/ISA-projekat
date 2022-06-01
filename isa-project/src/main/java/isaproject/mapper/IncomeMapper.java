package isaproject.mapper;

import isaproject.dto.IncomeDTO;
import isaproject.model.Income;

public class IncomeMapper {
	public static Income IncomeDTOToIncome(IncomeDTO incomeDTO) {
		Income income = new Income();
		income.setDailySum(incomeDTO.getDailySum());
		income.setMonthlySum(incomeDTO.getMonthlySum());
		income.setYearlySum(incomeDTO.getYearlySum());
		return income;
	}
	
	public static IncomeDTO IncomeToIncomeDTO(Income income) {
		IncomeDTO incomeDTO = new IncomeDTO();
		incomeDTO.setYearlySum(income.getYearlySum());
		incomeDTO.setDailySum(income.getDailySum());
		incomeDTO.setMonthlySum(income.getMonthlySum());
		return incomeDTO;
	}
}
