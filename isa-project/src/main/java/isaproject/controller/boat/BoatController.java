package isaproject.controller.boat;

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

import isaproject.dto.BoatAvailabilityDTO;
import isaproject.dto.GradeDTO;
import isaproject.dto.IncomeDTO;
import isaproject.dto.ReservationCountDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.boat.BoatDTO;
import isaproject.model.DateTimeSpan;
import isaproject.service.boat.BoatService;

@RestController
@RequestMapping(value = "/boat", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class BoatController {
	
	@Autowired
	BoatService boatService;
	
	@GetMapping
	@ResponseBody
	public Set<BoatDTO> getAll(){
		return boatService.findAll();
	}
	
	@PostMapping("/pagination")
	@ResponseBody
	public Page<BoatDTO> getAllPagination(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size,
			@RequestBody List<SortTypeDTO> sortTypeDTOList) {
		Pageable paging = PageRequest.of(page, size);
		return boatService.findAllPagination(sortTypeDTOList,paging);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('BOAT_OWNER','CUSTOMER')")	
	public BoatDTO loadById(@PathVariable("id") Long id) {
		return boatService.findById(id);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatDTO> update(@RequestBody BoatDTO boatDTO) {
		return new ResponseEntity<>(boatService.update(boatDTO),HttpStatus.CREATED);
	}
	
	@PutMapping("/info")
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatDTO> updateInfo(@RequestBody BoatDTO boatDTO) {
		BoatDTO boatReturnDTO =  boatService.updateInfo(boatDTO);
		if(boatReturnDTO==null)
			return new ResponseEntity<>(boatReturnDTO,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(boatReturnDTO,HttpStatus.OK);
	}
	
	@PutMapping("/availableTerms/{id}")
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatDTO> updateAvailableTerms(@PathVariable("id")Long id, @RequestBody DateTimeSpan dateTimeSpan) {
		BoatDTO boatReturnDTO =  boatService.updateAvailableTerms(id, dateTimeSpan);
		if(boatReturnDTO==null)
			return new ResponseEntity<>(boatReturnDTO,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(boatReturnDTO,HttpStatus.OK);
	}
	
	@PutMapping("/unavailableTerms/{id}")
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatDTO> updateUnavailableTerms(@PathVariable("id")Long id, @RequestBody DateTimeSpan dateTimeSpan) {
		BoatDTO boatReturnDTO =  boatService.updateUnavailableTerms(id, dateTimeSpan);
		if(boatReturnDTO==null)
			return new ResponseEntity<>(boatReturnDTO,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(boatReturnDTO,HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatDTO> save(@RequestBody BoatDTO boatDTO) {
		return new ResponseEntity<>(boatService.save(boatDTO),HttpStatus.CREATED);
	}

	@GetMapping("/search")
	@ResponseBody
	public Set<BoatDTO> search(@RequestParam("name") String boatName) {
		return boatService.findByBoatName(boatName);
	}
	
	@GetMapping("/owner/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public Set<BoatDTO> getByBoatOwnerId(@PathVariable("id") Long id) {
		return boatService.findByBoatOwnerId(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public BoatDTO deleteById(@PathVariable("id") Long id) {
		return boatService.deleteById(id);
	}
	
//	@PostMapping("/availability")
//	@ResponseBody
//	public Set<BoatDTO> search(@RequestBody DateSpanDTO reservationDate) {
//		return boatService.findByReservationDate(reservationDate);
//	}
	
	@PostMapping("/availability")
	@ResponseBody
	public Page<BoatDTO> search(@RequestBody BoatAvailabilityDTO boatAvailability,	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size) {
		Pageable paging = PageRequest.of(page, size);
		return boatService.findByAvailability(boatAvailability,paging);
	}
	
	@GetMapping("/{id}/yearlyCount")
	@PreAuthorize("hasRole('BOAT_OWNER')")	
	public ResponseEntity<ReservationCountDTO> getReservationCountYearly(@PathVariable("id") Long id) {
		return new ResponseEntity<>(boatService.getBoatReservationCountYearly(id),HttpStatus.OK);
	}
	
	@GetMapping("/{id}/monthlyCount")
	@PreAuthorize("hasRole('BOAT_OWNER')")	
	public ResponseEntity<ReservationCountDTO> getReservationCountMonthly(@PathVariable("id") Long id) {
		return new ResponseEntity<>(boatService.getBoatReservationCountMonthly(id),HttpStatus.OK);
	}
	
	@GetMapping("/{id}/weeklyCount")
	@PreAuthorize("hasRole('BOAT_OWNER')")	
	public ResponseEntity<ReservationCountDTO> getReservationCountWeekly(@PathVariable("id") Long id) {
		return new ResponseEntity<>(boatService.getBoatReservationCountWeekly(id),HttpStatus.OK);
	}
	
	@PostMapping("/{id}/yearlyIncome")
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<IncomeDTO> getIncomeYearly(@PathVariable("id") Long id, @RequestBody DateTimeSpan duration) {
		return new ResponseEntity<>(boatService.getBoatIncomeYearly(duration, id), HttpStatus.OK);
	}

	@PostMapping("/{id}/monthlyIncome")
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<IncomeDTO> getIncomeMonthly(@PathVariable("id") Long id, @RequestBody DateTimeSpan duration) {
		return new ResponseEntity<>(boatService.getBoatIncomeMonthly(duration, id), HttpStatus.OK);
	}

	@PostMapping("/{id}/dailyIncome")
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<IncomeDTO> getIncomeWeeklyDaily(@PathVariable("id") Long id,
			@RequestBody DateTimeSpan duration) {
		return new ResponseEntity<>(boatService.getBoatIncomeDaily(duration, id), HttpStatus.OK);
	}
	
	@PostMapping("/{id}/grade")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<BoatDTO> addGrade(@PathVariable("id") Long id,
			@RequestBody GradeDTO gradeDTO) {
		return new ResponseEntity<>(boatService.addGrade(gradeDTO, id), HttpStatus.OK);
	}
	
}
