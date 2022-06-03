export interface ILoyaltySettings {
  id: number;
  customerScore: number;
  ownerScore: number;
  minScoreRegular: number;
  minScoreSilver: number;
  minScoreGold: number;
  customerRegularDiscount: number;
  onwerRegularRevenue: number;
  customerSilverDiscount: number;
  onwerSilverRevenue: number;
  customerGoldDiscount: number;
  onwerGoldRevenue: number;
  systemRevenue: number;
}
