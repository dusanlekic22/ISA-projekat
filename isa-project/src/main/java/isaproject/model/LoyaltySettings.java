package isaproject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LoyaltySettings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int customerScore;
	private int ownerScore;
	private int minScoreRegular;
	private int minScoreSilver;
	private int minScoreGold;
	private double customerRegularDiscount;
	private double onwerRegularRevenue;
	private double customerSilverDiscount;
	private double onwerSilverRevenue;
	private double customerGoldDiscount;
	private double onwerGoldRevenue;
	private double systemRevenue;

	public LoyaltySettings() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCustomerScore() {
		return customerScore;
	}

	public void setCustomerScore(int customerScore) {
		this.customerScore = customerScore;
	}

	public int getOwnerScore() {
		return ownerScore;
	}

	public void setOwnerScore(int ownerScore) {
		this.ownerScore = ownerScore;
	}

	public int getMinScoreRegular() {
		return minScoreRegular;
	}

	public void setMinScoreRegular(int minScoreRegular) {
		this.minScoreRegular = minScoreRegular;
	}

	public int getMinScoreSilver() {
		return minScoreSilver;
	}

	public void setMinScoreSilver(int minScoreSilver) {
		this.minScoreSilver = minScoreSilver;
	}

	public int getMinScoreGold() {
		return minScoreGold;
	}

	public void setMinScoreGold(int minScoreGold) {
		this.minScoreGold = minScoreGold;
	}

	public double getCustomerRegularDiscount() {
		return customerRegularDiscount;
	}

	public void setCustomerRegularDiscount(double customerRegularDiscount) {
		this.customerRegularDiscount = customerRegularDiscount;
	}

	public double getOnwerRegularRevenue() {
		return onwerRegularRevenue;
	}

	public void setOnwerRegularRevenue(double onwerRegularRevenue) {
		this.onwerRegularRevenue = onwerRegularRevenue;
	}

	public double getCustomerSilverDiscount() {
		return customerSilverDiscount;
	}

	public void setCustomerSilverDiscount(double customerSilverDiscount) {
		this.customerSilverDiscount = customerSilverDiscount;
	}

	public double getOnwerSilverRevenue() {
		return onwerSilverRevenue;
	}

	public void setOnwerSilverRevenue(double onwerSilverRevenue) {
		this.onwerSilverRevenue = onwerSilverRevenue;
	}

	public double getCustomerGoldDiscount() {
		return customerGoldDiscount;
	}

	public void setCustomerGoldDiscount(double customerGoldDiscount) {
		this.customerGoldDiscount = customerGoldDiscount;
	}

	public double getOnwerGoldRevenue() {
		return onwerGoldRevenue;
	}

	public void setOnwerGoldRevenue(double onwerGoldRevenue) {
		this.onwerGoldRevenue = onwerGoldRevenue;
	}

	public double getSystemRevenue() {
		return systemRevenue;
	}

	public void setSystemRevenue(double systemRevenue) {
		this.systemRevenue = systemRevenue;
	}

}
