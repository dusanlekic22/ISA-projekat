package isaproject.controller.boat;

import java.io.UnsupportedEncodingException;
import java.util.List;
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
import isaproject.dto.SortTypeDTO;
import isaproject.dto.boat.BoatReservationDTO;
import isaproject.dto.cottage.CottageReservationDTO;
import isaproject.service.boat.BoatReservationService;
import isaproject.util.ProjectUtil;

@RestController
@RequestMapping(value = "/boatReservation", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class BoatReservationController {

	@Autowired
	BoatReservationService boatReservationService;

	@GetMapping
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Set<BoatReservationDTO>> getAll() {
		return new ResponseEntity<>(boatReservationService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/customerHasReservationNow")
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Set<CustomerDTO>> findCustomersHasCurrentReservation() {
		return new ResponseEntity<>(boatReservationService.findCustomersHasCurrentReservation(), HttpStatus.OK);
	}

	@GetMapping("/passed")
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Set<BoatReservationDTO>> getAllPassed() {
		return new ResponseEntity<>(boatReservationService.findAllPast(), HttpStatus.OK);
	}


	@GetMapping("/active")
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Set<BoatReservationDTO>> getAllActive() {
		return new ResponseEntity<>(boatReservationService.findAllActive(), HttpStatus.OK);
	}

	@GetMapping("/active/{boatId}")
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Set<BoatReservationDTO>> getAllActiveByBoatId(@PathVariable("boatId") Long id) {
		return new ResponseEntity<>(boatReservationService.findAllActiveByBoatId(id), HttpStatus.OK);
	}

	@GetMapping("/passed/{boatId}")
	@ResponseBody
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Set<BoatReservationDTO>> getAllPassedByBoatId(@PathVariable("boatId") Long id) {
		return new ResponseEntity<>(boatReservationService.findAllPastByBoatId(id), HttpStatus.OK);
	}
	
	@GetMapping("/customer/{id}")
	@PreAuthorize("hasRole('CUSTOMER')")
	@ResponseBody
	public Page<BoatReservationDTO> getAllCustomerReservations(@PathVariable("id") Long id,@RequestParam(defaultValue = "0") int page,
					@RequestParam(defaultValue = "6") int size, @RequestBody List<SortTypeDTO> sortTypeDTOList) {
				Pageable paging = PageRequest.of(page, size);
				return boatReservationService.findAllPagination(id,sortTypeDTOList, paging);
			}

	@PostMapping("/owner")
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatReservationDTO> reserveOwner(@RequestBody BoatReservationDTO BoatReservationDTO,
			HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		BoatReservationDTO BoatReservationReturnDTO = boatReservationService
				.reserveBoatOwner(BoatReservationDTO, ProjectUtil.getSiteURL(request));
		if (BoatReservationReturnDTO == null)
			return new ResponseEntity<>(BoatReservationReturnDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(BoatReservationReturnDTO, HttpStatus.CREATED);
	}

	@PostMapping("/customer")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<BoatReservationDTO> reserveCustomer(
			@RequestBody BoatReservationDTO BoatReservationDTO) {
		BoatReservationDTO BoatReservationReturnDTO = boatReservationService
				.reserveCustomer(BoatReservationDTO);
		if (BoatReservationReturnDTO == null)
			return new ResponseEntity<>(BoatReservationReturnDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(BoatReservationReturnDTO, HttpStatus.CREATED);
	}

	@GetMapping("/confirm/{id}")
	public ResponseEntity<BoatReservationDTO> confirmReservation(@PathVariable("id") Long id) {
		BoatReservationDTO BoatReservationDTO = boatReservationService.confirmReservation(id);
		if (BoatReservationDTO == null)
			return new ResponseEntity<>(BoatReservationDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(BoatReservationDTO, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<BoatReservationDTO> deleteById(@PathVariable("id") Long id) {
		BoatReservationDTO BoatReservationDTO = boatReservationService.deleteById(id);
		return new ResponseEntity<>(BoatReservationDTO, HttpStatus.OK);
	}

	@GetMapping("/active/owner/{boatId}")
	public ResponseEntity<Set<BoatReservationDTO>> getAllActiveByBoatOwnerIdId(@PathVariable("boatId") Long id) {
		return new ResponseEntity<>(boatReservationService.findAllActiveByBoatOwnerId(id), HttpStatus.OK);
	}

	@GetMapping("/passed/owner/{boatId}")
	public ResponseEntity<Set<BoatReservationDTO>> getAllPassedByBoatOwnerIdId(@PathVariable("boatId") Long id) {
		return new ResponseEntity<>(boatReservationService.findAllPastByBoatOwnerId(id), HttpStatus.OK);
	}
}
