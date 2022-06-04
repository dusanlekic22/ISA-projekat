export interface ILoyaltyProgram {
  points: number;
  loyaltyRank: LoyaltyRank;
}

export enum LoyaltyRank {
  Regular = "Regular",
  Silver = "Silver",
  Gold = "Gold",
}
