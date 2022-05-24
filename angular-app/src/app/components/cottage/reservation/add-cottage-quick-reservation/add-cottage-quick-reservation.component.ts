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
  @Input() cottageQuickReservation: ICottageQuickReservation={
    id: 0,
    duration: {
      startDate: new Date(),
      endDate: new Date(),
    },
    guestCapacity: 0,
    price: 0,
  };
  @Input() minDate!:string;
  @Output() submitted = new EventEmitter<boolean>();
  
  constructor(
    private _cottageQuickReservationService: CottageQuickReservationService,
    private _toastr: ToastrService,
    private _cottageService: CottageService,
  ) {}

  ngOnInit(): void {}

  addQuickReservation(): void {
    // this.cottageQuickReservation.duration.startDate = this.format(
    //   this.cottageQuickReservation.duration.startDate
    // );
    // this.cottageQuickReservation.duration.endDate = this.format(
    //   this.cottageQuickReservation.duration.endDate
    // );
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

  format(d: Date): Date {
    return new Date(
      d.getFullYear(),
      d.getMonth(),
      d.getDate(),
      d.getHours(),
      d.getMinutes() - d.getTimezoneOffset()
    );
  }
}
