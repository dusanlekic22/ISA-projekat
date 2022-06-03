package isaproject.mapper;

import isaproject.dto.LoyaltyProgramDTO;
import isaproject.dto.LoyaltySettingsDTO;
import isaproject.model.LoyaltyProgram;
import isaproject.model.LoyaltySettings;

public class LoyaltyMapper {

	public static LoyaltySettings DTOToLoyaltySettings(LoyaltySettingsDTO loyaltySettingsDTO) {
		LoyaltySettings loyaltySettings = new LoyaltySettings();
		loyaltySettings.setId(loyaltySettingsDTO.getId());
		loyaltySettings.setCustomerScore(loyaltySettingsDTO.getCustomerScore());
		loyaltySettings.setOwnerScore(loyaltySettingsDTO.getOwnerScore());
		loyaltySettings.setMinScoreRegular(loyaltySettingsDTO.getMinScoreRegular());
		loyaltySettings.setMinScoreSilver(loyaltySettingsDTO.getMinScoreSilver());
		loyaltySettings.setMinScoreGold(loyaltySettingsDTO.getMinScoreGold());
		loyaltySettings.setCustomerRegularDiscount(loyaltySettingsDTO.getCustomerRegularDiscount());
		loyaltySettings.setOnwerRegularRevenue(loyaltySettingsDTO.getOnwerRegularRevenue());
		loyaltySettings.setCustomerSilverDiscount(loyaltySettingsDTO.getCustomerSilverDiscount());
		loyaltySettings.setOnwerSilverRevenue(loyaltySettingsDTO.getOnwerSilverRevenue());
		loyaltySettings.setCustomerGoldDiscount(loyaltySettingsDTO.getCustomerGoldDiscount());
		loyaltySettings.setOnwerGoldRevenue(loyaltySettingsDTO.getOnwerGoldRevenue());
		loyaltySettings.setSystemRevenue(loyaltySettingsDTO.getSystemRevenue());
		return loyaltySettings;
	}

	public static LoyaltySettingsDTO LoyaltySettingsToDTO(LoyaltySettings loyaltySettings) {
		LoyaltySettingsDTO loyaltySettingsDTO = new LoyaltySettingsDTO();
		loyaltySettingsDTO.setId(loyaltySettings.getId());
		loyaltySettingsDTO.setCustomerScore(loyaltySettings.getCustomerScore());
		loyaltySettingsDTO.setOwnerScore(loyaltySettings.getOwnerScore());
		loyaltySettingsDTO.setMinScoreRegular(loyaltySettings.getMinScoreRegular());
		loyaltySettingsDTO.setMinScoreSilver(loyaltySettings.getMinScoreSilver());
		loyaltySettingsDTO.setMinScoreGold(loyaltySettings.getMinScoreGold());
		loyaltySettingsDTO.setCustomerRegularDiscount(loyaltySettings.getCustomerRegularDiscount());
		loyaltySettingsDTO.setOnwerRegularRevenue(loyaltySettings.getOnwerRegularRevenue());
		loyaltySettingsDTO.setCustomerSilverDiscount(loyaltySettings.getCustomerSilverDiscount());
		loyaltySettingsDTO.setOnwerSilverRevenue(loyaltySettings.getOnwerSilverRevenue());
		loyaltySettingsDTO.setCustomerGoldDiscount(loyaltySettings.getCustomerGoldDiscount());
		loyaltySettingsDTO.setOnwerGoldRevenue(loyaltySettings.getOnwerGoldRevenue());
		loyaltySettingsDTO.setSystemRevenue(loyaltySettings.getSystemRevenue());
		return loyaltySettingsDTO;
	}

	public static LoyaltyProgramDTO LoyaltyProgramToDTO(LoyaltyProgram loyaltyProgram) {
		LoyaltyProgramDTO loyaltyProgramDTO = new LoyaltyProgramDTO();
		loyaltyProgramDTO.setLoyaltyRank(loyaltyProgram.getLoyaltyRank());
		loyaltyProgramDTO.setPoints(loyaltyProgram.getPoints());
		return loyaltyProgramDTO;
	}

	public static LoyaltyProgram DTOTOLoyaltyProgram(LoyaltyProgramDTO loyaltyProgramDTO) {
		LoyaltyProgram loyaltyProgram = new LoyaltyProgram();
		loyaltyProgram.setLoyaltyRank(loyaltyProgramDTO.getLoyaltyRank());
		loyaltyProgram.setPoints(loyaltyProgramDTO.getPoints());
		return loyaltyProgram;
	}

}
