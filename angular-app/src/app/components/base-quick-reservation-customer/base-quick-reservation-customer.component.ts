import { CottageQuickReservationService } from 'src/app/pages/cottage-owner/services/cottage-quick-reservation.service';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IAddress } from 'src/app/model/address';
import { BoatQuickReservationService } from 'src/app/pages/boat-owner/services/boat-quick-reservation.service';
import { FishingQuickReservationService } from 'src/app/service/fishingQuickReservation.service';

@Component({
  selector: 'app-base-quick-reservation-customer',
  templateUrl: './base-quick-reservation-customer.component.html',
  styleUrls: ['./base-quick-reservation-customer.component.css'],
})
export class BaseQuickReservationCustomerComponent implements OnInit {
  @Input() id!: string;
  @Input() customerId!: number;
  @Input() reservationId!: number;
  @Input() name!: string;
  @Input() price!: string;
  @Input() openForReservation!: string;
  @Input() address!: IAddress;
  @Input() startDate!: Date;
  @Input() endDate!: Date;
  @Input() promoDesc!: string;
  @Input() grade!: number;
  @Input() type!: number;

  constructor(
    public router: Router,
    private cottageQuickService: CottageQuickReservationService,
    private boatQuickService: BoatQuickReservationService,
    private fishingQuickService: FishingQuickReservationService
  ) {}

  ngOnInit(): void {}

  book() {}

  bookCottageQuickReservation() {
    this.cottageQuickService
      .appoint(this.reservationId, this.customerId)
      .subscribe(() => {});
  }

  bookBoatQuickReservation() {
    this.boatQuickService
      .appoint(this.reservationId, this.customerId)
      .subscribe(() => {});
  }

  bookFishingQuickReservation() {
    this.fishingQuickService
      .appoint(this.reservationId, this.customerId)
      .subscribe(() => {});
  }
}
