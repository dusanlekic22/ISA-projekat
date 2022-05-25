package isaproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.FishingImageDTO;
import isaproject.service.FishingImageService;

@RestController
@RequestMapping(value = "/fishingImage", produces = MediaType.APPLICATION_JSON_VALUE)
public class FishingImageController {
	
	private FishingImageService fishingImageService;
	
	@Autowired
	public FishingImageController(FishingImageService fishingImageService) {
		super();
		this.fishingImageService = fishingImageService;
	}

	@PostMapping
	public ResponseEntity<FishingImageDTO> save(@RequestBody FishingImageDTO fishingImageDTO) {
		fishingImageService.save(fishingImageDTO);
		return new ResponseEntity<>(fishingImageDTO,HttpStatus.CREATED);
	}
	
}
