import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CottageReservationService } from 'src/app/pages/cottage-owner/services/cottage-reservation.service';
import { ToastrService } from 'ngx-toastr';
import { ICustomer } from 'src/app/model/customer';
import { ICottageReservation } from 'src/app/model/cottageReservation';
import { ICottage } from 'src/app/model/cottage';

@Component({
  selector: 'app-add-reservation',
  templateUrl: './add-reservation.component.html',
  styleUrls: ['../../../../pages/cottage-owner/cottage-style.css'],
})
export class AddReservationComponent implements OnInit {
  @Input() minDate!:string;
  @Output() submitted = new EventEmitter<boolean>();
  @Input() cottage! : ICottage;
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
    confirmed:false
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
      customers.forEach(customer=>console.log(customer.firstName))
    });
  }

  addReservation(): void {
    this._cottageReservationService
      .addCottageReservation(this.cottageReservation,this.cottage)
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
