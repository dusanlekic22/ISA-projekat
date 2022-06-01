import { CustomerService } from './../../pages/customer.service';
import { ICustomer } from './../../model/customer';
import { CottageReservationService } from './../../pages/cottage-owner/services/cottage-reservation.service';
import { ToastrService } from 'ngx-toastr';
import { CottageAdditionalServicesService } from './../../pages/cottage-owner/services/cottage-additional-services.service';
import { IAdditionalService } from './../../model/additionalService';
import { MatChip } from '@angular/material/chips';
import { CottageService } from 'src/app/pages/cottage-owner/services/cottage.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { ICottage } from 'src/app/model/cottage';
import {
  emptyCottageReservation,
  ICottageReservation,
} from 'src/app/model/cottageReservation';

@Component({
  selector: 'app-base-reservation',
  templateUrl: './base-reservation.component.html',
  styleUrls: ['./base-reservation.component.css'],
})
export class BaseReservationComponent implements OnInit {
  id!: string;
  startCottageDate: Date = new Date();
  endCottageDate: Date = new Date();
  minDate: Date = new Date();
  minDateString: string = '';
  startDateCottageString: string = '';
  endDateCottageString: any = '';
  cottage!: ICottage;
  cottages!: ICottage[];
  chips!: MatChip;
  cottageChips: string[] = [];
  cottageServices: IAdditionalService[] = [];
  reservationServices: IAdditionalService[] = [];
  cottageReservation: ICottageReservation = emptyCottageReservation;

  constructor(
    private _route: ActivatedRoute,
    private _toastr: ToastrService,
    private _cottageService: CottageService,
    private _customerService: CustomerService,
    private _cottageReservationService: CottageReservationService,
    private _cottageAdditionalService: CottageAdditionalServicesService
  ) {}

  ngOnInit(): void {
    this.minDateString = this.date(new Date());
    this.startDateCottageString = this.date(new Date());
    this._customerService
      .getLoggedInCustomer()
      .subscribe((customer) => (this.cottageReservation.customer = customer));

    this._route.params.subscribe((data) => {
      this.id = data.id;
      this.cottageReservation.duration.startDate = data.startDate;
      this.cottageReservation.duration.endDate = data.endDate;
      if (this.endCottageDate !== null) {
        this.activateCottageEnd();
      }
      this._cottageService
        .getCottageById(parseInt(this.id))
        .subscribe((data) => {
          this.cottage = data;
          console.log(data);
        });
      if (this.id != undefined) {
        this._cottageAdditionalService
          .getAdditionalServicesByCottageId(parseInt(this.id))
          .subscribe((tags) => {
            tags.forEach((t) => {
              this.cottageServices.push(t);
            });
          });
        window.scrollTo(0, 0);
      }
    });
  }

  getChips() {
    this._cottageAdditionalService
      .getAdditionalServicesByCottageId(this.cottage.id)
      .subscribe((tags) => {
        tags.forEach((t) => {
          this.cottageServices.push(t);
        });
      });
  }

  toggleSelectionCottage(chip: MatChip, option: IAdditionalService) {
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
    this._cottageReservationService
      .addCottageReservationCustomer(this.cottageReservation, this.cottage)
      .subscribe(
        (Reservation) => {
          //this.addReservationFormOpened = false;
          this._toastr.success('Reservation was successfully added.');
          this.reservationServices.forEach((tag) => {
            this._cottageAdditionalService
              .addAdditionalServiceForCottageReservation(tag, Reservation)
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

  activateCottageEnd() {
    let x = '';
    x = this.startCottageDate.toString();
    this.endDateCottageString = this.startCottageDate;
  }
}
