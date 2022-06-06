package isaproject.controller.cottage;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.CottageAvailabilityDTO;
import isaproject.dto.GradeDTO;
import isaproject.dto.IncomeDTO;
import isaproject.dto.ReservationCountDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.cottage.CottageDTO;
import isaproject.model.DateTimeSpan;
import isaproject.service.cottage.CottageService;

@RestController
@RequestMapping(value = "/cottage", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class CottageController {

	@Autowired
	CottageService cottageService;

	@GetMapping
	@ResponseBody
	public Set<CottageDTO> getAll() {
		return cottageService.findAll();
	}

	@PostMapping("/pagination")
	@ResponseBody
	public Page<CottageDTO> getAllPagination(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size, @RequestBody List<SortTypeDTO> sortTypeDTOList) {
		Pageable paging = PageRequest.of(page, size);
		return cottageService.findAllPagination(sortTypeDTOList, paging);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('COTTAGE_OWNER','CUSTOMER')")
	public CottageDTO loadById(@PathVariable("id") Long id) {
		return cottageService.findById(id);
	}

	@PutMapping
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageDTO> update(@RequestBody CottageDTO cottageDTO) {
		return new ResponseEntity<>(cottageService.update(cottageDTO), HttpStatus.CREATED);
	}

	@PutMapping("/info")
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageDTO> updateInfo(@RequestBody CottageDTO cottageDTO) {
		CottageDTO cottageReturnDTO = cottageService.updateInfo(cottageDTO);
		if (cottageReturnDTO == null)
			return new ResponseEntity<>(cottageReturnDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(cottageReturnDTO, HttpStatus.OK);
	}

	@PutMapping("/availableTerms/{id}")
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageDTO> updateAvailableTerms(@PathVariable("id") Long id,
			@RequestBody DateTimeSpan dateTimeSpan) {
		CottageDTO cottageReturnDTO = cottageService.updateAvailableTerms(id, dateTimeSpan);
		if (cottageReturnDTO == null)
			return new ResponseEntity<>(cottageReturnDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(cottageReturnDTO, HttpStatus.OK);
	}

	@PutMapping("/unavailableTerms/{id}")
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageDTO> updateUnavailableTerms(@PathVariable("id") Long id,
			@RequestBody DateTimeSpan dateTimeSpan) {
		CottageDTO cottageReturnDTO = cottageService.updateUnavailableTerms(id, dateTimeSpan);
		if (cottageReturnDTO == null)
			return new ResponseEntity<>(cottageReturnDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(cottageReturnDTO, HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageDTO> save(@RequestBody CottageDTO cottageDTO) {
		return new ResponseEntity<>(cottageService.save(cottageDTO), HttpStatus.CREATED);
	}

	@GetMapping("/search")
	@ResponseBody
	public Set<CottageDTO> search(@RequestParam("name") String cottageName) {
		return cottageService.findByCottageName(cottageName);
	}

	@GetMapping("/owner/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public Set<CottageDTO> getByCottageOwnerId(@PathVariable("id") Long id) {
		return cottageService.findByCottageOwnerId(id);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public CottageDTO deleteById(@PathVariable("id") Long id) {
		return cottageService.deleteById(id);
	}

	@PostMapping("/availability")
	@ResponseBody
	public Page<CottageDTO> search(@RequestBody CottageAvailabilityDTO cottageAvailability,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
		Pageable paging = PageRequest.of(page, size);
		return cottageService.findByAvailability(cottageAvailability, paging);
	}

	@PostMapping("/searchCottage")
	@ResponseBody
	public Page<CottageDTO> searchCottages(@RequestBody String name, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size) {
		Pageable paging = PageRequest.of(page, size);
		return cottageService.findByName(name, paging);
	}

	@GetMapping("/{id}/yearlyCount")
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<ReservationCountDTO> getReservationCountYearly(@PathVariable("id") Long id) {
		return new ResponseEntity<>(cottageService.getCottageReservationCountYearly(id), HttpStatus.OK);
	}

	@GetMapping("/{id}/monthlyCount")
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<ReservationCountDTO> getReservationCountMonthly(@PathVariable("id") Long id) {
		return new ResponseEntity<>(cottageService.getCottageReservationCountMonthly(id), HttpStatus.OK);
	}

	@GetMapping("/{id}/weeklyCount")
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<ReservationCountDTO> getReservationCountWeekly(@PathVariable("id") Long id) {
		return new ResponseEntity<>(cottageService.getCottageReservationCountWeekly(id), HttpStatus.OK);
	}

	@PostMapping("/{id}/yearlyIncome")
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<IncomeDTO> getIncomeYearly(@PathVariable("id") Long id, @RequestBody DateTimeSpan duration) {
		return new ResponseEntity<>(cottageService.getCottageIncomeYearly(duration, id), HttpStatus.OK);
	}

	@PostMapping("/{id}/monthlyIncome")
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<IncomeDTO> getIncomeMonthly(@PathVariable("id") Long id, @RequestBody DateTimeSpan duration) {
		return new ResponseEntity<>(cottageService.getCottageIncomeMonthly(duration, id), HttpStatus.OK);
	}

	@PostMapping("/{id}/dailyIncome")
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<IncomeDTO> getIncomeWeeklyDaily(@PathVariable("id") Long id,
			@RequestBody DateTimeSpan duration) {
		return new ResponseEntity<>(cottageService.getCottageIncomeDaily(duration, id), HttpStatus.OK);
	}

	@PostMapping("/{id}/grade")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<CottageDTO> addGrade(@PathVariable("id") Long id, @RequestBody GradeDTO gradeDTO) {
		return new ResponseEntity<>(cottageService.addGrade(gradeDTO, id), HttpStatus.OK);
	}

}
