package isaproject.controller.boat;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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

import isaproject.dto.DateSpanDTO;
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
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('BOAT_OWNER')")	
	public BoatDTO loadById(@PathVariable("id") Long id) {
		return boatService.findById(id);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatDTO> update(@RequestBody BoatDTO boatDTO) {
		boatService.update(boatDTO);
		return new ResponseEntity<>(boatDTO,HttpStatus.CREATED);
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
	
	@PostMapping
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatDTO> save(@RequestBody BoatDTO boatDTO) {
		boatService.save(boatDTO);
		return new ResponseEntity<>(boatDTO,HttpStatus.CREATED);
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
	
	@PostMapping("/availability")
	@ResponseBody
	public Set<BoatDTO> search(@RequestBody DateSpanDTO reservationDate) {
		return boatService.findByReservationDate(reservationDate);
	}
	
}
