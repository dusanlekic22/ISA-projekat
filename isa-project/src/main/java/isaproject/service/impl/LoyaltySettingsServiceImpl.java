package isaproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
