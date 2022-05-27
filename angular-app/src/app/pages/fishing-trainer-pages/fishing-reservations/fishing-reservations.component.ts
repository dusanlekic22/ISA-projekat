import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ICustomer } from 'src/app/model/customer';
import { IFishingReservation } from 'src/app/model/fishingReservation';

@Component({
  selector: 'app-fishing-reservations',
  templateUrl: './fishing-reservations.component.html',
  styleUrls: ['./fishing-reservations.component.css']
})
export class FishingReservationsComponent implements OnInit {
  @Input() reservations!: IFishingReservation[];
  @Input() customers! : ICustomer[];
  @Output() customerEmit = new EventEmitter<number>();
  constructor(private router: Router) {}

  ngOnInit(): void {}

  newReservation(customer: ICustomer) {
    this.customerEmit.emit(customer.id);
  }

  deleteReservation(id: number) {}

  customerInfo(customer: ICustomer) {
    this.router.navigateByUrl(`customer/${customer.id}`);
  }

  isCustomerEligible(customer: ICustomer) {
    return this.customers.some(c => c.id === customer.id);
  }

  openQuickReservationForm() {
    // this.addReservationFormOpened = true;
    // this.quickReservationFormElement.nativeElement.scrollIntoView();
  }
}
