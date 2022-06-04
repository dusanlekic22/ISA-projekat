package isaproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isaproject.dto.LoyaltySettingsDTO;
import isaproject.mapper.LoyaltyMapper;
import isaproject.model.LoyaltyProgram;
import isaproject.model.LoyaltySettings;
import isaproject.repository.LoyaltySettingsRepository;
import isaproject.service.LoyaltySettingsService;

@Service
public class LoyaltySettingsServiceImpl implements LoyaltySettingsService {

	LoyaltySettingsRepository loyaltySettingsRepository;

	@Autowired
	public LoyaltySettingsServiceImpl(LoyaltySettingsRepository loyaltySettingsRepository) {
		super();
		this.loyaltySettingsRepository = loyaltySettingsRepository;
	}

	@Override
	public LoyaltySettingsDTO getLoyaltySettings() {
		LoyaltySettings loyaltySettings = loyaltySettingsRepository.findById(1L).get();
		return LoyaltyMapper.LoyaltySettingsToDTO(loyaltySettings);
	}

	@Override
	public LoyaltySettingsDTO updateLoyaltySettings(LoyaltySettingsDTO loyaltySettingsDTO) {
		LoyaltySettings loyaltySettings = LoyaltyMapper.DTOToLoyaltySettings(loyaltySettingsDTO);
		return LoyaltyMapper.LoyaltySettingsToDTO(loyaltySettingsRepository.save(loyaltySettings));
	}

	@Override
	public Double getCustomerDiscount(LoyaltyProgram loyaltyProgram) {
		LoyaltySettings loyaltySettings = loyaltySettingsRepository.findById(1L).get();
		switch (loyaltyProgram.getLoyaltyRank()) {
		case Gold:
			return loyaltySettings.getCustomerGoldDiscount();
		case Silver:
			return loyaltySettings.getCustomerSilverDiscount();
		case Regular:
			return loyaltySettings.getCustomerRegularDiscount();
		default:
			return 0.0;
		}
	}

	@Override
	public Double getOwnerRevenue(LoyaltyProgram loyaltyProgram) {
		LoyaltySettings loyaltySettings = loyaltySettingsRepository.findById(1L).get();
		switch (loyaltyProgram.getLoyaltyRank()) {
		case Gold:
			return loyaltySettings.getOnwerGoldRevenue();
		case Silver:
			return loyaltySettings.getOnwerSilverRevenue();
		case Regular:
			return loyaltySettings.getOnwerRegularRevenue();
		default:
			return 0.0;
		}
	}

}
