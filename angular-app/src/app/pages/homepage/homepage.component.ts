import { ISortType, sortTypes } from './../../model/sortType';
import { IDateSpan } from 'src/app/model/dateSpan';
import { ICottageAvailability } from './../../model/cottageReservation';
import { FormControl, Validators } from '@angular/forms';
import { CottageService } from './../cottage-owner/services/cottage.service';

import { Component, OnInit } from '@angular/core';
import { MatChip } from '@angular/material/chips';
import { ReservationService } from 'src/app/service/reservation.service';
import { ICottage, ICottagePage } from 'src/app/model/cottage';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent implements OnInit {
  searchCottageName!: string;
  searchCottageBeds!: string;
  cottages!: ICottage[];
  startCottageDate: any = null;
  endCottageDate: any = null;
  optionsCottages: string[] = ['dare', 'leka'];
  chips!: MatChip;
  sortChips!: MatChip;
  cottageChips: string[] = [];
  cottageSortChips: string[] = [];
  cottageTimespan!: IDateSpan;
  minDate: Date = new Date();
  minDateString: string = '';
  startDateCottageString: string = '';
  endDateCottageString: any = '';
  openCottages: string = 'yes';
  cottageBedCount!: number;
  cottagePersonCount!: number;
  cottageGrade: number = -1;
  beds: number[] = Array.from(Array(5).keys()).map((i) => (i += 1));
  sortListCottage: ISortType[] = sortTypes;

  paginatorCottage!: MatPaginator;
  reservationCottage: ICottageAvailability = {
    name: '',
    dateSpan: {
      startDate: new Date(),
      endDate: new Date(),
    },
    bedCapacity: 0,
    price: 0,
    grade: -1,
    longitude: 0,
    latitude: 0,
    sortBy: [],
    freeAdditionalServices: [],
  };

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
  toggleSelectionCottageSort(chip: MatChip, option: string) {
    let x: string[] = [];
    if (chip.toggleSelected()) {
      this.cottageSortChips.push(option);
    } else {
      this.cottageSortChips = this.cottageSortChips.filter((e) => e !== option);
    }
  }

  availableCottages() {
    if (this.startCottageDate === this.endCottageDate) {
      this.reservationCottage.dateSpan.startDate = this.startCottageDate;
      this.reservationCottage.dateSpan.endDate = this.endCottageDate;
    } else {
      this.reservationCottage.dateSpan.startDate = this.startCottageDate;
      this.reservationCottage.dateSpan.endDate = this.endCottageDate;
    }
    this._reservationService
      .getAvailableCottagesByTimeSpan(this.reservationCottage)
      .subscribe((data: any) => {
        console.log('podaci', data.content);
        this.cottages = data.content;
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
