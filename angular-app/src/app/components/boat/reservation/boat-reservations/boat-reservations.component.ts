import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ICustomer } from 'src/app/model/customer';
import { ActivatedRoute, Router } from '@angular/router';
import { IBoatReservation } from 'src/app/model/boat/boatReservation';
import { IAdditionalService } from 'src/app/model/additionalService';
import { BoatAdditionalServicesService } from 'src/app/pages/boat-owner/services/boat-additional-services.service';

@Component({
  selector: 'app-boat-reservations',
  templateUrl: './boat-reservations.component.html',
  styleUrls: ['./boat-reservations.component.css'],
})
export class BoatReservationsComponent implements OnInit {
  @Input() reservations!: IBoatReservation[];
  @Input() customers!: ICustomer[];
  @Output() customerEmit = new EventEmitter<number>();
  services!: IAdditionalService[];

  constructor(
    private _route: ActivatedRoute,
    private _router: Router,
    private _additionalBoatService: BoatAdditionalServicesService
  ) {}

  ngOnInit(): void {
    let boatId = this._route.snapshot.paramMap.get('boatId')!;
    this._additionalBoatService
      .getAdditionalServicesByBoatReservationId(parseInt(boatId))
      .subscribe((services) => {
        this.services = services;
      });
  }

  newReservation(customer: ICustomer) {
    this.customerEmit.emit(customer.id);
  }

  deleteReservation(id: number) {}

  customerInfo(customer: ICustomer) {
    this._router.navigateByUrl(`customer/${customer.id}`);
  }

  isCustomerEligible(customer: ICustomer) {
    return this.customers.some((c) => c.id === customer.id);
  }

  openQuickReservationForm() {
    // this.addReservationFormOpened = true;
    // this.quickReservationFormElement.nativeElement.scrollIntoView();
  }
}
