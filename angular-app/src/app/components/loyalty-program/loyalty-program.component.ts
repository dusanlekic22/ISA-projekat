import { LoyaltySettingsService } from './../../service/loyalty-settings.service';
import { ILoyaltySettings } from './../../model/loyaltySettings';
import { ILoyaltyProgram, LoyaltyRank } from './../../model/loyaltyProgram';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-loyalty-program',
  templateUrl: './loyalty-program.component.html',
  styleUrls: ['./loyalty-program.component.css'],
})
export class LoyaltyProgramComponent implements OnInit {
  @Input("loyaltyProgram") loyaltyProgram: ILoyaltyProgram = {
    loyaltyRank: LoyaltyRank.Regular,
    points: 0,
  };
  @Input("userType") userType: string = "customer";
  loyaltySettings!: ILoyaltySettings;

  constructor(private loyaltySettingsService: LoyaltySettingsService) {}

  ngOnInit(): void {
    this.loyaltySettingsService
      .getLoyaltySettings()
      .subscribe((loyaltySettings) => {
        this.loyaltySettings = loyaltySettings;
      });
  }

  getDiscount() {
    if (!this.loyaltySettings) return 0
    if (this.userType == "customer") {
      if (this.loyaltyProgram.loyaltyRank == LoyaltyRank.Regular) return this.loyaltySettings.customerRegularDiscount;
      if (this.loyaltyProgram.loyaltyRank == LoyaltyRank.Gold) return this.loyaltySettings.customerGoldDiscount;
      if (this.loyaltyProgram.loyaltyRank == LoyaltyRank.Silver) return this.loyaltySettings.customerSilverDiscount;
    } else if (this.userType == "owner") {
      if (this.loyaltyProgram.loyaltyRank == LoyaltyRank.Regular) return this.loyaltySettings.onwerRegularRevenue;
      if (this.loyaltyProgram.loyaltyRank == LoyaltyRank.Gold) return this.loyaltySettings.onwerGoldRevenue;
      if (this.loyaltyProgram.loyaltyRank == LoyaltyRank.Silver) return this.loyaltySettings.onwerSilverRevenue;
    }
    return 0;
  }
}
