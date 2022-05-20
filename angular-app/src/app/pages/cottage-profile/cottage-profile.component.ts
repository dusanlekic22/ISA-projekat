import { IDateSpan } from './dateSpan';
import { ICottageReservation } from './cottageReservation';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { ActivatedRoute } from '@angular/router';
import { CottageService } from '../cottage.service';
import { IAdditionalService } from './additionalService';
import { ICottage } from './cottage';
import { ICottageQuickReservation } from './cottageQuickReservation';
import { ToastrService } from 'ngx-toastr';

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
    },
  };

  @ViewChild('quickReservationInput')
  reservationFormElement!: ElementRef<HTMLInputElement>;
  addReservationFormOpened = false;
  additionalServiceTags: IAdditionalService[] = [];
  cottageId!: number;
  minDate!: Date;
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
  
  availableStartDate!: Date;
  availableEndDate!: Date;
  
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
    private toastr: ToastrService
  ) {}

  openQuickReservationForm() {
    this.addReservationFormOpened = true;
    this.reservationFormElement.nativeElement.scrollIntoView();
  }

  ngOnInit(): void {
    this.cottageId = +this._route.snapshot.paramMap.get('cottageId')!;
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
    this._cottageService
      .getCottageQuickReservations()
      .subscribe((cottageQuickReservation) => {
        this.cottage.cottageQuickReservation = cottageQuickReservation;
      });
    this._cottageService
      .getAdditionalServicesByCottageId(this.cottageId)
      .subscribe((additionalService) => {
        this.additionalServiceTags = additionalService.filter(
          (additionalService) => additionalService.name != null
        );
      });
    this._cottageService
      .getActiveCottageReservationByCottageId(this.cottageId)
      .subscribe((activeReservations) => {
        this.activeReservations = activeReservations;
      });
    this._cottageService
      .getPassedCottageReservationByCottageId(this.cottageId)
      .subscribe((passedReservations) => {
        this.passedReservations = passedReservations;
      });
    this.minDate = new Date(Date.now());
  }

  onItemAdded(input: any): void {
    let text = input.display.split(' ');
    this.additionalServiceTags.pop();
    this.additionalServiceTags.push({ id: 0, name: text[0], price: text[1] });
  }

  addEvent(type: string, event: MatDatepickerInputEvent<Date>) {
    if (event.value != null) {
      if (type === 'inputStart' || type === 'changeStart') {
        this.cottageQuickReservation.duration.startDate.setFullYear(
          event.value.getFullYear()
        );
        this.cottageQuickReservation.duration.startDate.setMonth(
          event.value.getMonth()
        );
        this.cottageQuickReservation.duration.startDate.setDate(
          event.value.getDate()
        );
      } else if (type === 'inputEnd' || type === 'changeEnd') {
        this.cottageQuickReservation.duration.endDate.setFullYear(
          event.value.getFullYear()
        );
        this.cottageQuickReservation.duration.endDate.setMonth(
          event.value.getMonth()
        );
        this.cottageQuickReservation.duration.endDate.setDate(
          event.value.getDate()
        );
      }
    }
  }

  edit(): void {
    this._cottageService.editCottageInfo(this.cottage).subscribe((cottage) => {
      this.cottage = cottage;
      this.additionalServiceTags.forEach((element) => {
        this._cottageService
          .addAdditionalService(element, this.cottage)
          .subscribe((additionalService) => {});
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

  addQuickReservation(): void {
    this.cottageQuickReservation.duration.startDate = this.format(this.cottageQuickReservation.duration.startDate);
    this.cottageQuickReservation.duration.endDate = this.format(this.cottageQuickReservation.duration.endDate);
    this._cottageService
      .addCottageQuickReservation(this.cottageQuickReservation, this.cottage)
      .subscribe(
        (quickReservation) => {
          this.addReservationFormOpened = false;
          this.toastr.success('Reservation was successfully added.');
          this._cottageService
            .getCottageQuickReservations()
            .subscribe((cottageQuickReservation) => {
              this.cottage.cottageQuickReservation = cottageQuickReservation;
            });
        },
        (err) => {
          this.toastr.error(
            'Reservation term overlaps with another.',
            'Try a different date!'
          );
        }
      );
  }

  deleteQuickReservation(id: number): void {
    this._cottageService.deleteCottageQuickReservations(id).subscribe(
      (quickReservation) => {
        this.toastr.success('Reservation was successfully removed.');
        this._cottageService
          .getCottageQuickReservations()
          .subscribe((cottageQuickReservation) => {
            this.cottage.cottageQuickReservation = cottageQuickReservation;
          });
      },
      (err) => {
        this.toastr.error('Reservation removal failed');
      }
    );
  }

  format(d : Date): Date{
    return new Date(d.getFullYear(), d.getMonth(), d.getDate(), d.getHours(), d.getMinutes() - d.getTimezoneOffset());
  }

  addDateSpan() {
    this.cottage.availableReservationDateSpan.push({
      startDate: this.format(this.availableStartDate),
      endDate: this.format(this.availableEndDate),
    });
    this._cottageService.editCottage(this.cottage).subscribe((cottage) => {
      this.cottage = cottage;
    });
  }

  removeTerm(span: IDateSpan) {
    this.cottage.availableReservationDateSpan =
      this.cottage.availableReservationDateSpan.filter((term) => term != span);
      this._cottageService.editCottage(this.cottage).subscribe((cottage) => {
        this.cottage = cottage;
      });
  }
}
