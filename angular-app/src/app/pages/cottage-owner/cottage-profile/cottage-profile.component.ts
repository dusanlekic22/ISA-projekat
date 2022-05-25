import { ICustomer } from './../../../model/customer';
import { Component, ElementRef, OnInit, ViewChild, Pipe } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IAdditionalService } from '../../../model/additionalService';
import { CottageService } from '../services/cottage.service';
import { CottageQuickReservationService } from '../services/cottage-quick-reservation.service';
import { AdditionalServiceService } from '../services/additional-service.service';
import { CottageReservationService } from '../services/cottage-reservation.service';
import { ICottage } from 'src/app/model/cottage';
import { ICottageQuickReservation } from 'src/app/model/cottageQuickReservation';
import { ICottageReservation } from 'src/app/model/cottageReservation';
import { IDateSpan } from 'src/app/model/dateSpan';

@Component({
  selector: 'app-cottage-profile',
  templateUrl: './cottage-profile.component.html',
  styleUrls: ['../cottage-style.css'],
})
export class CottageProfileComponent implements OnInit {
  cottage: ICottage = {
    id: 0,
    name: '',
    address: {
      city: 'Kraljevo',
      country: 'Srbija',
      latitude: '73',
      longitude: '89',
      street: 'Zmajevacka',
    },
    promoDescription: '',
    bedCount: 0,
    roomCount: 0,
    pricePerHour: 0,
    cottageRules: '',
    cottageImage: [],
    cottageReservation: [],
    cottageQuickReservation: [],
    availableReservationDateSpan: [],
    cottageOwner: {
      id: 0,
      username: '',
      password: '',
      firstName: '',
      lastName: '',
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
    },
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
  cottageId!: number;
  minDate!: Date;
  minDateString!: string;
  initialImage = '';
  imagePickerConf: object = {
    borderRadius: '4px',
    language: 'en',
    width: '320px',
    height: '240px',
  };
  imageString: string = '';
  cottageQuickReservation: ICottageQuickReservation = {
    id: 0,
    duration: {
      startDate: new Date(),
      endDate: new Date(),
    },
    guestCapacity: 0,
    price: 0,
  };

  availableDateSpan!: IDateSpan;
  eligibleCustomers!: ICustomer[];
  imageObject: Array<object> = [
    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
    },
    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
    },
  ];
  activeReservations!: ICottageReservation[];
  passedReservations!: ICottageReservation[];

  constructor(
    private _route: ActivatedRoute,
    private _cottageService: CottageService,
    private _cottageQuickReservationService: CottageQuickReservationService,
    private _additionalServiceService: AdditionalServiceService,
    private _cottageReservationService: CottageReservationService
  ) {}

  ngOnInit(): void {
    this.minDateString = this.date();
    this.cottageId = +this._route.snapshot.paramMap.get('cottageId')!;
    this.getCottage();
    this._cottageQuickReservationService
      .getCottageQuickReservations()
      .subscribe((cottageQuickReservation) => {
        this.cottage.cottageQuickReservation = cottageQuickReservation;
      });
    this._additionalServiceService
      .getAdditionalServicesByCottageId(this.cottageId)
      .subscribe((additionalService) => {
        this.additionalServiceTags = additionalService.filter(
          (additionalService) => additionalService.name != null
        );
      });
    this._cottageReservationService
      .getActiveCottageReservationByCottageId(this.cottageId)
      .subscribe((activeReservations) => {
        this.activeReservations = activeReservations;
      });
    this._cottageReservationService
      .getPassedCottageReservationByCottageId(this.cottageId)
      .subscribe((passedReservations) => {
        this.passedReservations = passedReservations;
      });
    this._cottageReservationService
      .getCustomerHasReservationNow()
      .subscribe((customers) => {
        this.eligibleCustomers = customers;
      });
    this.minDate = new Date(Date.now());
  }

  reservationAdded() {
    this._cottageReservationService
      .getActiveCottageReservationByCottageId(this.cottage.id)
      .subscribe((reservations) => {
        this.activeReservations = reservations;
        this.getCottage();
      });
  }

  quickReservationAdded() {
    this._cottageQuickReservationService
      .getCottageQuickReservations()
      .subscribe((cottageQuickReservation) => {
        this.cottage.cottageQuickReservation = cottageQuickReservation;
        this.getCottage();
      });
  }

  getCottage() {
    this._cottageService.getCottageById(this.cottageId).subscribe((cottage) => {
      this.cottage = cottage;
      this.cottage.cottageImage.forEach((element) => {
        this.imageObject.push({
          image: element.image,
          thumbImage: element.image,
          alt: 'alt of image',
        });
      });
    });
  }

  addCottageImage(): void {
    if (this.imageString != '')
      this._cottageService
        .addCottageImage(0, this.imageString, this.cottage)
        .subscribe((cottageImage) => {
          this.imageObject.push({
            image: cottageImage.image,
            thumbImage: cottageImage.image,
            alt: 'alt of image',
          });
        });
  }

  onImageChange(event: string): void {
    this.imageString = event;
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
