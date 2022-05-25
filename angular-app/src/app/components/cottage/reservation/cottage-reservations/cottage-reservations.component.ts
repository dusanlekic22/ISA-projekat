import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ICustomer } from 'src/app/model/customer';
import { Router } from '@angular/router';
import { ICottageReservation } from 'src/app/model/cottageReservation';

@Component({
  selector: 'app-cottage-reservations',
  templateUrl: './cottage-reservations.component.html',
  styleUrls: ['./cottage-reservations.component.css'],
})
export class CottageReservationsComponent implements OnInit {
  @Input() reservations!: ICottageReservation[];
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
