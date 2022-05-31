import {
  ISortType,
  sortTypes,
  emptySortType,
  priceSortType,
  gradeSortType,
} from './../../model/sortType';
import { IDateSpan } from 'src/app/model/dateSpan';
import { ICottageAvailability } from './../../model/cottageReservation';
import { FormControl, Validators } from '@angular/forms';
import { CottageService } from './../cottage-owner/services/cottage.service';

import { Component, OnInit } from '@angular/core';
import { MatChip } from '@angular/material/chips';
import { ReservationService } from 'src/app/service/reservation.service';
import { ICottage, ICottagePage } from 'src/app/model/cottage';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent implements OnInit {
  searchCottageName!: string;
  searchCottageBeds!: string;
  cottages!: ICottage[];
  startCottageDate!: Date;
  endCottageDate!: Date;
  optionsCottages: string[] = ['dare', 'leka'];
  chips!: MatChip;
  sortChips!: MatChip;
  cottageChips: string[] = [];
  cottageSortChips: ISortType[] = [];
  cottageTimespan!: IDateSpan;
  minDate: Date = new Date();
  minDateString: string = 'adasd';
  startDateCottageString: string = '';
  endDateCottageString: any = '';
  openCottages: string = 'yes';
  cottageSearch: boolean = false;
  cottageBedCount: number = 0;
  cottagePersonCount!: number;
  cottageGrade!: number;
  beds: number[] = Array.from(Array(5).keys()).map((i) => (i += 1));
  sortListCottage: ISortType[] = sortTypes;
  cottageSortBy!: ISortType;
  priceSortCottageType: ISortType = priceSortType;
  gradeSortCottageType: ISortType = gradeSortType;
  cottagePage: number = 0;
  cottageTotalElements: number = 30;

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
    this.getCottages();
    this.minDateString = this.date(new Date());
    this.startDateCottageString = this.date(new Date());
  }

  getCottages() {
    this._cottageService
      .getCottagesPagination(this.cottagePage, this.cottageSortChips)
      .subscribe((data) => {
        this.cottages = data.content;
        this.cottageTotalElements = data.totalElements;
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
  toggleSelectionCottageSort(chip: MatChip, option: ISortType) {
    if (chip.toggleSelected()) {
      this.cottageSortChips.push(option);
    } else {
      this.cottageSortChips = this.cottageSortChips.filter((e) => e !== option);
    }
    console.log('chipssss', this.cottageSortChips);
    if (this.cottageSearch === true) {
      this.availableCottages(this.cottagePage);
    } else {
      this.getCottages();
    }
  }

  availableCottages(page: number) {
    this.reservationCottage.sortBy = [];
    this.cottageSortChips.forEach((e) =>
      this.reservationCottage.sortBy.push(e)
    );
    this.reservationCottage.sortBy.push(this.cottageSortBy);
    console.log('duzina', this.reservationCottage.sortBy.length);
    if (this.startCottageDate === this.endCottageDate) {
      this.reservationCottage.dateSpan.startDate = this.startCottageDate;
      this.reservationCottage.dateSpan.endDate = this.endCottageDate;
    } else {
      this.reservationCottage.dateSpan.startDate = this.startCottageDate;
      this.reservationCottage.dateSpan.endDate = this.endCottageDate;
    }

    this.reservationCottage.name = this.searchCottageName;
    this.reservationCottage.bedCapacity = this.cottageBedCount;
    this.reservationCottage.grade = this.cottageGrade;
    this._reservationService
      .getAvailableCottagesByTimeSpan(this.reservationCottage, page)
      .subscribe((data: any) => {
        console.log('podaci', data.content);
        this.cottages = data.content;
        this.cottagePage = page;
        this.cottageTotalElements = data.totalElements;
        if (
          this.startCottageDate !== undefined ||
          this.endCottageDate !== undefined ||
          this.startCottageDate !== '' ||
          this.endCottageDate !== ''
        ) {
          this.openCottages = 'no';
        }
      });
  }

  searchCottage() {
    this.cottageSearch = true;
    this.availableCottages(0);
  }

  onChangeCottagePage(pe: PageEvent) {
    this.cottagePage = pe.pageIndex;
    if (this.cottageSearch === true) {
      this.availableCottages(this.cottagePage);
    } else {
      this.getCottages();
    }
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
