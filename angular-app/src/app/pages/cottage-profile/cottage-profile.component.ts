import { Component, OnInit } from '@angular/core';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { ActivatedRoute } from '@angular/router';
import { CottageService } from '../cottage.service';
import { ICottage } from './cottage';
import { ICottageQuickReservation } from './cottageQuickReservation';

@Component({
  selector: 'app-cottage-profile',
  templateUrl: './cottage-profile.component.html',
  styleUrls: ['../cotage-style.css'],
})
export class CottageProfileComponent implements OnInit {
  cottage: ICottage = {
    id: 0,
    name: '',
    address: {
      id: 0,
      city: 'Kraljevo',
      country: 'Srbija',
      latitude: '73',
      longitude: '89',
      street: 'Zmajevacka',
    },
    promoDescription: '',
    bedCount: 0,
    roomCount: 0,
    cottageRules: '',
    cottageImage: [],
    cottageReservation: [],
    cottageQuickReservation: [],
  };
  cottageId!: number;
  minDate!: Date;
  initialImage = 'https://havanatursa.com/assets/images/carousel/Hoteles.webp';
  imagePickerConf: object = {
    borderRadius: '4px',
    language: 'en',
    width: '320px',
    height: '240px',
  };
  imageString: string = '';
  cottageQuickReservation: ICottageQuickReservation = {
    id: 0,
    startDate: new Date(),
    endDate: new Date(),
    guestCapacity: '',
    price: 0,
  };
  startYear!: number;
  startMonth!: number;
  startDay!: number;
  startHours!: number;
  startMinutes!: number;

  endYear!: number;
  endMonth!: number;
  endDay!: number;
  endHours!: number;
  endMinutes!: number;

  duration!: number;
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
    {
      image: '../assets/img/theme/team-3-800x800.jpg',
      thumbImage: 'assets/img/theme/profile-cover.jpg',
      alt: 'alt of image',
    },
  ];

  constructor(
    private _route: ActivatedRoute,
    private _cottageService: CottageService
  ) {}

  ngOnInit(): void {
    this.cottageId = +this._route.snapshot.paramMap.get('cottageId')!;
    this._cottageService.getCottageById(this.cottageId).subscribe((cottage) => {
      this.cottage = cottage;
      this.cottage.cottageImage.forEach((element) => {
        console.log('slika');
        this.imageObject.push({
          image: element.image,
          thumbImage: element.image,
          alt: 'alt of image',
        });
      });
    });
    this._cottageService
      .getCottageQuickReservations()
      .subscribe((cottageQuickReservation) => {
        this.cottage.cottageQuickReservation = cottageQuickReservation;
      });
    this.minDate = new Date(Date.now());
  }

  addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
    if (event.value != null) {
      if (type === 'inputStart' || type === 'changeStart') {
        this.cottageQuickReservation.startDate.setFullYear(
          event.value.getFullYear()
        );
        this.cottageQuickReservation.startDate.setMonth(event.value.getMonth());
        this.cottageQuickReservation.startDate.setDate(event.value.getDate());
      } else if (type === 'inputEnd' || type === 'changeEnd') {
        this.cottageQuickReservation.endDate.setFullYear(
          event.value.getFullYear()
        );
        this.cottageQuickReservation.endDate.setMonth(event.value.getMonth());
        this.cottageQuickReservation.endDate.setDate(event.value.getDate());
      }
    }
  }

  edit(): void {
    this._cottageService.editCottage(this.cottage).subscribe((cottage) => {
      this.cottage = cottage;
    });
  }

  addCottageImage(): void {
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

  addQuickReservation(): void {
    this._cottageService
      .addCottageQuickReservation(this.cottageQuickReservation, this.cottage)
      .subscribe((quickReservation) => {this._cottageService
        .getCottageQuickReservations()
        .subscribe((cottageQuickReservation) => {
          this.cottage.cottageQuickReservation = cottageQuickReservation;
        });});
  }
}
