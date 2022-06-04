import {
  emptyBoatAvailability,
  IBoatAvailability,
} from './../../model/boat/boatReservation';
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
import {
  emptyFishingCourseAvailability,
  IFishingCourse,
  IFishingCourseAvailability,
} from 'src/app/model/fishingCourse';
import { FishingCourseService } from 'src/app/service/fishingCourse.service';
import {
  emptyFishingTrainerAvailability,
  IFishingTrainer,
  IFishingTrainerAvailability,
} from 'src/app/model/fishingTrainer';
import { FishingTrainerService } from 'src/app/service/fishingTrainer.service';

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
  reservationBoat: IBoatAvailability = emptyBoatAvailability;

  searchFishingCourseName!: string;
  searchFishingCourseCapacity!: string;
  fishingCourses!: IFishingCourse[];
  startFishingCourseDate!: Date;
  endFishingCourseDate!: Date;
  optionsFishingCourses: string[] = ['dare', 'leka'];
  fchips!: MatChip;
  fsortChips!: MatChip;
  fishingCourseChips: string[] = [];
  fishingCourseSortChips: ISortType[] = [];
  fishingCourseTimespan!: IDateSpan;
  fminDateString: string = 'adasd';
  startDateFishingCourseString: string = '';
  endDateFishingCourseString: any = '';
  openFishingCourses: string = 'yes';
  fishingCourseSearch: boolean = false;
  fishingCourseBedCount: number = 0;
  fishingCoursePersonCount!: number;
  fishingCourseGrade!: number;
  fbeds: number[] = Array.from(Array(5).keys()).map((i) => (i += 1));
  sortListFishingCourse: ISortType[] = sortTypes;
  fishingCourseSortBy!: ISortType;
  priceSortFishingCourseType: ISortType = priceSortType;
  gradeSortFishingCourseType: ISortType = gradeSortType;
  fishingCoursePage: number = 0;
  fishingCourseTotalElements: number = 30;
  paginatorFishingCourse!: MatPaginator;
  reservationFishingCourse: IFishingCourseAvailability =
    emptyFishingCourseAvailability;

  searchFishingTrainerName!: string;
  fishingTrainers!: IFishingTrainer[];
  startFishingTrainerDate!: Date;
  endFishingTrainerDate!: Date;
  // optionsFishingTrainer: string[] = ['dare', 'leka'];
  tchips!: MatChip;
  tsortChips!: MatChip;
  fishingTrainerChips: string[] = [];
  fishingTrainerSortChips: ISortType[] = [];
  fishingTrainerTimespan!: IDateSpan;
  tminDateString: string = 'adasd';
  startDateFishingTrainerString: string = '';
  endDateFishingTrainerString: any = '';
  openFishingTrainers: string = 'yes';
  fishingTrainerSearch: boolean = false;
  fishingTrainerGrade!: number;
  sortListFishingTraner: ISortType[] = sortTypes;
  fishingTrainerSortBy!: ISortType;
  gradeSortFishingTrainerType: ISortType = gradeSortType;
  fishingTrainerPage: number = 0;
  fishingTrainerTotalElements: number = 30;
  paginatorFishingTrainer!: MatPaginator;
  reservationFishingTrainer: IFishingTrainerAvailability =
    emptyFishingTrainerAvailability;
  allFishingTrainers: IFishingTrainer[] = [];
  fishingTrainerId: number = 0;

  constructor(
    private _cottageService: CottageService,
    private _boatService: BoatService,
    private _fishingCourseService: FishingCourseService,
    private _fishingTrainerService: FishingTrainerService,
    private _reservationService: ReservationService
  ) {}

  ngOnInit(): void {
    this.getCottages();
    this.getBoats();
    this.getAllFishingTrainers();
    this.getFishingCourses();
    this.getFishingTrainers();
    this.minDateString = this.date(new Date());
    this.startDateCottageString = this.date(new Date());
    this.bminDateString = this.date(new Date());
    this.startDateBoatString = this.date(new Date());
    this.startDateFishingCourseString = this.date(new Date());
  }

  getAllFishingTrainers() {
    this._fishingTrainerService
      .getAllFishingTrainers()
      .subscribe(
        (allFishingTrainers) => (this.allFishingTrainers = allFishingTrainers)
      );
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
    this.reservationBoat.sortBy = [];
    this.boatSortChips.forEach((e) => this.reservationBoat.sortBy.push(e));
    this.reservationBoat.sortBy.push(this.boatSortBy);
    if (this.startBoatDate === this.endBoatDate) {
      this.reservationBoat.dateSpan.startDate = this.startBoatDate;
      this.reservationBoat.dateSpan.endDate = this.endBoatDate;
    } else {
      this.reservationBoat.dateSpan.startDate = this.startBoatDate;
      this.reservationBoat.dateSpan.endDate = this.endBoatDate;
    }
    this.reservationBoat.name = this.searchBoatName;
    this.reservationBoat.bedCapacity = this.boatBedCount;
    this.reservationBoat.grade = this.boatGrade;
    this._reservationService
      .getAvailableBoatsByTimeSpan(this.reservationBoat, page)
      .subscribe((data: any) => {
        this.boats = data.content;
        this.boatPage = page;
        this.boatTotalElements = data.totalElements;
        if (
          this.startBoatDate !== undefined ||
          this.endBoatDate !== undefined ||
          this.startBoatDate !== '' ||
          this.endBoatDate !== ''
        ) {
          this.openBoats = 'no';
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

  //fishing course tab start
  getFishingCourses() {
    this._fishingCourseService
      .getFishingCoursesPagination(
        this.fishingCoursePage,
        this.fishingCourseSortChips
      )
      .subscribe((data) => {
        this.fishingCourses = data.content;
        this.fishingCourseTotalElements = data.totalElements;
      });
  }

  toggleSelectionFishingCourse(chip: MatChip, option: string) {
    let x: string[] = [];
    if (chip.toggleSelected()) {
      this.fishingCourseChips.push(option);
    } else {
      this.fishingCourseChips = this.fishingCourseChips.filter(
        (e) => e !== option
      );
    }
  }
  toggleSelectionFishingCourseSort(chip: MatChip, option: ISortType) {
    if (chip.toggleSelected()) {
      this.fishingCourseSortChips.push(option);
    } else {
      this.fishingCourseSortChips = this.fishingCourseSortChips.filter(
        (e) => e !== option
      );
    }
    if (this.fishingCourseSearch === true) {
      this.availableFishingCourses(this.fishingCoursePage);
    } else {
      this.getFishingCourses();
    }
  }

  availableFishingCourses(page: number) {
    this.reservationFishingCourse.sortBy = [];
    this.fishingCourseSortChips.forEach((e) =>
      this.reservationFishingCourse.sortBy.push(e)
    );
    this.reservationFishingCourse.sortBy.push(this.fishingCourseSortBy);
    if (this.startFishingCourseDate === this.endFishingCourseDate) {
      this.reservationFishingCourse.dateSpan.startDate =
        this.startFishingCourseDate;
      this.reservationFishingCourse.dateSpan.endDate =
        this.endFishingCourseDate;
    } else {
      this.reservationFishingCourse.dateSpan.startDate =
        this.startFishingCourseDate;
      this.reservationFishingCourse.dateSpan.endDate =
        this.endFishingCourseDate;
    }
    this.reservationFishingCourse.name = this.searchFishingCourseName;
    this.reservationFishingCourse.bedCapacity = this.fishingCourseBedCount;
    this.reservationFishingCourse.grade = this.fishingCourseGrade;
    this._reservationService
      .getAvailableFishingCoursesByTimeSpan(this.reservationFishingCourse, page)
      .subscribe((data: any) => {
        this.fishingCourses = data.content;
        this.fishingCoursePage = page;
        this.fishingCourseTotalElements = data.totalElements;
        if (
          this.startFishingCourseDate !== undefined ||
          this.endFishingCourseDate !== undefined ||
          this.startFishingCourseDate !== '' ||
          this.endFishingCourseDate !== ''
        ) {
          this.openFishingCourses = 'no';
        }
      });
  }

  searchFishingCourse() {
    this.fishingCourseSearch = true;
    this.availableFishingCourses(0);
  }

  onChangeFishingCoursePage(pe: PageEvent) {
    this.fishingCoursePage = pe.pageIndex;
    if (this.fishingCourseSearch === true) {
      this.availableFishingCourses(this.fishingCoursePage);
    } else {
      this.getFishingCourses();
    }
  }

  activateFishingCourseEnd() {
    this.endDateFishingCourseString = this.startFishingCourseDate;
  }
  //fishing course tab end

  //fishing trainer tab start
  getFishingTrainers() {
    this._fishingTrainerService
      .getFishingTrainersPagination(
        this.fishingTrainerPage,
        this.fishingTrainerSortChips
      )
      .subscribe((data) => {
        this.fishingTrainers = data.content;
        this.fishingTrainerTotalElements = data.totalElements;
      });
  }

  toggleSelectionFishingTrainer(chip: MatChip, option: string) {
    let x: string[] = [];
    if (chip.toggleSelected()) {
      this.fishingTrainerChips.push(option);
    } else {
      this.fishingTrainerChips = this.fishingTrainerChips.filter(
        (e) => e !== option
      );
    }
  }
  toggleSelectionFishingTrainerSort(chip: MatChip, option: ISortType) {
    if (chip.toggleSelected()) {
      this.fishingTrainerSortChips.push(option);
    } else {
      this.fishingTrainerSortChips = this.fishingTrainerSortChips.filter(
        (e) => e !== option
      );
    }
    if (this.fishingTrainerSearch === true) {
      this.availableFishingTrainers(this.fishingTrainerPage);
    } else {
      this.getFishingTrainers();
    }
  }

  availableFishingTrainers(page: number) {
    this.reservationFishingTrainer.sortBy = [];
    this.fishingTrainerSortChips.forEach((e) =>
      this.reservationFishingTrainer.sortBy.push(e)
    );
    this.reservationFishingTrainer.sortBy.push(this.fishingTrainerSortBy);
    if (this.startFishingTrainerDate === this.endFishingTrainerDate) {
      this.reservationFishingTrainer.dateSpan.startDate =
        this.startFishingTrainerDate;
      this.reservationFishingTrainer.dateSpan.endDate =
        this.endFishingTrainerDate;
    } else {
      this.reservationFishingTrainer.dateSpan.startDate =
        this.startFishingTrainerDate;
      this.reservationFishingTrainer.dateSpan.endDate =
        this.endFishingTrainerDate;
    }
    this.reservationFishingTrainer.name = this.searchFishingTrainerName;
    this.reservationFishingTrainer.grade = this.fishingTrainerGrade;
    this._reservationService
      .getAvailableFishingTrainersByTimeSpan(
        this.reservationFishingTrainer,
        page
      )
      .subscribe((data: any) => {
        this.fishingTrainers = data.content;
        this.fishingTrainerPage = page;
        this.fishingTrainerTotalElements = data.totalElements;
        if (
          this.startFishingTrainerDate !== undefined ||
          this.endFishingTrainerDate !== undefined ||
          this.startFishingTrainerDate !== '' ||
          this.endFishingTrainerDate !== ''
        ) {
          this.openFishingTrainers = 'no';
        }
      });
  }

  searchFishingTrainer() {
    this.fishingCourseSearch = true;
    this.availableFishingTrainers(0);
  }

  onChangeFishingTrainerPage(pe: PageEvent) {
    this.fishingTrainerPage = pe.pageIndex;
    if (this.fishingTrainerSearch === true) {
      this.availableFishingTrainers(this.fishingTrainerPage);
    } else {
      this.getFishingTrainers();
    }
  }

  activateFishingTrainerEnd() {
    this.endDateFishingTrainerString = this.startFishingTrainerDate;
  }
  //fishing trainer tab end

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
