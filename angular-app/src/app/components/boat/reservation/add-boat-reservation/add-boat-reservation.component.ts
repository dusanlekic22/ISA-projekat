import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { BoatReservationService } from 'src/app/pages/boat-owner/services/boat-reservation.service';
import { ToastrService } from 'ngx-toastr';
import { ICustomer } from 'src/app/model/customer';
import { BoatService } from 'src/app/pages/boat-owner/services/boat.service';
import { UserService } from 'src/app/service/user.service';
import { ActivatedRoute } from '@angular/router';
import { IBoat, initBoat } from 'src/app/model/boat/boat';
import { IBoatReservation } from 'src/app/model/boat/boatReservation';
import { MatChip } from '@angular/material/chips';
import { IAdditionalService } from 'src/app/model/additionalService';
import { BoatAdditionalServicesService } from 'src/app/pages/boat-owner/services/boat-additional-services.service';

@Component({
  selector: 'app-add-boat-reservation',
  templateUrl: './add-boat-reservation.component.html',
  styleUrls: ['./add-boat-reservation.component.css'],
})
export class AddBoatReservationComponent implements OnInit {
  @Input() minDate!: string;
  @Output() submitted = new EventEmitter<boolean>();
  boat!: IBoat;
  eligibleCustomers!: ICustomer[];
  @Input() customer!: ICustomer;
  boats!: IBoat[];
  chips!: MatChip;
  boatsChips: string[] = [];
  boatServices: IAdditionalService[] = [];
  reservationServices: IAdditionalService[] = [];

  boatReservation: IBoatReservation = {
    id: 0,
    duration: {
      startDate: new Date(),
      endDate: new Date(),
    },
    guestCapacity: 0,
    boat: initBoat,
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
      penalties: 0,
    },
    confirmed: false,
  };

  constructor(
    private _boatReservationService: BoatReservationService,
    private _toastr: ToastrService,
    private _userService: UserService,
    private _boatService: BoatService,
    private _route: ActivatedRoute,
    private _boatAdditionalService: BoatAdditionalServicesService
  ) {}

  ngOnInit(): void {
    let boatId = +this._route.snapshot.paramMap.get('boatId')!;
    this._boatReservationService
      .getCustomerHasReservationNow(boatId)
      .subscribe((customers) => {
        this.eligibleCustomers = customers;
      });
    this._userService.currentUser.subscribe((user) => {
      this._boatService.getBoatsByBoatOwnerId(user.id).subscribe((boats) => {
        this.boats = boats;
        if (boatId != undefined)
          this.boat = this.boats.filter((c) => c.id == boatId)[0];
      });
    });
    if (boatId != undefined) {
      this.getChips(boatId);
    }
  }

  setCustomer(id: number) {
    this.customer = this.eligibleCustomers.filter((c) => c.id == id)[0];
    this._boatReservationService
      .getCustomerHasReservationNow(id)
      .subscribe((customers) => {
        this.eligibleCustomers = customers;
      });
  }

  getChips(id: number) {
    this._boatAdditionalService
      .getAdditionalServicesByBoatId(id)
      .subscribe((tags) => {
        tags.forEach((t) => {
          if (this.boatServices.length < 1) {
            this.boatServices.push(t);
          } else if (this.boatServices.some((e) => e.name !== t.name)) {
            this.boatServices.push(t);
          }
        });
      });
  }

  toggleSelectionBoat(chip: MatChip, option: IAdditionalService) {
    if (chip.toggleSelected()) {
      this.reservationServices.push({
        id: 0,
        name: option.name,
        price: option.price,
      });
    } else {
      this.reservationServices = this.reservationServices.filter(
        (e) => e !== option
      );
    }
  }

  addReservation(): void {
    this.boatReservation.customer = this.customer;
    this._boatReservationService
      .addBoatReservation(this.boatReservation, this.boat)
      .subscribe(
        (reservation) => {
          this._toastr.success('Reservation was successfully added.');
          this.reservationServices.forEach((tag) => {
            this._boatAdditionalService
              .addAdditionalServiceForBoatReservation(tag, reservation)
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
