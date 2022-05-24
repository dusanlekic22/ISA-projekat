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

  constructor(
    private _cottageService: CottageService,
    private _reservationService: ReservationService
  ) {}

  ngOnInit(): void {
    this._cottageService.getCottages().subscribe((data) => {
      this.cottages = data;
      console.log('evo data', data);
    });
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
    console.log(this.startCottageDate);
    this.cottageTimespan.startDate = this.startCottageDate;
    this.cottageTimespan.endDate = this.endCottageDate;
    this._reservationService
      .getAvailableCottagesByTimeSpan(this.cottageTimespan)
      .subscribe((data) => {
        console.log('podaci', data);
        this.cottages = data;
      });
  }
}
