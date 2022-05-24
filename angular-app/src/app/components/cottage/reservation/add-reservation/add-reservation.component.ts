import { ICottageReservation } from './../../../../pages/cottage-owner/cottage-profile/cottageReservation';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CottageReservationService } from 'src/app/pages/cottage-owner/services/cottage-reservation.service';
import { ICottage } from 'src/app/pages/cottage-owner/cottage-profile/cottage';
import { ToastrService } from 'ngx-toastr';
import { ICustomer } from 'src/app/model/customer';

@Component({
  selector: 'app-add-reservation',
  templateUrl: './add-reservation.component.html',
  styleUrls: ['../../../../pages/cottage-owner/cottage-style.css'],
})
export class AddReservationComponent implements OnInit {
  @Input() minDate!:string;
  @Output() submitted = new EventEmitter<boolean>();
  eligibleCustomers!: ICustomer[];
  customer!: ICustomer;

  cottageReservation: ICottageReservation = {
    id: 0,
    duration: {
      startDate: new Date(),
      endDate: new Date(),
    },
    guestCapacity: 0,
    price: 0,
    customer: {
      id: 0,
      firstName: '',
      lastName: '',
      username: '',
      password: '',
      email: '',
      phoneNumber: '',
      roles: [],
      address: {
        street: '',
        city: '',
        country: '',
        latitude: '',
        longitude: '',
      },
      enabled: true,
      verificationCode: '',
      points: '',
      loyalityProgram: '',
    },
  };

  constructor(
    private _cottageReservationService: CottageReservationService,
    private _toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this._cottageReservationService
    .getCustomerHasReservationNow()
    .subscribe((customers) => {
      this.eligibleCustomers = customers;
    });
  }

  addReservation(): void {
    this._cottageReservationService
      .addCottageReservation(this.cottageReservation)
      .subscribe(
        (reservation) => {
          this._toastr.success('Reservation was successfully added.');
            
        this.submitted.emit();
        },
        (err) => {
          this._toastr.error(
            'Reservation term overlaps with another.',
            'Try a different date!'
          );
        }
      );
  }
}
