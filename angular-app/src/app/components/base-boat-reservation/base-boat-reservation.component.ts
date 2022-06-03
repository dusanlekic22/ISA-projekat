import { Component, OnInit } from '@angular/core';
import { CustomerService } from './../../pages/customer.service';
import { ToastrService } from 'ngx-toastr';
import { IAdditionalService } from './../../model/additionalService';
import { MatChip } from '@angular/material/chips';
import { ActivatedRoute } from '@angular/router';

import { IBoat } from 'src/app/model/boat/boat';
import {
  emptyBoatReservation,
  IBoatReservation,
} from 'src/app/model/boat/boatReservation';
import { BoatService } from 'src/app/pages/boat-owner/services/boat.service';
import { BoatReservationService } from 'src/app/pages/boat-owner/services/boat-reservation.service';
import { BoatAdditionalServicesService } from 'src/app/pages/boat-owner/services/boat-additional-services.service';

@Component({
  selector: 'app-base-boat-reservation',
  templateUrl: './base-boat-reservation.component.html',
  styleUrls: ['./base-boat-reservation.component.css'],
})
export class BaseBoatReservationComponent implements OnInit {
  id!: string;
  startBoatDate: Date = new Date();
  endBoatDate: Date = new Date();
  minDate: Date = new Date();
  minDateString: string = '';
  startDateBoatString: string = '';
  endDateBoatString: any = '';
  boat!: IBoat;
  boats!: IBoat[];
  chips!: MatChip;
  boatChips: string[] = [];
  boatServices: IAdditionalService[] = [];
  reservationServices: IAdditionalService[] = [];
  boatReservation: IBoatReservation = emptyBoatReservation;

  constructor(
    private _route: ActivatedRoute,
    private _toastr: ToastrService,
    private _boatService: BoatService,
    private _customerService: CustomerService,
    private _boatReservationService: BoatReservationService,
    private _boatAdditionalService: BoatAdditionalServicesService
  ) {}

  ngOnInit(): void {
    this.minDateString = this.date(new Date());
    this.startDateBoatString = this.date(new Date());
    this._customerService
      .getLoggedInCustomer()
      .subscribe((customer) => (this.boatReservation.customer = customer));

    this._route.params.subscribe((data) => {
      this.id = data.id;
      this.boatReservation.duration.startDate = data.startDate;
      this.boatReservation.duration.endDate = data.endDate;
      if (this.endBoatDate !== null) {
        this.activateBoatEnd();
      }
      this._boatService.getBoatById(parseInt(this.id)).subscribe((data) => {
        this.boat = data;
        console.log(data);
      });
      if (this.id != undefined) {
        this._boatAdditionalService
          .getAdditionalServicesByBoatId(parseInt(this.id))
          .subscribe((tags) => {
            tags.forEach((t) => {
              this.boatServices.push(t);
            });
          });
        window.scrollTo(0, 0);
      }
    });
  }

  getChips() {
    this._boatAdditionalService
      .getAdditionalServicesByBoatId(this.boat.id)
      .subscribe((tags) => {
        tags.forEach((t) => {
          this.boatServices.push(t);
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
    this._boatReservationService
      .addBoatReservationCustomer(this.boatReservation, this.boat)
      .subscribe(
        (Reservation) => {
          //this.addReservationFormOpened = false;
          this._toastr.success('Reservation was successfully added.');
          this.reservationServices.forEach((tag) => {
            this._boatAdditionalService
              .addAdditionalServiceForBoatReservation(tag, Reservation)
              .subscribe((service) => {});
          });
        },
        (err) => {
          this._toastr.error(
            'Reservation term overlaps with another.',
            'Try a different date!'
          );
        }
      );
  }

  date(min: Date): string {
    let month = '';
    let day = '';
    if (min.getMonth() < 10) {
      month = '0' + (min.getMonth() + 1).toString();
    } else {
      month = (min.getMonth() + 1).toString();
    }
    if (min.getDate() < 10) {
      day = '0' + min.getDate().toString();
    } else {
      day = min.getDate().toString();
    }
    let x = min.getFullYear().toString() + '-' + month + '-' + day + 'T00:00';
    return x;
  }

  activateBoatEnd() {
    let x = '';
    x = this.startBoatDate.toString();
    this.endDateBoatString = this.startBoatDate;
  }
}
