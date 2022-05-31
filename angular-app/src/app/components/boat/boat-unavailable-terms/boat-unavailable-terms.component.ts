import { Component, Input, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { IBoat } from 'src/app/model/boat/boat';
import { IDateSpan } from 'src/app/model/dateSpan';
import { BoatService } from 'src/app/pages/boat-owner/services/boat.service';

@Component({
  selector: 'app-boat-unavailable-terms',
  templateUrl: './boat-unavailable-terms.component.html',
  styleUrls: ['./boat-unavailable-terms.component.css']
})
export class BoatUnavailableTermsComponent implements OnInit {

  @Input() boat!: IBoat;
  @Input() minDate!: string;

  unavailableStartDate!: Date;
  unavailableEndDate!: Date;

  unavailableSpanFormOpened: boolean = false;

  constructor(private _boatService: BoatService,
    private _toastr: ToastrService,) {}

  ngOnInit(): void {
    this.unavailableStartDate = new Date();
    this.unavailableEndDate = new Date();
  }

  addDateSpan() {
    this._boatService
      .editUnavailableTerms(this.boat.id, {
        startDate: this.unavailableStartDate,
        endDate: this.unavailableEndDate,
      })
      .subscribe(
        (boat) => {
          this.boat = boat;
          this.unavailableSpanFormOpened = false;
        },
        (err) => {
          this._toastr.error(
            'Theres already an active reservation in this date span.',
            'Try a different date!'
          );
        }
      );
  }

  removeTerm(span: IDateSpan) {
    this.boat.unavailableReservationDateSpan =
      this.boat.unavailableReservationDateSpan.filter((term) => term != span);
    this._boatService.editBoat(this.boat).subscribe((boat) => {
      this.boat = boat;
    });
  }

  openUnavailableSpanForm() {
    this.unavailableSpanFormOpened = true;
  }
}
