import { IDateSpan } from './../../../model/dateSpan';
import {
  emptyFishingQuickReservation,
  IFishingQuickReservation,
} from './../../../model/fishingQuickReservation';
import { emptyFishingCourse } from './../../../model/fishingCourse';
import { IFishingImage } from './../../../model/fishingImage';
import { ActivatedRoute } from '@angular/router';
import { FishingCourseService } from './../../../service/fishingCourse.service';
import { ICustomer } from 'src/app/model/customer';
import { IAdditionalService } from 'src/app/model/additionalService';
import { IFishingCourse } from 'src/app/model/fishingCourse';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { IFishingReservation } from 'src/app/model/fishingReservation';
import { FishingQuickReservationService } from 'src/app/service/fishingQuickReservation.service';
import { FishingReservationService } from 'src/app/service/fishingReservation.service';

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

  @ViewChild('quickReservationInput')
  quickReservationFormElement!: ElementRef<HTMLInputElement>;
  @ViewChild('reservationInput', { static: true, read: ElementRef })
  reservationFormElement!: any;
  @ViewChild('customerSelect')
  customerSelectElement!: ElementRef<HTMLInputElement>;
  @ViewChild('availableStartSelect')
  availableStartSelectElement!: ElementRef<HTMLInputElement>;
  addReservationFormOpened = false;
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
  fishingQuickReservation: IFishingQuickReservation =
    emptyFishingQuickReservation;

  availableDateSpan!: IDateSpan;
  eligibleCustomers!: ICustomer[];
  imageObject: Array<object> = [];
  activeReservations!: IFishingReservation[];
  passedReservations!: IFishingReservation[];

  constructor(
    private route: ActivatedRoute,
    private fishingCourseService: FishingCourseService,
    private fishingCourseQuickReservationService: FishingQuickReservationService,
    private fishingCourseReservationService: FishingReservationService
  ) {}

  ngOnInit(): void {
    this.fishingCourseId = +this.route.snapshot.paramMap.get('id')!;
    this.getFishingCourse();
    this.fishingCourseQuickReservationService
      .getFishingQuickReservations()
      .subscribe((fishingCourseQuickReservation) => {
        this.fishingCourse.fishingQuickReservation =
          fishingCourseQuickReservation;
      });
    this.fishingCourseReservationService
      .getActiveFishingReservationByFishingCourseId(this.fishingCourseId)
      .subscribe((activeReservations) => {
        this.activeReservations = activeReservations;
      });
    this.fishingCourseReservationService
      .getPassedFishingReservationByFishingCourseId(this.fishingCourseId)
      .subscribe((passedReservations) => {
        this.passedReservations = passedReservations;
      });
    this.fishingCourseReservationService
      .getCustomerHasReservationNow(this.fishingCourseId)
      .subscribe((customers) => {
        this.eligibleCustomers = customers;
      });
    this.minDateString = this.date();
    this.minDate = new Date(Date.now());
  }

  reservationAdded() {
    this.fishingCourseReservationService
      .getActiveFishingReservationByFishingCourseId(this.fishingCourse.id)
      .subscribe((reservations) => {
        this.activeReservations = reservations;
        this.getFishingCourse();
      });
  }

  quickReservationAdded() {
    this.fishingCourseQuickReservationService
      .getFishingQuickReservations()
      .subscribe((fishingCourseQuickReservation) => {
        this.fishingCourse.fishingQuickReservation =
          fishingCourseQuickReservation;
        this.getFishingCourse();
      });
  }

  getFishingCourse() {
    this.fishingCourseService
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
      this.fishingCourseService
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
