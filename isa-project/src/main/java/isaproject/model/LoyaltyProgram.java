package isaproject.model;

import javax.persistence.Embeddable;

@Embeddable
public class LoyaltyProgram {

	private LoyaltyRank loyaltyRank;
	private Integer points;

	public LoyaltyProgram() {
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
