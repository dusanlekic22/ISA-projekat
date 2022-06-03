import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { IAdditionalService } from './../../model/additionalService';
import { MatChip } from '@angular/material/chips';
import { ActivatedRoute } from '@angular/router';
import { CustomerService } from 'src/app/pages/customer.service';
import { IFishingCourse } from 'src/app/model/fishingCourse';
import { FishingCourseService } from 'src/app/service/fishingCourse.service';
import {
  emptyFishingReservation,
  IFishingReservation,
} from 'src/app/model/fishingReservation';
import { FishingReservationService } from 'src/app/service/fishingReservation.service';
import { AdditionalServiceService } from 'src/app/pages/cottage-owner/services/additional-service.service';

@Component({
  selector: 'app-base-fishing-course-reservation',
  templateUrl: './base-fishing-course-reservation.component.html',
  styleUrls: ['./base-fishing-course-reservation.component.css'],
})
export class BaseFishingCourseReservationComponent implements OnInit {
  id!: string;
  startFishingCourseDate: Date = new Date();
  endFishingCourseDate: Date = new Date();
  minDate: Date = new Date();
  minDateString: string = '';
  startDateFishingCourseString: string = '';
  endDateFishingCourseString: any = '';
  fishingCourse!: IFishingCourse;
  fishingCourses!: IFishingCourse[];
  chips!: MatChip;
  fishingCourseChips: string[] = [];
  fishingCourseServices: IAdditionalService[] = [];
  reservationServices: IAdditionalService[] = [];
  fishingCourseReservation: IFishingReservation = emptyFishingReservation;

  constructor(
    private _route: ActivatedRoute,
    private _toastr: ToastrService,
    private _fishingCourseService: FishingCourseService,
    private _customerService: CustomerService,
    private _fishingCourseReservationService: FishingReservationService,
    private _fishingCourseAdditionalService: AdditionalServiceService
  ) {}

  ngOnInit(): void {
    this.minDateString = this.date(new Date());
    this.startDateFishingCourseString = this.date(new Date());
    this._customerService
      .getLoggedInCustomer()
      .subscribe(
        (customer) => (this.fishingCourseReservation.customer = customer)
      );

    this._route.params.subscribe((data) => {
      this.id = data.id;
      this.fishingCourseReservation.duration.startDate = data.startDate;
      this.fishingCourseReservation.duration.endDate = data.endDate;
      if (this.endFishingCourseDate !== null) {
        this.activateFishingCourseEnd();
      }
      this._fishingCourseService
        .getFishingCourseById(parseInt(this.id))
        .subscribe((data) => {
          this.fishingCourse = data;
          this.fishingCourseReservation.fishingCourse = this.fishingCourse;
          console.log(data);
        });
      if (this.id != undefined) {
        this._fishingCourseAdditionalService
          .getAdditionalServicesByFishingCourseId(parseInt(this.id))
          .subscribe((tags) => {
            tags.forEach((t) => {
              this.fishingCourseServices.push(t);
            });
          });
        window.scrollTo(0, 0);
      }
    });
  }

  getChips() {
    this._fishingCourseAdditionalService
      .getAdditionalServicesByFishingCourseId(this.fishingCourse.id)
      .subscribe((tags) => {
        tags.forEach((t) => {
          this.fishingCourseServices.push(t);
        });
      });
  }

  toggleSelectionFishingCourse(chip: MatChip, option: IAdditionalService) {
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
    this._fishingCourseReservationService
      .addFishingReservationCustomer(this.fishingCourseReservation)
      .subscribe(
        (reservation) => {
          this._toastr.success('Reservation was successfully added.');
          this.reservationServices.forEach((tag) => {
            this._fishingCourseAdditionalService
              .addAdditionalServiceForFishingReservation(tag, reservation)
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

  activateFishingCourseEnd() {
    this.endDateFishingCourseString = this.startFishingCourseDate;
  }
}
