import { UserService } from './../../../../service/user.service';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CottageQuickReservationService } from 'src/app/pages/cottage-owner/services/cottage-quick-reservation.service';
import { ToastrService } from 'ngx-toastr';
import { CottageService } from 'src/app/pages/cottage-owner/services/cottage.service';
import { ICottage } from 'src/app/model/cottage';
import { ICottageQuickReservation } from 'src/app/model/cottageQuickReservation';

@Component({
  selector: 'app-add-cottage-quick-reservation',
  templateUrl: './add-cottage-quick-reservation.component.html',
  styleUrls: ['../../../../pages/cottage-owner/cottage-style.css'],
})
export class AddCottageQuickReservationComponent implements OnInit {
  @Input() cottage!: ICottage;
  @Input() cottageQuickReservation: ICottageQuickReservation = {
    id: 0,
    duration: {
      startDate: new Date(),
      endDate: new Date(),
    },
    guestCapacity: 0,
    price: 0,
  };
  minDate!: string;
  @Output() submitted = new EventEmitter<boolean>();
  cottages!: ICottage[];

  constructor(
    private _cottageQuickReservationService: CottageQuickReservationService,
    private _toastr: ToastrService,
    private _cottageService: CottageService,
    private _userService: UserService
  ) {}

  ngOnInit(): void {
    this.minDate = this.date();
    this._userService.currentUser.subscribe((user) => {
      this._cottageService
        .getCottagesByCottageOwnerId(user.id)
        .subscribe((cottages) => {
          this.cottages = cottages;
        });
    });
  }

  addQuickReservation(): void {
    this._cottageQuickReservationService
      .addCottageQuickReservation(this.cottageQuickReservation, this.cottage)
      .subscribe(
        (quickReservation) => {
          //this.addReservationFormOpened = false;
          this._toastr.success('Reservation was successfully added.');
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
