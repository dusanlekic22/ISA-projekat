import { initCottage } from './../../../../model/cottage';
import { UserService } from './../../../../service/user.service';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CottageQuickReservationService } from 'src/app/pages/cottage-owner/services/cottage-quick-reservation.service';
import { ToastrService } from 'ngx-toastr';
import { CottageService } from 'src/app/pages/cottage-owner/services/cottage.service';
import { ICottage } from 'src/app/model/cottage';
import { ICottageQuickReservation } from 'src/app/model/cottageQuickReservation';
import { ActivatedRoute, Route } from '@angular/router';
import { MatChip } from '@angular/material/chips';
import { IAdditionalService } from 'src/app/model/additionalService';
import { CottageAdditionalServicesService } from 'src/app/pages/cottage-owner/services/cottage-additional-services.service';

@Component({
  selector: 'app-add-cottage-quick-reservation',
  templateUrl: './add-cottage-quick-reservation.component.html',
  styleUrls: ['./add-cottage-quick-reservation.component.css'],
})
export class AddCottageQuickReservationComponent implements OnInit {
  cottage!: ICottage;
  @Input() cottageQuickReservation: ICottageQuickReservation = {
    id: 0,
    duration: {
      startDate: new Date(),
      endDate: new Date(),
    },
    guestCapacity: 0,
    price: 0,
    cottage: initCottage
  };
  minDate!: string;
  @Output() submitted = new EventEmitter<boolean>();
  cottages!: ICottage[];
  chips!: MatChip;
  cottageChips: string[] = [];
  cottageServices: IAdditionalService[] = [];
  reservationServices: IAdditionalService[] = [];

  constructor(
    private _cottageQuickReservationService: CottageQuickReservationService,
    private _toastr: ToastrService,
    private _cottageService: CottageService,
    private _userService: UserService,
    private _route: ActivatedRoute,
    private _cottageAdditionalService: CottageAdditionalServicesService
  ) {}

  ngOnInit(): void {
    this.minDate = this.date();
    let cottageId = this._route.snapshot.paramMap.get('cottageId');
    this._userService.currentUser.subscribe((user) => {
      this._cottageService
        .getCottagesByCottageOwnerId(user.id)
        .subscribe((cottages) => {
          this.cottages = cottages;
          this.cottage= this.cottages.filter(c=>c.id==parseInt(cottageId!))[0];
        });
    });
    if(cottageId!=undefined){
      this._cottageAdditionalService
      .getAdditionalServicesByCottageId(parseInt(cottageId))
      .subscribe((tags) => {
        tags.forEach((t) => {
          this.cottageServices.push(t);
        });
      });
    }
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
      this.reservationServices.push({id:0,name:option.name,price:option.price});
    } else {
      this.reservationServices = this.reservationServices.filter(
        (e) => e !== option
      );
    }
  }

  addQuickReservation(): void {
    this._cottageQuickReservationService
      .addCottageQuickReservation(this.cottageQuickReservation, this.cottage)
      .subscribe(
        (quickReservation) => {
          //this.addReservationFormOpened = false;
          this._toastr.success('Reservation was successfully added.');
          this.reservationServices.forEach((tag) => {
            this._cottageAdditionalService
              .addAdditionalServiceForCottageQuickReservation(
                tag,
                quickReservation
              )
              .subscribe((service) => {});
          });
          this.submitted.emit();
        },
        (err) => {
          this._toastr.error(
            'Reservation term overlaps with another.',
            'Try a different date!'
          );
        }
      );
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
