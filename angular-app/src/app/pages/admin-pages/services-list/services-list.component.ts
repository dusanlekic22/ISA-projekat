import { Component, OnInit } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { IBoat } from 'src/app/model/boat/boat';
import { ICottage } from 'src/app/model/cottage';
import { emptySortType } from 'src/app/model/sortType';
import { FishingCourseService } from 'src/app/service/fishingCourse.service';
import { ReservationService } from 'src/app/service/reservation.service';
import { BoatService } from '../../boat-owner/services/boat.service';
import { CottageService } from '../../cottage-owner/services/cottage.service';
import { IFishingCourse } from './../../../model/fishingCourse';

@Component({
  selector: 'app-services-list',
  templateUrl: './services-list.component.html',
  styleUrls: ['./services-list.component.css'],
})
export class ServicesListComponent implements OnInit {
  cottages!: ICottage[];
  cottageSearch: boolean = false;
  cottagePage: number = 0;
  cottageTotalElements: number = 30;
  paginatorCottage!: MatPaginator;
  search: string = '';

  boats!: IBoat[];
  boatSearch: boolean = false;
  boatBedCount: number = 0;
  boatPage: number = 0;
  boatTotalElements: number = 30;
  paginatorBoat!: MatPaginator;
  searchB: string = '';

  fishingCourses!: IFishingCourse[];
  fishingCourseSearch: boolean = false;
  fishingCourseBedCount: number = 0;
  fishingCoursePage: number = 0;
  fishingCourseTotalElements: number = 30;
  paginatorFishingCourse!: MatPaginator;
  searchF: string = '';

  constructor(
    private _cottageService: CottageService,
    private _boatService: BoatService,
    private _fishingCourseService: FishingCourseService,
    private _reservationService: ReservationService,
  ) {}

  ngOnInit(): void {
    this.getCottages();
    this.getBoats();
    this.getFishingCourses();
  }

  getCottages() {
    this._cottageService
      .getCottagesPaginationAdmin(this.cottagePage, [emptySortType])
      .subscribe((data) => {
        this.cottages = data.content;
        this.cottageTotalElements = data.totalElements;
      });
  }

  searchCottages(page: number) {
    this._reservationService
      .getSearchCottages(this.search, page)
      .subscribe((data: any) => {
        this.cottages = data.content;
        this.cottagePage = page;
        this.cottageTotalElements = data.totalElements;
      });
  }

  searchCottage() {
    if (this.search == '') {
      this.cottageSearch = false;
      this.getCottages();
      return;
    }
    this.cottageSearch = true;
    this.searchCottages(0);
  }

  onChangeCottagePage(pe: PageEvent) {
    this.cottagePage = pe.pageIndex;
    if (this.cottageSearch === true) {
      this.searchCottages(this.cottagePage);
    } else {
      this.getCottages();
    }
  }

  //boat tab start

  getBoats() {
    this._boatService
      .getBoatsPaginationAdmin(this.boatPage, [emptySortType])
      .subscribe((data) => {
        this.boats = data.content;
        this.boatTotalElements = data.totalElements;
      });
  }

  searchBoats(page: number) {
    this._reservationService
      .getSearchBoats(this.searchB, page)
      .subscribe((data: any) => {
        this.boats = data.content;
        this.boatPage = page;
        this.boatTotalElements = data.totalElements;
      });
  }

  searchBoat() {
    if (this.searchB == '') {
      this.boatSearch = false;
      this.getBoats();
      return;
    }
    this.boatSearch = true;
    this.searchBoats(0);
  }

  onChangeBoatPage(pe: PageEvent) {
    this.boatPage = pe.pageIndex;
    if (this.boatSearch === true) {
      this.searchBoats(this.boatPage);
    } else {
      this.getBoats();
    }
  }

  //fishing tab start

  getFishingCourses() {
    this._fishingCourseService
      .getFishingCoursesPaginationAdmin(this.fishingCoursePage, [emptySortType])
      .subscribe((data) => {
        this.fishingCourses = data.content;
        this.fishingCourseTotalElements = data.totalElements;
      });
  }

  searchFishingCourses(page: number) {
    this._reservationService
      .getSearchFishingCourses(this.searchF, page)
      .subscribe((data: any) => {
        this.fishingCourses = data.content;
        this.fishingCoursePage = page;
        this.fishingCourseTotalElements = data.totalElements;
      });
  }

  searchFishingCourse() {
    if (this.searchF == '') {
      this.fishingCourseSearch = false;
      this.getFishingCourses();
      return;
    }
    this.fishingCourseSearch = true;
    this.searchFishingCourses(0);
  }

  onChangeFishingCoursePage(pe: PageEvent) {
    this.fishingCoursePage = pe.pageIndex;
    if (this.fishingCourseSearch === true) {
      this.searchFishingCourses(this.fishingCoursePage);
    } else {
      this.getFishingCourses();
    }
  }

  getAddress(service: any) {
    if (!service.address) return 'none';
    return (
      service.address.country +
      ', ' +
      service.address.city +
      ', ' +
      service.address.street
    );
  }

  deleteFishingCourse(id: number) {
    if (confirm('Do you want to delete this service?')) {
      this._fishingCourseService.deleteFishingCourse(id).subscribe(() => {
        this.getFishingCourses();
      });
    }
  }

  deleteCottage(id: number) {
    if (confirm('Do you want to delete this service?')) {
      this._cottageService.deleteCottage(id).subscribe(() => {
        this.getCottages();
      });
    }
  }

  deleteBoat(id: number) {
    if (confirm('Do you want to delete this service?')) {
      this._boatService.deleteBoat(id).subscribe(() => {
        this.getBoats();
      });
    }
  }
}
