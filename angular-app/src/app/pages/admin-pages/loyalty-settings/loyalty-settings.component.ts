import { ILoyaltySettings } from './../../../model/loyaltySettings';
import { Component, OnInit } from '@angular/core';
import { LoyaltySettingsService } from 'src/app/service/loyalty-settings.service';

@Component({
  selector: 'app-loyalty-settings',
  templateUrl: './loyalty-settings.component.html',
  styleUrls: ['./loyalty-settings.component.css'],
})
export class LoyaltySettingsComponent implements OnInit {
  loyaltySettings!: ILoyaltySettings;

  constructor(private loyaltySettingsService: LoyaltySettingsService) {}

  ngOnInit(): void {
    this.loyaltySettingsService
      .getLoyaltySettings()
      .subscribe((loyaltySettings) => {
        this.loyaltySettings = loyaltySettings;
      });
  }

  edit(): void {
    this.loyaltySettingsService
      .editLoyaltySettings(this.loyaltySettings)
      .subscribe((loyaltySettings) => {
        this.loyaltySettings = loyaltySettings;
      });
  }
}
