import { Component, Input, OnInit } from '@angular/core';
import { BoatService } from 'src/app/pages/boat-owner/services/boat.service';
import { ToastrService } from 'ngx-toastr';
import { IDateSpan } from 'src/app/model/dateSpan';
import { IBoat } from 'src/app/model/boat/boat';

@Component({
  selector: 'app-boat-available-terms',
  templateUrl: './boat-available-terms.component.html',
  styleUrls: ['./boat-available-terms.component.css'],
})
export class BoatAvailableTermsComponent implements OnInit {
  @Input() boat!: IBoat;
  @Input() minDate!: string;

  availableStartDate!: Date;
  availableEndDate!: Date;

  availableSpanFormOpened: boolean = false;

  constructor(private _boatService: BoatService,
    private _toastr: ToastrService,) {}

  ngOnInit(): void {
    this.availableStartDate = new Date();
    this.availableEndDate = new Date();
  }

  addDateSpan() {
    console.log(this.availableStartDate);
    this._boatService
      .editAvailableTerms(this.boat.id, {
        startDate: this.availableStartDate,
        endDate: this.availableEndDate,
      })
      .subscribe(
        (boat) => {
          this.boat = boat;
          this.availableSpanFormOpened = false;
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
    this.boat.availableReservationDateSpan =
      this.boat.availableReservationDateSpan.filter((term) => term != span);
    this._boatService.editBoat(this.boat).subscribe((boat) => {
      this.boat = boat;
    });
  }
  
  openAvailableSpanForm() {
    this.availableSpanFormOpened = true;
  }

}
