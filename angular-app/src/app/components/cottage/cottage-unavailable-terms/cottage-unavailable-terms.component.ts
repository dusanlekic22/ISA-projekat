import { Component, Input, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ICottage } from 'src/app/model/cottage';
import { IDateSpan } from 'src/app/model/dateSpan';
import { CottageService } from 'src/app/pages/cottage-owner/services/cottage.service';

@Component({
  selector: 'app-cottage-unavailable-terms',
  templateUrl: './cottage-unavailable-terms.component.html',
  styleUrls: ['./cottage-unavailable-terms.component.css']
})
export class CottageUnavailableTermsComponent implements OnInit {

  @Input() cottage!: ICottage;
  @Input() minDate!: string;

  unavailableStartDate!: Date;
  unavailableEndDate!: Date;

  unavailableSpanFormOpened: boolean = false;

  constructor(private _cottageService: CottageService,
    private _toastr: ToastrService,) {}

  ngOnInit(): void {
    this.unavailableStartDate = new Date();
    this.unavailableEndDate = new Date();
  }

  addDateSpan() {
    this._cottageService
      .editUnavailableTerms(this.cottage.id, {
        startDate: this.unavailableStartDate,
        endDate: this.unavailableEndDate,
      })
      .subscribe(
        (cottage) => {
          this.cottage = cottage;
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
    this.cottage.unavailableReservationDateSpan =
      this.cottage.unavailableReservationDateSpan.filter((term) => term != span);
    this._cottageService.editCottage(this.cottage).subscribe((cottage) => {
      this.cottage = cottage;
    });
  }

  openUnavailableSpanForm() {
    this.unavailableSpanFormOpened = true;
  }

}
