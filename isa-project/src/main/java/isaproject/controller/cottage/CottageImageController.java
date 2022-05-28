package isaproject.controller.cottage;

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

import isaproject.dto.cottage.CottageImageDTO;
import isaproject.service.cottage.CottageImageService;

@RestController
@RequestMapping(value = "/cottageImage", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class CottageImageController {
	
	@Autowired
	private CottageImageService cottageImageService;

	@PostMapping
    @PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageImageDTO> save(@RequestBody CottageImageDTO cottageImageDTO) {
		cottageImageService.save(cottageImageDTO);
		return new ResponseEntity<>(cottageImageDTO,HttpStatus.CREATED);
	}
	
}
