import { ICustomer } from './../../../model/customer';
import { Component, ElementRef, OnInit, ViewChild, Pipe } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BoatService } from '../services/boat.service';
import { BoatQuickReservationService } from '../services/boat-quick-reservation.service';
import { BoatReservationService } from '../services/boat-reservation.service';
import { IDateSpan } from 'src/app/model/dateSpan';
import { IBoat, initBoat } from 'src/app/model/boat/boat';
import { IBoatQuickReservation } from 'src/app/model/boat/boatQuickReservation';
import { IBoatReservation } from 'src/app/model/boat/boatReservation';

@Component({
  selector: 'app-boat-profile',
  templateUrl: './boat-profile.component.html',
  styleUrls: ['./boat-profile.component.css'],
})
export class BoatProfileComponent implements OnInit {
  boat: IBoat = initBoat;

  @ViewChild('quickReservationInput')
  quickReservationFormElement!: ElementRef<HTMLInputElement>;
  @ViewChild('reservationInput', { static: true, read: ElementRef })
  reservationFormElement!: any;
  @ViewChild('customerSelect')
  customerSelectElement!: ElementRef<HTMLInputElement>;
  @ViewChild('availableStartSelect')
  availableStartSelectElement!: ElementRef<HTMLInputElement>;
  addReservationFormOpened = false;
  boatId!: number;
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
  boatQuickReservation: IBoatQuickReservation = {
    id: 0,
    duration: {
      startDate: new Date(),
      endDate: new Date(),
    },
    guestCapacity: 0,
    price: 0,
    boat: initBoat
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
  activeReservations!: IBoatReservation[];
  passedReservations!: IBoatReservation[];

  constructor(
    private _route: ActivatedRoute,
    private _boatService: BoatService,
    private _boatQuickReservationService: BoatQuickReservationService,
    private _boatReservationService: BoatReservationService
  ) {}

  ngOnInit(): void {
    this.minDateString = this.date();
    this.boatId = +this._route.snapshot.paramMap.get('boatId')!;
    this.getBoat();
    this._boatQuickReservationService
      .getBoatQuickReservations()
      .subscribe((boatQuickReservation) => {
        this.boat.boatQuickReservation = boatQuickReservation;
      });
    this._boatReservationService
      .getActiveBoatReservationByBoatId(this.boatId)
      .subscribe((activeReservations) => {
        this.activeReservations = activeReservations;
      });
    this._boatReservationService
      .getPassedBoatReservationByBoatId(this.boatId)
      .subscribe((passedReservations) => {
        this.passedReservations = passedReservations;
      });
    this._boatReservationService
      .getCustomerHasReservationNow(this.boatId)
      .subscribe((customers) => {
        this.eligibleCustomers = customers;
      });
    this.minDate = new Date(Date.now());
  }

  reservationAdded() {
    this._boatReservationService
      .getActiveBoatReservationByBoatId(this.boat.id)
      .subscribe((reservations) => {
        this.activeReservations = reservations;
        this.getBoat();
      });
  }

  quickReservationAdded() {
    this._boatQuickReservationService
      .getBoatQuickReservations()
      .subscribe((boatQuickReservation) => {
        this.boat.boatQuickReservation = boatQuickReservation;
        this.getBoat();
      });
  }

  getBoat() {
    this._boatService.getBoatById(this.boatId).subscribe((boat) => {
      this.boat = boat;
      this.boat.boatImage.forEach((element) => {
        this.imageObject.push({
          image: element.image,
          thumbImage: element.image,
          alt: 'alt of image',
        });
      });
    });
  }

  addBoatImage(): void {
    if (this.imageString != '')
      this._boatService
        .addBoatImage(0, this.imageString, this.boat)
        .subscribe((boatImage) => {
          this.imageObject.push({
            image: boatImage.image,
            thumbImage: boatImage.image,
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
