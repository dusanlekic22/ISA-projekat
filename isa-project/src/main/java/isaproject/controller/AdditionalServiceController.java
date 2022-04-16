package isaproject.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.AdditionalServiceDTO;
import isaproject.dto.CottageDTO;
import isaproject.service.AdditionalServiceService;

@RestController
@RequestMapping(value = "/additionalService", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class AdditionalServiceController {
	
	@Autowired
	private AdditionalServiceService additionalServiceService;

	@PostMapping
	public ResponseEntity<AdditionalServiceDTO> save(@RequestBody AdditionalServiceDTO additionalServiceDTO) {
		additionalServiceService.save(additionalServiceDTO);
		return new ResponseEntity<>(additionalServiceDTO,HttpStatus.CREATED);
	}
	
	@GetMapping("/free")
	@ResponseBody
	public Set<AdditionalServiceDTO> getAll(){
		return additionalServiceService.findFree();
	}

}
