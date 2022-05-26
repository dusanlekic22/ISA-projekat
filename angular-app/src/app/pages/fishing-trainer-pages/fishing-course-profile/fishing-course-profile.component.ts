import { emptyFishingCourse } from './../../../model/fishingCourse';
import { IFishingImage } from './../../../model/fishingImage';
import { ActivatedRoute } from '@angular/router';
import { FishingCourseService } from './../../../service/fishingCourse.service';
import { ICustomer } from 'src/app/model/customer';
import { IAdditionalService } from 'src/app/model/additionalService';
import { IFishingCourse } from 'src/app/model/fishingCourse';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-fishing-course-profile',
  templateUrl: './fishing-course-profile.component.html',
  styleUrls: ['./fishing-course-profile.component.css'],
})
export class FishingCourseProfileComponent implements OnInit {
  fishingCourse: IFishingCourse = emptyFishingCourse;

  fishingImage: IFishingImage = {
    id: 0,
    image: '',
    fishingCourse: emptyFishingCourse,
  };

  // @ViewChild('quickReservationInput')
  // quickReservationFormElement!: ElementRef<HTMLInputElement>;
  @ViewChild('reservationInput', { static: true, read: ElementRef })
  reservationFormElement!: any;
  // @ViewChild('customerSelect')
  // customerSelectElement!: ElementRef<HTMLInputElement>;
  // @ViewChild('availableStartSelect')
  // availableStartSelectElement!: ElementRef<HTMLInputElement>;
  // addReservationFormOpened = false;
  additionalServiceTags: IAdditionalService[] = [];
  fishingCourseId!: number;
  minDate!: Date;
  minDateString!: string;
  imagePickerConf: object = {
    borderRadius: '4px',
    language: 'en',
    width: '320px',
    height: '240px',
  };
  // fishingCourseQuickReservation: IFishingCourseQuickReservation = {
  //   id: 0,
  //   duration: {
  //     startDate: new Date(),
  //     endDate: new Date(),
  //   },
  //   guestCapacity: 0,
  //   price: 0,
  // };

  // availableDateSpan!: IDateSpan;
  eligibleCustomers!: ICustomer[];
  imageObject: Array<object> = [];
  // activeReservations!: IFishingCourseReservation[];
  // passedReservations!: IFishingCourseReservation[];

  constructor(
    private _route: ActivatedRoute,
    private _fishingCourseService: FishingCourseService,
    // private _fishingCourseQuickReservationService: FishingCourseQuickReservationService,
    // private _fishingCourseReservationService: FishingCourseReservationService
  ) {}

  ngOnInit(): void {
    this.fishingCourseId = +this._route.snapshot.paramMap.get('id')!;
    this.getFishingCourse();
    // this._fishingCourseQuickReservationService
    //   .getFishingCourseQuickReservations()
    //   .subscribe((fishingCourseQuickReservation) => {
    //     this.fishingCourse.fishingCourseQuickReservation = fishingCourseQuickReservation;
    //   });
    // this._fishingCourseReservationService
    //   .getActiveFishingCourseReservationByFishingCourseId(this.fishingCourseId)
    //   .subscribe((activeReservations) => {
    //     this.activeReservations = activeReservations;
    //   });
    // this._fishingCourseReservationService
    //   .getPassedFishingCourseReservationByFishingCourseId(this.fishingCourseId)
    //   .subscribe((passedReservations) => {
    //     this.passedReservations = passedReservations;
    //   });
    // this._fishingCourseReservationService
    //   .getCustomerHasReservationNow()
    //   .subscribe((customers) => {
    //     this.eligibleCustomers = customers;
    //   });
    this.minDateString = this.date();
    this.minDate = new Date(Date.now());
  }

  // reservationAdded() {
  //   this._fishingCourseReservationService
  //     .getActiveFishingCourseReservationByFishingCourseId(this.fishingCourse.id)
  //     .subscribe((reservations) => {
  //       this.activeReservations = reservations;
  //       this.getFishingCourse();
  //     });
  // }

  // quickReservationAdded() {
  //   this._fishingCourseQuickReservationService
  //     .getFishingCourseQuickReservations()
  //     .subscribe((fishingCourseQuickReservation) => {
  //       this.fishingCourse.fishingCourseQuickReservation = fishingCourseQuickReservation;
  //       this.getFishingCourse();
  //     });
  // }

  getFishingCourse() {
    this._fishingCourseService
      .getFishingCourseById(this.fishingCourseId)
      .subscribe((fishingCourse) => {
        this.fishingCourse = fishingCourse;
        this.fishingImage.fishingCourse = fishingCourse;
        this.fishingCourse.fishingImage.forEach((element) => {
          this.imageObject.push({
            image: element.image,
            thumbImage: element.image,
            alt: 'alt of image',
          });
        });
      });
  }

  addFishingCourseImage(): void {
    if (this.fishingImage.image != '' && this.fishingImage.image != null)
      this._fishingCourseService
        .addFishingCourseImage(this.fishingImage)
        .subscribe((fishingCourseImage) => {
          this.imageObject.push({
            image: fishingCourseImage.image,
            thumbImage: fishingCourseImage.image,
            alt: 'alt of image',
          });
          this.fishingImage.image = '';
        });
  }

  onImageChange(event: string): void {
    this.fishingImage.image = event;
  }

  focusReservation() {
    this.reservationFormElement.nativeElement.scrollIntoView();
  }

  date() {
    let min = new Date();
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
    console.log(x);
    return x;
  }
}
