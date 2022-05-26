import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ICustomer } from 'src/app/model/customer';
import { Router } from '@angular/router';
import { IBoatReservation } from 'src/app/model/boat/boatReservation';

@Component({
  selector: 'app-boat-reservations',
  templateUrl: './boat-reservations.component.html',
  styleUrls: ['./boat-reservations.component.css'],
})
export class BoatReservationsComponent implements OnInit {
  @Input() reservations!: IBoatReservation[];
  @Input() customers! : ICustomer[];
  @Output() customerEmit = new EventEmitter<number>();
  constructor(private _router: Router) {}

  ngOnInit(): void {}

  newReservation(customer: ICustomer) {
    this.customerEmit.emit(customer.id);
  }

  deleteReservation(id: number) {}

  customerInfo(customer: ICustomer) {
    this._router.navigateByUrl(`customer/${customer.id}`);
  }
  
  isCustomerEligible(customer: ICustomer) {
    return this.customers.some(c => c.id === customer.id);
  }

  openQuickReservationForm() {
    // this.addReservationFormOpened = true;
    // this.quickReservationFormElement.nativeElement.scrollIntoView();
  }
}
