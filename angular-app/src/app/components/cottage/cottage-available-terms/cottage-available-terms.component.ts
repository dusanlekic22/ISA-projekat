import { Component, Input, OnInit } from '@angular/core';
import { CottageService } from 'src/app/pages/cottage-owner/services/cottage.service';
import { ToastrService } from 'ngx-toastr';
import { ICottage } from 'src/app/model/cottage';
import { IDateSpan } from 'src/app/model/dateSpan';

@Component({
  selector: 'app-cottage-available-terms',
  templateUrl: './cottage-available-terms.component.html',
  styleUrls: ['./cottage-available-terms.component.css'],
})
export class CottageAvailableTermsComponent implements OnInit {
  @Input() cottage!: ICottage;
  @Input() minDate!: string;

  availableStartDate!: Date;
  availableEndDate!: Date;

  availableSpanFormOpened: boolean = false;

  constructor(private _cottageService: CottageService,
    private _toastr: ToastrService,) {}

  ngOnInit(): void {
    this.availableStartDate = new Date();
    this.availableEndDate = new Date();
  }

  addDateSpan() {
    console.log(this.availableStartDate);
    this._cottageService
      .editAvailableTerms(this.cottage.id, {
        startDate: this.availableStartDate,
        endDate: this.availableEndDate,
      })
      .subscribe(
        (cottage) => {
          this.cottage = cottage;
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
    this.cottage.availableReservationDateSpan =
      this.cottage.availableReservationDateSpan.filter((term) => term != span);
    this._cottageService.editCottage(this.cottage).subscribe((cottage) => {
      this.cottage = cottage;
    });
  }

  openAvailableSpanForm() {
    this.availableSpanFormOpened = true;
  }

}
