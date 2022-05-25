import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-base-reservation',
  templateUrl: './base-reservation.component.html',
  styleUrls: ['./base-reservation.component.css'],
})
export class BaseReservationComponent implements OnInit {
  startCottageDate: Date = new Date();
  endCottageDate: Date = new Date();
  minDate: Date = new Date();
  minDateString: string = '';
  startDateCottageString: string = '';
  endDateCottageString: any = '';

  constructor() {}

  ngOnInit(): void {
    this.minDateString = this.date(new Date());
    this.startDateCottageString = this.date(new Date());
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
