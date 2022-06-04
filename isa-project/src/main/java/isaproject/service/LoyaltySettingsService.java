package isaproject.service;

import isaproject.dto.LoyaltySettingsDTO;
import isaproject.model.LoyaltyProgram;

public interface LoyaltySettingsService {

	LoyaltySettingsDTO getLoyaltySettings();

	LoyaltySettingsDTO updateLoyaltySettings(LoyaltySettingsDTO loyaltySettingsDTO);
	
	Double getCustomerDiscount(LoyaltyProgram loyaltyProgram);

	Double getOwnerRevenue(LoyaltyProgram loyaltyProgram);
}
