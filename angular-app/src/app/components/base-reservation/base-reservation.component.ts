import { CottageService } from 'src/app/pages/cottage-owner/services/cottage.service';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { ICottage } from 'src/app/model/cottage';

@Component({
  selector: 'app-base-reservation',
  templateUrl: './base-reservation.component.html',
  styleUrls: ['./base-reservation.component.css'],
})
export class BaseReservationComponent implements OnInit {
  id!: string;
  startCottageDate: Date = new Date();
  endCottageDate: Date = new Date();
  minDate: Date = new Date();
  minDateString: string = '';
  startDateCottageString: string = '';
  endDateCottageString: any = '';
  cottage!: ICottage;

  constructor(
    private _route: ActivatedRoute,
    private _cottageService: CottageService
  ) {}

  ngOnInit(): void {
    this.minDateString = this.date(new Date());
    this.startDateCottageString = this.date(new Date());
    this._route.params.subscribe((data) => {
      this.id = data.id;
      this.startCottageDate = data.startDate;
      this.endCottageDate = data.endDate;
      if (this.endCottageDate !== null) {
        this.activateCottageEnd();
      }
      this._cottageService
        .getCottageById(parseInt(this.id))
        .subscribe((data) => {
          this.cottage = data;
          console.log(data);
        });
    });
  }

  date(min: Date): string {
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
    return x;
  }

  activateCottageEnd() {
    let x = '';
    x = this.startCottageDate.toString();
    this.endDateCottageString = this.startCottageDate;
  }
}
