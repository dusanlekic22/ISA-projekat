package isaproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.IncomeDTO;
import isaproject.model.DateTimeSpan;
import isaproject.service.AplicationIncomeService;

@RestController
@RequestMapping(value = "/aplicationIncome", produces = MediaType.APPLICATION_JSON_VALUE)
public class AplicationIncomeController {

	AplicationIncomeService aplicationIncomeService;

	@Autowired
	public AplicationIncomeController(AplicationIncomeService aplicationIncomeService) {
		super();
		this.aplicationIncomeService = aplicationIncomeService;
	}

	@PostMapping("/yearlyIncome")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<IncomeDTO> getIncomeYearly(@RequestBody DateTimeSpan duration) {
		return new ResponseEntity<>(aplicationIncomeService.getAllIncomeYearly(duration), HttpStatus.OK);
	}

	@PostMapping("/monthlyIncome")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<IncomeDTO> getIncomeMonthly(@RequestBody DateTimeSpan duration) {
		return new ResponseEntity<>(aplicationIncomeService.getAllIncomeMonthly(duration), HttpStatus.OK);
	}

	@PostMapping("/dailyIncome")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<IncomeDTO> getIncomeWeeklyDaily(@PathVariable("id") Long id,
			@RequestBody DateTimeSpan duration) {
		return new ResponseEntity<>(aplicationIncomeService.getAllIncomeDaily(duration), HttpStatus.OK);
	}
	
}
