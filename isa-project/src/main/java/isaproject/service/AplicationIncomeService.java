package isaproject.service;

import isaproject.dto.IncomeDTO;
import isaproject.model.DateTimeSpan;

public interface AplicationIncomeService {

	IncomeDTO getAllIncomeYearly(DateTimeSpan duration);

	IncomeDTO getAllIncomeMonthly(DateTimeSpan duration);

	IncomeDTO getAllIncomeDaily(DateTimeSpan duration);

}
