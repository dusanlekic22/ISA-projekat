package isaproject.controller.boat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.boat.BoatImageDTO;
import isaproject.service.boat.BoatImageService;

@RestController
@RequestMapping(value = "/boatImage", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class BoatImageController {
	
	@Autowired
	private BoatImageService boatImageService;

	@PostMapping
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatImageDTO> save(@RequestBody BoatImageDTO boatImageDTO) {
		return new ResponseEntity<>(boatImageService.save(boatImageDTO),HttpStatus.CREATED);
	}
	
}
