package isaproject.dto;

public class LoyaltySettingsDTO {

	private Long id;
	private Integer customerScore;
	private Integer ownerScore;
	private Integer minScoreRegular;
	private Integer minScoreSilver;
	private Integer minScoreGold;
	private Double customerRegularDiscount;
	private Double onwerRegularRevenue;
	private Double customerSilverDiscount;
	private Double onwerSilverRevenue;
	private Double customerGoldDiscount;
	private Double onwerGoldRevenue;
	private Double systemRevenue;

	public LoyaltySettingsDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCustomerScore() {
		return customerScore;
	}

	public void setCustomerScore(Integer customerScore) {
		this.customerScore = customerScore;
	}

	public Integer getOwnerScore() {
		return ownerScore;
	}

	public void setOwnerScore(Integer ownerScore) {
		this.ownerScore = ownerScore;
	}

	public Integer getMinScoreRegular() {
		return minScoreRegular;
	}

	public void setMinScoreRegular(Integer minScoreRegular) {
		this.minScoreRegular = minScoreRegular;
	}

	public Integer getMinScoreSilver() {
		return minScoreSilver;
	}

	public void setMinScoreSilver(Integer minScoreSilver) {
		this.minScoreSilver = minScoreSilver;
	}

	public Integer getMinScoreGold() {
		return minScoreGold;
	}

	public void setMinScoreGold(Integer minScoreGold) {
		this.minScoreGold = minScoreGold;
	}

	public Double getCustomerRegularDiscount() {
		return customerRegularDiscount;
	}

	public void setCustomerRegularDiscount(Double customerRegularDiscount) {
		this.customerRegularDiscount = customerRegularDiscount;
	}

	public Double getOnwerRegularRevenue() {
		return onwerRegularRevenue;
	}

	public void setOnwerRegularRevenue(Double onwerRegularRevenue) {
		this.onwerRegularRevenue = onwerRegularRevenue;
	}

	public Double getCustomerSilverDiscount() {
		return customerSilverDiscount;
	}

	public void setCustomerSilverDiscount(Double customerSilverDiscount) {
		this.customerSilverDiscount = customerSilverDiscount;
	}

	public Double getOnwerSilverRevenue() {
		return onwerSilverRevenue;
	}

	public void setOnwerSilverRevenue(Double onwerSilverRevenue) {
		this.onwerSilverRevenue = onwerSilverRevenue;
	}

	public Double getCustomerGoldDiscount() {
		return customerGoldDiscount;
	}

	public void setCustomerGoldDiscount(Double customerGoldDiscount) {
		this.customerGoldDiscount = customerGoldDiscount;
	}

	public Double getOnwerGoldRevenue() {
		return onwerGoldRevenue;
	}

	public void setOnwerGoldRevenue(Double onwerGoldRevenue) {
		this.onwerGoldRevenue = onwerGoldRevenue;
	}

	public Double getSystemRevenue() {
		return systemRevenue;
	}

	public void setSystemRevenue(Double systemRevenue) {
		this.systemRevenue = systemRevenue;
	}

}
