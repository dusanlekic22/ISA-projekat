import { FormControl, Validators } from '@angular/forms';
import { CottageService } from './../cottage-owner/services/cottage.service';
import { IDateTimeSpan } from './../../model/date-time-span';
import { Component, OnInit } from '@angular/core';
import { MatChip } from '@angular/material/chips';
import { ReservationService } from 'src/app/service/reservation.service';
import { ICottage } from 'src/app/model/cottage';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent implements OnInit {
  searchCottageName!: string;
  searchCottageBeds!: string;
  cottages!: ICottage[];
  startCottageDate: Date = new Date();
  endCottageDate: Date = new Date();
  optionsCottages: string[] = ['dare', 'leka'];
  chips!: MatChip;
  cottageChips: string[] = [];
  cottageTimespan!: IDateTimeSpan;
  minDate: Date = new Date();
  minDateString: string = '';
  startDateCottageString: string = '';
  endDateCottageString: any = '';
  openCottages: string = 'yes';

  constructor(
    private _cottageService: CottageService,
    private _reservationService: ReservationService
  ) {}

  ngOnInit(): void {
    this._cottageService.getCottages().subscribe((data) => {
      this.cottages = data;
      console.log('evo data', data);
    });
    this.minDateString = this.date(new Date());
    this.startDateCottageString = this.date(new Date());
  }
  toggleSelectionCottage(chip: MatChip, option: string) {
    let x: string[] = [];
    if (chip.toggleSelected()) {
      this.cottageChips.push(option);
    } else {
      this.cottageChips = this.cottageChips.filter((e) => e !== option);
    }
  }

  availableCottages() {
    this._reservationService
      .getAvailableCottagesByTimeSpan({
        startDate: this.startCottageDate,
        endDate: this.endCottageDate,
      })
      .subscribe((data) => {
        this.cottages = data;
        this.openCottages = 'no';
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
