import { IUser, IRole } from './../../registration/registration/user';
import { IDateSpan } from './dateSpan';
import { ICottageReservation } from './cottageReservation';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { ActivatedRoute, Router } from '@angular/router';
import { IAdditionalService } from './additionalService';
import { ICottage } from './cottage';
import { ICottageQuickReservation } from './cottageQuickReservation';
import { ToastrService } from 'ngx-toastr';
import { CottageService } from '../services/cottage.service';
import { CottageQuickReservationService } from '../services/cottage-quick-reservation.service';
import { AdditionalServiceService } from '../services/additional-service.service';
import { CottageReservationService } from '../services/cottage-reservation.service';

@Component({
  selector: 'app-cottage-profile',
  templateUrl: './cottage-profile.component.html',
  styleUrls: ['./cottage-profile.component.css'],
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
  @ViewChild('reservationInput')
  reservationFormElement!: ElementRef<HTMLInputElement>;
  @ViewChild('customerSelect')
  customerSelectElement!: ElementRef<HTMLInputElement>;
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

  cottageReservation: ICottageReservation = {
    id: 0,
    duration: {
      startDate: new Date(),
      endDate: new Date(),
    },
    guestCapacity: 0,
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
        latitude: '',
        longitude: '',
      },
    },
  };

  availableDateSpan!: IDateSpan;
  availableStartDate!: Date;
  availableEndDate!: Date;
  eligibleCustomers!: IUser[];
  customer!: IUser;

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

  availableSpanFormOpened: boolean = false;

  constructor(
    private _route: ActivatedRoute,
    private _cottageService: CottageService,
    private _cottageQuickReservationService: CottageQuickReservationService,
    private _cottageReservationService: CottageReservationService,
    private _additionalServiceService: AdditionalServiceService,
    private _toastr: ToastrService,
    private _router: Router
  ) {}

  openQuickReservationForm() {
    this.addReservationFormOpened = true;
    this.quickReservationFormElement.nativeElement.scrollIntoView();
  }

  openAvailableSpanForm() {
    this.availableSpanFormOpened = true;
  }

  ngOnInit(): void {
    this.availableStartDate = new Date();
    this.availableEndDate = new Date();
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
        console.log(activeReservations[0].duration.startDate);
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

  private getCottage() {
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

  onItemAdded(input: any): void {
    let text = input.display.split(' ');
    this.additionalServiceTags.pop();
    this.additionalServiceTags.push({ id: 0, name: text[0], price: text[1] });
  }

  addStartEvent(startDate: Date, event: MatDatepickerInputEvent<Date>) {
    if (event.value != null) {
      startDate.setFullYear(event.value.getFullYear());
      startDate.setMonth(event.value.getMonth());
      startDate.setDate(event.value.getDate());
    }
  }

  addEndEvent(endDate: Date, event: MatDatepickerInputEvent<Date>) {
    if (event.value != null) {
      endDate.setFullYear(event.value.getFullYear());
      endDate.setMonth(event.value.getMonth());
      endDate.setDate(event.value.getDate());
      console.log('grrr' + this.availableEndDate);
    }
  }

  edit(): void {
    this._cottageService.editCottageInfo(this.cottage).subscribe((cottage) => {
      this.cottage = cottage;
      this.additionalServiceTags.forEach((element) => {
        this._additionalServiceService
          .addAdditionalServiceForCottage(element, this.cottage)
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
    this.cottageQuickReservation.duration.startDate = this.format(
      this.cottageQuickReservation.duration.startDate
    );
    this.cottageQuickReservation.duration.endDate = this.format(
      this.cottageQuickReservation.duration.endDate
    );
    this._cottageQuickReservationService
      .addCottageQuickReservation(this.cottageQuickReservation, this.cottage)
      .subscribe(
        (quickReservation) => {
          this.addReservationFormOpened = false;
          this._toastr.success('Reservation was successfully added.');
          this.getCottage();
          this._cottageQuickReservationService
            .getCottageQuickReservations()
            .subscribe((cottageQuickReservation) => {
              this.cottage.cottageQuickReservation = cottageQuickReservation;
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

  deleteQuickReservation(id: number): void {
    this._cottageQuickReservationService
      .deleteCottageQuickReservations(id)
      .subscribe(
        (quickReservation) => {
          this._toastr.success('Reservation was successfully removed.');
          this.getCottage();
          this._cottageQuickReservationService
            .getCottageQuickReservations()
            .subscribe((cottageQuickReservation) => {
              this.cottage.cottageQuickReservation = cottageQuickReservation;
            });
        },
        (err) => {
          this._toastr.error('Reservation removal failed');
        }
      );
  }

  addReservation(): void {
    console.log(this.cottageReservation.customer);
    this._cottageReservationService
      .addCottageReservation(this.cottageReservation)
      .subscribe(
        (reservation) => {
          this._toastr.success('Reservation was successfully added.');
          this._cottageReservationService
            .getActiveCottageReservationByCottageId(this.cottageId)
            .subscribe((reservations) => {
              this.activeReservations = reservations;
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

  format(d: Date): Date {
    return new Date(
      d.getFullYear(),
      d.getMonth(),
      d.getDate(),
      d.getHours(),
      d.getMinutes() - d.getTimezoneOffset()
    );
  }

  addDateSpan() {
    this._cottageService
      .editAvailableTerms(this.cottage.id, {
        startDate: this.availableStartDate,
        endDate: this.availableEndDate,
      })
      .subscribe(
        (cottage) => {
          this.cottage = cottage;
        },
        (err) => {
          this._toastr.error(
            'Theres already an active reservation in this date span.',
            'Try a different date!'
          );
        }
      );
    this.availableSpanFormOpened = false;
  }

  removeTerm(span: IDateSpan) {
    this.cottage.availableReservationDateSpan =
      this.cottage.availableReservationDateSpan.filter((term) => term != span);
    this._cottageService.editCottage(this.cottage).subscribe((cottage) => {
      this.cottage = cottage;
    });
  }

  newReservation(customer: IUser) {
    this.reservationFormElement.nativeElement.scrollIntoView(true);
    this.customer = customer;
    this.customerSelectElement.nativeElement.value = customer.firstName;
  }

  customerInfo(customer: IUser) {
    this._router.navigate([`customer/${customer.id}`]);
  }

  isCustomerEligible(customer: IUser) {
    return this.eligibleCustomers.includes(customer);
  }

  selectChange(event:any) {
    //In my case $event come with a id value
    this.cottageReservation.customer = this.eligibleCustomers[event];
  }
}
