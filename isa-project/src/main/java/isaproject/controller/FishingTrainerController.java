package isaproject.controller;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.FishingTrainerAvailabilityDTO;
import isaproject.dto.FishingTrainerDTO;
import isaproject.dto.SortTypeDTO;
import isaproject.dto.UserDTO;
import isaproject.exception.ResourceConflictException;
import isaproject.mapper.UserMapper;
import isaproject.model.DateTimeSpan;
import isaproject.model.FishingTrainer;
import isaproject.model.User;
import isaproject.service.FishingTrainerService;
import isaproject.service.UserService;

@RestController
@RequestMapping(value = "/fishingTrainer", produces = MediaType.APPLICATION_JSON_VALUE)
public class FishingTrainerController {

	private FishingTrainerService fishingTrainerService;
	private UserService userService;

	public FishingTrainerController(FishingTrainerService fishingTrainerService, UserService userService) {
		super();
		this.fishingTrainerService = fishingTrainerService;
		this.userService = userService;
	}
	
	
	@PostMapping("/all")
	@ResponseBody
	public Set<FishingTrainerDTO> getAll() {
		return fishingTrainerService.getAll();
	}
	
	@PostMapping("/pagination")
	@ResponseBody
	public Page<FishingTrainerDTO> getAllPagination(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "6") int size,
			@RequestBody List<SortTypeDTO> sortTypeDTOList) {
		Pageable paging = PageRequest.of(page, size);
		return fishingTrainerService.findAllPagination(sortTypeDTOList,paging);
	}
	

	@PostMapping("/signup")
	public ResponseEntity<UserDTO> addUser(@RequestBody BusinessOwnerDTO businessOwnerDTO) {
		User existUser = this.userService.findByUsername(businessOwnerDTO.getUsername());

		if (existUser != null) {
			throw new ResourceConflictException(existUser.getId(), "Username already exists");
		}

		FishingTrainer user = this.fishingTrainerService.registerFishingTrainer(businessOwnerDTO);

		return new ResponseEntity<>(UserMapper.UserToDTO(user), HttpStatus.CREATED);
	}

	@GetMapping
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<FishingTrainerDTO> get(Principal user) {
		FishingTrainerDTO fishingTrainerDTO = fishingTrainerService.findByUsername(user.getName());
		return new ResponseEntity<>(fishingTrainerDTO, HttpStatus.OK);
	}


	@PutMapping("/availableTerms/{id}")
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<FishingTrainerDTO> updateAvailableTerms(@PathVariable("id") Long id,
			@RequestBody DateTimeSpan dateTimeSpan) {
		FishingTrainerDTO fishingTrainerDTO = fishingTrainerService.updateAvailableTerms(id, dateTimeSpan);
		if (fishingTrainerDTO == null)
			return new ResponseEntity<>(fishingTrainerDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(fishingTrainerDTO, HttpStatus.OK);
	}

	@PutMapping("/unavailableTerms/{id}")
	@PreAuthorize("hasRole('FISHING_TRAINER')")
	public ResponseEntity<FishingTrainerDTO> updateUnavailableTerms(@PathVariable("id") Long id,
			@RequestBody DateTimeSpan dateTimeSpan) {
		FishingTrainerDTO fishingTrainerDTO = fishingTrainerService.updateUnavailableTerms(id, dateTimeSpan);
		if (fishingTrainerDTO == null)
			return new ResponseEntity<>(fishingTrainerDTO, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(fishingTrainerDTO, HttpStatus.OK);
	}
	
	@PostMapping("/availability")
	@ResponseBody
	public Page<FishingTrainerDTO> search(@RequestBody FishingTrainerAvailabilityDTO fishingTrainerAvailability,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
		Pageable paging = PageRequest.of(page, size);
		return fishingTrainerService.findByAvailability(fishingTrainerAvailability, paging);
	}

}
