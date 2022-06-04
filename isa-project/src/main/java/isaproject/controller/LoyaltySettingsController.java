package isaproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isaproject.dto.LoyaltySettingsDTO;
import isaproject.service.LoyaltySettingsService;

@RestController
@RequestMapping(value = "/loyaltySettings", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoyaltySettingsController {
	
	LoyaltySettingsService loyaltySettingsService;

	@Autowired
	public LoyaltySettingsController(LoyaltySettingsService loyaltySettingsService) {
		super();
		this.loyaltySettingsService = loyaltySettingsService;
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER', 'FISHING_TRAINER', 'COTTAGE_OWNER', 'BOAT_OWNER')")
	public ResponseEntity<LoyaltySettingsDTO> get() {
		LoyaltySettingsDTO dto = loyaltySettingsService.getLoyaltySettings();
		return new ResponseEntity<LoyaltySettingsDTO>(dto, HttpStatus.OK);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<LoyaltySettingsDTO> update(@RequestBody LoyaltySettingsDTO loyaltySettingsDTO) {
		LoyaltySettingsDTO dto = loyaltySettingsService.updateLoyaltySettings(loyaltySettingsDTO);
		return new ResponseEntity<LoyaltySettingsDTO>(dto, HttpStatus.OK);
	}
	
}
