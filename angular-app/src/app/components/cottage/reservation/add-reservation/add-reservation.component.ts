import { initCottage } from './../../../../model/cottage';
import { emptyCottageReservation } from './../../../../model/cottageReservation';
import { IAdditionalService } from './../../../../model/additionalService';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CottageReservationService } from 'src/app/pages/cottage-owner/services/cottage-reservation.service';
import { ToastrService } from 'ngx-toastr';
import { ICustomer } from 'src/app/model/customer';
import { ICottageReservation } from 'src/app/model/cottageReservation';
import { ICottage } from 'src/app/model/cottage';
import { CottageService } from 'src/app/pages/cottage-owner/services/cottage.service';
import { UserService } from 'src/app/service/user.service';
import { ActivatedRoute } from '@angular/router';
import { MatChip } from '@angular/material/chips';
import { CottageAdditionalServicesService } from 'src/app/pages/cottage-owner/services/cottage-additional-services.service';

@Component({
  selector: 'app-add-reservation',
  templateUrl: './add-reservation.component.html',
  styleUrls: ['./add-reservation.component.css'],
})
export class AddReservationComponent implements OnInit {
  @Input() minDate!: string;
  @Output() submitted = new EventEmitter<boolean>();
  cottage!: ICottage;
  eligibleCustomers!: ICustomer[];
  @Input() customer!: ICustomer;
  cottages!: ICottage[];
  chips!: MatChip;
  cottageChips: string[] = [];
  cottageServices: IAdditionalService[] = [];
  reservationServices: IAdditionalService[] = [];

  cottageReservation: ICottageReservation = emptyCottageReservation;

  constructor(
    private _cottageReservationService: CottageReservationService,
    private _toastr: ToastrService,
    private _userService: UserService,
    private _cottageService: CottageService,
    private _route: ActivatedRoute,
    private _cottageAdditionalService: CottageAdditionalServicesService
  ) {}

  ngOnInit(): void {
    this._cottageReservationService
      .getCustomerHasReservationNow()
      .subscribe((customers) => {
        this.eligibleCustomers = customers;
      });
    let cottageId = this._route.snapshot.paramMap.get('cottageId');
    this._userService.currentUser.subscribe((user) => {
      this._cottageService
        .getCottagesByCottageOwnerId(user.id)
        .subscribe((cottages) => {
          this.cottages = cottages;
          if (cottageId != undefined)
            this.cottage = this.cottages.filter(
              (c) => c.id == parseInt(cottageId!)
            )[0];
        });
    });
    if(cottageId!=undefined){
      this.getChips(parseInt(cottageId));
    }
  }

  setCustomer(id: number) {
    this.customer = this.eligibleCustomers.filter((c) => c.id == id)[0];
  }

  getChips(id:number) {
    this._cottageAdditionalService
      .getAdditionalServicesByCottageId(id)
      .subscribe((tags) => {
        tags.forEach((t) => {
          if (this.cottageServices.length < 1 ) {
            this.cottageServices.push(t);
          }
          else if(this.cottageServices.some(e => e.name !== t.name)){
            this.cottageServices.push(t);
          }
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
    this.cottageReservation.customer = this.customer;
    this._cottageReservationService
      .addCottageReservation(this.cottageReservation, this.cottage)
      .subscribe(
        (reservation) => {
          this._toastr.success('Reservation was successfully added.');
          this.reservationServices.forEach((tag) => {
            this._cottageAdditionalService
              .addAdditionalServiceForCottageReservation(tag, reservation)
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
}
