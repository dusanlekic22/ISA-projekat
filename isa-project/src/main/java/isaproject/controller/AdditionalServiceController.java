package isaproject.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.AdditionalServiceDTO;
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
	public Set<AdditionalServiceDTO> getFree(){
		return additionalServiceService.findFree();
	}
	
	@GetMapping("/cottage/{id}")
	@ResponseBody
	public Set<AdditionalServiceDTO> getByCottageId(@PathVariable("id") Long id){
		return additionalServiceService.findByCottageId(id);
	}
	
	@GetMapping("/boat/{id}")
	@ResponseBody
	public Set<AdditionalServiceDTO> getByBoatId(@PathVariable("id") Long id){
		return additionalServiceService.findByBoatId(id);
	}

}
