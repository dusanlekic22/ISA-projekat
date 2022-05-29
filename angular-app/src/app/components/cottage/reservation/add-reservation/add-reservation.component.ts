import { IAdditionalService } from './../../../../model/additionalService';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CottageReservationService } from 'src/app/pages/cottage-owner/services/cottage-reservation.service';
import { ToastrService } from 'ngx-toastr';
import { ICustomer } from 'src/app/model/customer';
import { ICottageReservation } from 'src/app/model/cottageReservation';
import { ICottage } from 'src/app/model/cottage';
import { CottageService } from 'src/app/pages/cottage-owner/services/cottage.service';
import { UserService } from 'src/app/service/user.service';
import { ActivatedRoute } from '@angular/router';
import { MatChip } from '@angular/material/chips';
import { CottageAdditionalServicesService } from 'src/app/pages/cottage-owner/services/cottage-additional-services.service';

@Component({
  selector: 'app-add-reservation',
  templateUrl: './add-reservation.component.html',
  styleUrls: ['./add-reservation.component.css'],
})
export class AddReservationComponent implements OnInit {
  @Input() minDate!: string;
  @Output() submitted = new EventEmitter<boolean>();
  cottage!: ICottage;
  eligibleCustomers!: ICustomer[];
  @Input() customer!: ICustomer;
  cottages!: ICottage[];
  optionsCottages: string[] = [];
  chips!: MatChip;
  cottageChips: string[] = [];
  cottageServices: IAdditionalService[] = [];
  reservationServices: IAdditionalService[] = [];

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
        latitude: 0,
        longitude: 0,
      },
      enabled: true,
      verificationCode: '',
      points: '',
      loyalityProgram: '',
    },
    confirmed: false,
  };

  constructor(
    private _cottageReservationService: CottageReservationService,
    private _toastr: ToastrService,
    private _userService: UserService,
    private _cottageService: CottageService,
    private _route: ActivatedRoute,
    private _cottageAdditionalService: CottageAdditionalServicesService
  ) {}

  ngOnInit(): void {
    this._cottageReservationService
      .getCustomerHasReservationNow()
      .subscribe((customers) => {
        this.eligibleCustomers = customers;
      });
    let cottageId = this._route.snapshot.paramMap.get('cottageId');
    this._userService.currentUser.subscribe((user) => {
      this._cottageService
        .getCottagesByCottageOwnerId(user.id)
        .subscribe((cottages) => {
          this.cottages = cottages;
          if (cottageId != undefined)
            this.cottage = this.cottages.filter(
              (c) => c.id == parseInt(cottageId!)
            )[0];
        });
    });
  }

  setCustomer(id: number) {
    this.customer = this.eligibleCustomers.filter((c) => c.id == id)[0];
  }

  getChips() {
    console.log(this.cottage.id);
    this._cottageAdditionalService
      .getAdditionalServicesByCottageId(this.cottage.id)
      .subscribe((tags) => {
        tags.forEach((t) => {
          this.cottageServices.push(t);
          this.optionsCottages.push(t.name + '(' + t.price + ')');
        });
      });
  }

  toggleSelectionCottage(chip: MatChip, option: IAdditionalService) {
    if (chip.toggleSelected()) {
      this.reservationServices.push({id:0,name:option.name,price:option.price});
    } else {
      this.reservationServices = this.reservationServices.filter(
        (e) => e !== option
      );
    }
  }

  addReservation(): void {
    this.cottageReservation.customer = this.customer;
    this._cottageReservationService
      .addCottageReservation(this.cottageReservation, this.cottage)
      .subscribe(
        (reservation) => {
          this._toastr.success('Reservation was successfully added.');
          this.reservationServices.forEach((tag) => {
            this._cottageAdditionalService
              .addAdditionalServiceForCottageReservation(
                tag,
                reservation
              )
              .subscribe((service) => {});
          });
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
