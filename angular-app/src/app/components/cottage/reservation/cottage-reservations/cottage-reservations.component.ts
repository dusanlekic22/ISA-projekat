import { ICottageReservation } from './../../../../pages/cottage-owner/cottage-profile/cottageReservation';
import { Component, Input, OnInit } from '@angular/core';
import { ICustomer } from 'src/app/model/customer';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cottage-reservations',
  templateUrl: './cottage-reservations.component.html',
  styleUrls: ['../../../../pages/cottage-owner/cottage-style.css'],
})
export class CottageReservationsComponent implements OnInit {
  @Input() reservations!: ICottageReservation[];
  @Input() customers! : ICustomer[];
  constructor(private _router: Router) {}

  ngOnInit(): void {}

  newReservation(customer: ICustomer) {
    // this.reservationFormElement.nativeElement.scrollIntoView(true);
    // this.customer = customer;
    // this.customerSelectElement.nativeElement.value = customer.firstName;
  }

  deleteReservation(id: number) {}

  customerInfo(customer: ICustomer) {
    this._router.navigateByUrl(`customer/${customer.id}`);
  }
  
  isCustomerEligible(customer: ICustomer) {
    return this.customers.includes(customer);
  }

  openQuickReservationForm() {
    // this.addReservationFormOpened = true;
    // this.quickReservationFormElement.nativeElement.scrollIntoView();
  }
}
