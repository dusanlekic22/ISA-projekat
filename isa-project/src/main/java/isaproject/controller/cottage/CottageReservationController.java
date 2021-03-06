package isaproject.controller.cottage;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.security.Principal;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.CustomerDTO;
import isaproject.dto.ReviewDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.cottage.CottageReservationDTO;
import isaproject.service.CustomerService;
import isaproject.service.cottage.CottageOwnerService;
import isaproject.service.cottage.CottageReservationService;
import isaproject.service.cottage.CottageService;
import isaproject.util.ProjectUtil;

@RestController
@RequestMapping(value = "/cottageReservation", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class CottageReservationController {

	@Autowired
	CottageReservationService cottageReservationService;

	@Autowired
	CustomerService customerService;

	@Autowired
	CottageService cottageService;

	@Autowired
	CottageOwnerService CottageOwnerService;

	@GetMapping
	@ResponseBody
	public ResponseEntity<Set<CottageReservationDTO>> getAll() {
		return new ResponseEntity<>(cottageReservationService.findAll(), HttpStatus.OK);
	}
	
	

	@GetMapping("/customerHasReservationNow/cottage/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<Set<CustomerDTO>> findCustomersHasCurrentReservation(@PathVariable("id") Long id) {
		return new ResponseEntity<>(cottageReservationService.findCustomersHasCurrentReservation(id), HttpStatus.OK);
	}

	@GetMapping("/passed")
	@ResponseBody
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<Set<CottageReservationDTO>> getAllPassed() {
		return new ResponseEntity<>(cottageReservationService.findAllPast(), HttpStatus.OK);
	}


	
	@PostMapping("/customer/{id}")
	@PreAuthorize("hasRole('CUSTOMER')")
	@ResponseBody
	public Page<CottageReservationDTO> getAllPastCustomerReservations(@PathVariable("id") Long id,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size,
			@RequestBody SortTypeDTO sortTypeDTO, Principal user) {
		CustomerDTO customer = this.customerService.getCustomer(id);
		if (!user.getName().equals(customer.getUsername())) {
			throw new InvalidParameterException();
		}
		Pageable paging = PageRequest.of(page, size);
		return cottageReservationService.findAllPagination(id, sortTypeDTO, paging);
	}

	@PostMapping("/incoming/customer/{id}")
	@PreAuthorize("hasRole('CUSTOMER')")
	@ResponseBody
	public Page<CottageReservationDTO> getAllIncomingCustomerReservations(@PathVariable("id") Long id,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size,
			@RequestBody SortTypeDTO sortTypeDTO, Principal user) {
		CustomerDTO customer = this.customerService.getCustomer(id);
		if (!user.getName().equals(customer.getUsername())) {
			throw new InvalidParameterException();
		}
		Pageable paging = PageRequest.of(page, size);
		return cottageReservationService.findAllIncomingPagination(id, sortTypeDTO, paging);
	}

	@PostMapping("/{id}/cancel")
	@PreAuthorize("hasRole('CUSTOMER')")
	@ResponseBody
	public ResponseEntity<CottageReservationDTO> cancelCottageReservation(
			@RequestBody CottageReservationDTO cottageReservationDTO, Principal user) {

		CustomerDTO customer = this.customerService.getCustomer(cottageReservationDTO.getCustomer().getId());
		if (!user.getName().equals(customer.getUsername())) {
			throw new InvalidParameterException();
		}
		return new ResponseEntity<>(cottageReservationService.cancelCottageReservation(cottageReservationDTO),
				HttpStatus.OK);
	}

	@GetMapping("/active")
	@ResponseBody
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<Set<CottageReservationDTO>> getAllActive() {
		return new ResponseEntity<>(cottageReservationService.findAllActive(), HttpStatus.OK);
	}

	@GetMapping("/active/{cottageId}")
	@ResponseBody
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<Set<CottageReservationDTO>> getAllActiveByCottageId(@PathVariable("cottageId") Long id) {
		return new ResponseEntity<>(cottageReservationService.findAllActiveByCottageId(id), HttpStatus.OK);
	}

	@GetMapping("/passed/{cottageId}")
	@ResponseBody
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<Set<CottageReservationDTO>> getAllPassedByCottageId(@PathVariable("cottageId") Long id) {
		return new ResponseEntity<>(cottageReservationService.findAllPastByCottageId(id), HttpStatus.OK);
	}

	@PostMapping("/owner")
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageReservationDTO> reserveOwner(@RequestBody CottageReservationDTO cottageReservationDTO,
			HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		CottageReservationDTO cottageReservationReturnDTO = cottageReservationService
				.reserveCottageOwner(cottageReservationDTO, ProjectUtil.getSiteURL(request));
		if (cottageReservationReturnDTO == null)
			return new ResponseEntity<>(cottageReservationReturnDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(cottageReservationReturnDTO, HttpStatus.CREATED);
	}

	@PostMapping("/customer")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<CottageReservationDTO> reserveCustomer(
			@RequestBody CottageReservationDTO cottageReservationDTO) {
		CottageReservationDTO cottageReservationReturnDTO = cottageReservationService
				.reserveCustomer(cottageReservationDTO);
		if (cottageReservationReturnDTO == null)
			return new ResponseEntity<>(cottageReservationReturnDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(cottageReservationReturnDTO, HttpStatus.CREATED);
	}

	@GetMapping("/confirm/{id}")
	// @PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageReservationDTO> confirmReservation(@PathVariable("id") Long id) {
		CottageReservationDTO cottageReservationDTO = cottageReservationService.confirmReservation(id);
		if (cottageReservationDTO == null)
			return new ResponseEntity<>(cottageReservationDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(cottageReservationDTO, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageReservationDTO> deleteById(@PathVariable("id") Long id) {
		CottageReservationDTO cottageReservationDTO = cottageReservationService.deleteById(id);
		return new ResponseEntity<>(cottageReservationDTO, HttpStatus.OK);
	}

	@GetMapping("/active/owner/{cottageId}")
	public ResponseEntity<Set<CottageReservationDTO>> getAllActiveByCottageOwnerIdId(
			@PathVariable("cottageId") Long id) {
		return new ResponseEntity<>(cottageReservationService.findAllActiveByCottageOwnerId(id), HttpStatus.OK);
	}

	@GetMapping("/passed/owner/{cottageId}")
	public ResponseEntity<Set<CottageReservationDTO>> getAllPassedByCottageOwnerIdId(
			@PathVariable("cottageId") Long id) {
		return new ResponseEntity<>(cottageReservationService.findAllPastByCottageOwnerId(id), HttpStatus.OK);
	}

	@PostMapping("/customer/{id}/review")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<CottageReservationDTO> reserveOwner(@RequestBody ReviewDTO reviewDTO,
			HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {

		return null;
//		if (cottageReservationReturnDTO == null)
//			return new ResponseEntity<>(cottageReservationReturnDTO, HttpStatus.BAD_REQUEST);
//		return new ResponseEntity<>(cottageReservationReturnDTO, HttpStatus.CREATED);
	}

}
