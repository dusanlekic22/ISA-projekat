import {
  ISortType,
  sortTypes,
  emptySortType,
  priceSortType,
  gradeSortType,
} from './../../model/sortType';
import { IDateSpan } from 'src/app/model/dateSpan';
import {
  ICottageAvailability,
  emptyCottageAvailability,
} from './../../model/cottageReservation';
import { FormControl, Validators } from '@angular/forms';
import { CottageService } from './../cottage-owner/services/cottage.service';

import { Component, OnInit } from '@angular/core';
import { MatChip } from '@angular/material/chips';
import { ReservationService } from 'src/app/service/reservation.service';
import { ICottage, ICottagePage } from 'src/app/model/cottage';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { IBoat } from 'src/app/model/boat/boat';
import { BoatService } from '../boat-owner/services/boat.service';

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
  reservationCottage: ICottageAvailability = emptyCottageAvailability;

  searchBoatName!: string;
  searchBoatBeds!: string;
  boats!: IBoat[];
  startBoatDate!: Date;
  endBoatDate!: Date;
  optionsBoats: string[] = ['dare', 'leka'];
  bchips!: MatChip;
  bsortChips!: MatChip;
  boatChips: string[] = [];
  boatSortChips: ISortType[] = [];
  boatTimespan!: IDateSpan;
  bminDateString: string = 'adasd';
  startDateBoatString: string = '';
  endDateBoatString: any = '';
  openBoats: string = 'yes';
  boatSearch: boolean = false;
  boatBedCount: number = 0;
  boatPersonCount!: number;
  boatGrade!: number;
  bbeds: number[] = Array.from(Array(5).keys()).map((i) => (i += 1));
  sortListBoat: ISortType[] = sortTypes;
  boatSortBy!: ISortType;
  priceSortBoatType: ISortType = priceSortType;
  gradeSortBoatType: ISortType = gradeSortType;
  boatPage: number = 0;
  boatTotalElements: number = 30;
  paginatorBoat!: MatPaginator;
  // reservationBoat: IBoatAvailability = emptyBoatAvailability;

  constructor(
    private _cottageService: CottageService,
    private _boatService: BoatService,
    private _reservationService: ReservationService
  ) {}

  ngOnInit(): void {
    this.getCottages();
    this.getBoats();
    this.minDateString = this.date(new Date());
    this.startDateCottageString = this.date(new Date());
    this.bminDateString = this.date(new Date());
    this.startDateBoatString = this.date(new Date());
  }

  //cottage tab start
  getCottages() {
    this._cottageService
      .getCottagesPagination(this.cottagePage, this.cottageSortChips)
      .subscribe((data) => {
        this.cottages = data.content;
        this.cottageTotalElements = data.totalElements;
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

  activateCottageEnd() {
    this.endDateCottageString = this.startCottageDate;
  }
  //cottage tab end

  //boat tab start
  getBoats() {
    this._boatService
      .getBoatsPagination(this.boatPage, this.boatSortChips)
      .subscribe((data) => {
        this.boats = data.content;
        this.boatTotalElements = data.totalElements;
      });
  }

  toggleSelectionBoat(chip: MatChip, option: string) {
    let x: string[] = [];
    if (chip.toggleSelected()) {
      this.boatChips.push(option);
    } else {
      this.boatChips = this.boatChips.filter((e) => e !== option);
    }
  }
  toggleSelectionBoatSort(chip: MatChip, option: ISortType) {
    if (chip.toggleSelected()) {
      this.boatSortChips.push(option);
    } else {
      this.boatSortChips = this.boatSortChips.filter((e) => e !== option);
    }
    if (this.boatSearch === true) {
      this.availableBoats(this.boatPage);
    } else {
      this.getBoats();
    }
  }

  availableBoats(page: number) {
    // this.reservationBoat.sortBy = [];
    // this.boatSortChips.forEach((e) =>
    //   this.reservationBoat.sortBy.push(e)
    // );
    this.reservationCottage.sortBy.push(this.cottageSortBy);
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

  searchBoat() {
    this.boatSearch = true;
    this.availableBoats(0);
  }

  onChangeBoatPage(pe: PageEvent) {
    this.boatPage = pe.pageIndex;
    if (this.boatSearch === true) {
      this.availableBoats(this.boatPage);
    } else {
      this.getBoats();
    }
  }

  activateBoatEnd() {
    this.endDateBoatString = this.startBoatDate;
  }
  //boat tab end

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
}
