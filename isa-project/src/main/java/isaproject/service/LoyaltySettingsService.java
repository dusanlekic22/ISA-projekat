package isaproject.service;

import isaproject.dto.LoyaltySettingsDTO;

public interface LoyaltySettingsService {

	LoyaltySettingsDTO getLoyaltySettings();

	LoyaltySettingsDTO updateLoyaltySettings(LoyaltySettingsDTO loyaltySettingsDTO);

}
