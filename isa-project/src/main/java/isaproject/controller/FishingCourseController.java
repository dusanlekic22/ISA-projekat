package isaproject.controller;

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

import isaproject.dto.FishingCourseAvailabilityDTO;
import isaproject.dto.FishingCourseDTO;
import isaproject.dto.GradeDTO;
import isaproject.dto.IncomeDTO;
import isaproject.dto.ReservationCountDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.boat.BoatDTO;
import isaproject.dto.cottage.CottageDTO;
import isaproject.model.DateTimeSpan;
import isaproject.service.FishingCourseService;

@RestController
@RequestMapping(value = "/fishingCourse", produces = MediaType.APPLICATION_JSON_VALUE)
public class FishingCourseController {

	private FishingCourseService fishingCourseService;

	@Autowired
	public FishingCourseController(FishingCourseService fishingCourseService) {
		super();
		this.fishingCourseService = fishingCourseService;
	}

	@GetMapping
	public Set<FishingCourseDTO> getAll() {
		return fishingCourseService.findAll();
	}

	@PostMapping("/pagination")
	@ResponseBody
	public Page<FishingCourseDTO> getAllPagination(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size, @RequestBody List<SortTypeDTO> sortTypeDTOList) {
		Pageable paging = PageRequest.of(page, size);
		return fishingCourseService.findAllPagination(sortTypeDTOList, paging);
	}

	@GetMapping("/{id}")
//	@PreAuthorize("hasAnyRole('FISHING_TRAINER', 'ADMIN','CUSTOMER')")
	public FishingCourseDTO get(@PathVariable Long id) {
		return fishingCourseService.findById(id);
	}

	@PostMapping
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<FishingCourseDTO> save(@RequestBody FishingCourseDTO fishingCourseDTO) {
		FishingCourseDTO courseDTO = fishingCourseService.save(fishingCourseDTO);
		return new ResponseEntity<>(courseDTO, HttpStatus.CREATED);
	}

	@PutMapping
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<FishingCourseDTO> update(@RequestBody FishingCourseDTO fishingCourseDTO) {
		FishingCourseDTO courseDTO = fishingCourseService.update(fishingCourseDTO);
		return new ResponseEntity<>(courseDTO, HttpStatus.OK);
	}

	@PutMapping("/info")
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<FishingCourseDTO> updateInfo(@RequestBody FishingCourseDTO fishingCourseDTO) {
		FishingCourseDTO courseDTO = fishingCourseService.updateInfo(fishingCourseDTO);
		return new ResponseEntity<>(courseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('FISHING_TRAINER', 'ROLE_ADMIN')")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		fishingCourseService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/search")
	public Set<FishingCourseDTO> search(@RequestParam("name") String fishingCourseName) {
		return fishingCourseService.findByFishingCourseName(fishingCourseName);
	}

	@GetMapping("/owner/{id}")
	public Set<FishingCourseDTO> getByFishingTrainerId(@PathVariable("id") Long id) {
		return fishingCourseService.findByFishingTrainerId(id);
	}

	@PostMapping("/searchFishing")
	@ResponseBody
	public Page<FishingCourseDTO> searchF(@RequestBody String name, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size) {
		Pageable paging = PageRequest.of(page, size);
		return fishingCourseService.findByName(name, paging);
	}

	@GetMapping("/{id}/yearlyCount")
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<ReservationCountDTO> getReservationCountYearly(@PathVariable("id") Long id) {
		return new ResponseEntity<>(fishingCourseService.getFishingCourseReservationCountYearly(id), HttpStatus.OK);
	}

	@GetMapping("/{id}/monthlyCount")
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<ReservationCountDTO> getReservationCountMonthly(@PathVariable("id") Long id) {
		return new ResponseEntity<>(fishingCourseService.getFishingCourseReservationCountMonthly(id), HttpStatus.OK);
	}

	@GetMapping("/{id}/weeklyCount")
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<ReservationCountDTO> getReservationCountWeekly(@PathVariable("id") Long id) {
		return new ResponseEntity<>(fishingCourseService.getFishingCourseReservationCountWeekly(id), HttpStatus.OK);
	}

	@PostMapping("/{id}/yearlyIncome")
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<IncomeDTO> getIncomeYearly(@PathVariable("id") Long id, @RequestBody DateTimeSpan duration) {
		return new ResponseEntity<>(fishingCourseService.getFishingCourseIncomeYearly(duration, id), HttpStatus.OK);
	}

	@PostMapping("/{id}/monthlyIncome")
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<IncomeDTO> getIncomeMonthly(@PathVariable("id") Long id, @RequestBody DateTimeSpan duration) {
		return new ResponseEntity<>(fishingCourseService.getFishingCourseIncomeMonthly(duration, id), HttpStatus.OK);
	}

	@PostMapping("/{id}/dailyIncome")
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<IncomeDTO> getIncomeDaily(@PathVariable("id") Long id, @RequestBody DateTimeSpan duration) {
		return new ResponseEntity<>(fishingCourseService.getFishingCourseIncomeDaily(duration, id), HttpStatus.OK);
	}

	@PostMapping("/{id}/grade")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<FishingCourseDTO> addGrade(@PathVariable("id") Long id, @RequestBody GradeDTO gradeDTO) {
		return new ResponseEntity<>(fishingCourseService.addGrade(gradeDTO, id), HttpStatus.OK);
	}

	@PostMapping("/availability")
	@ResponseBody
	public Page<FishingCourseDTO> search(@RequestBody FishingCourseAvailabilityDTO fishingCourseAvailability,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
		Pageable paging = PageRequest.of(page, size);
		return fishingCourseService.findByAvailability(fishingCourseAvailability, paging);
	}
	
	@GetMapping("/{id}/subscribe/{customerId}")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<FishingCourseDTO> subscribe(@PathVariable("id") Long id,@PathVariable("customerId") Long customerId) {
		return new ResponseEntity<>(fishingCourseService.subscribe(id,customerId), HttpStatus.OK);
	}
	
	@GetMapping("/{id}/unsubscribe/{customerId}")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<FishingCourseDTO> unsubscribe(@PathVariable("id") Long id,@PathVariable("customerId") Long customerId) {
		return new ResponseEntity<>(fishingCourseService.unsubscribe(id,customerId), HttpStatus.OK);
	}
	
	@GetMapping("/subscription/{customerId}")
	@PreAuthorize("hasRole('CUSTOMER')")
	public Page<FishingCourseDTO> findAllFishingSubscriptionByCustomer(@PathVariable("customerId") Long customerId,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
		Pageable paging = PageRequest.of(page, size);
		return fishingCourseService.findAllFishingSubscriptionByCustomer(customerId,paging);
	}

}
