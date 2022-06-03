package isaproject.dto;

import isaproject.model.LoyaltyRank;

public class LoyaltyProgramDTO {

	private LoyaltyRank loyaltyRank;
	private Integer points;

	public LoyaltyProgramDTO() {
		super();
	}

	public LoyaltyRank getLoyaltyRank() {
		return loyaltyRank;
	}

	public void setLoyaltyRank(LoyaltyRank loyaltyRank) {
		this.loyaltyRank = loyaltyRank;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

}
