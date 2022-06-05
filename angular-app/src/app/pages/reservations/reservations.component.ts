import { CottageReservationService } from 'src/app/pages/cottage-owner/services/cottage-reservation.service';
import { Component, OnInit } from '@angular/core';
import { MatChip } from '@angular/material/chips';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { ICottageReservation } from 'src/app/model/cottageReservation';
import { IDateSpan } from 'src/app/model/dateSpan';
import {
  initSortType,
  ISortType,
  sortReservationTypes,
} from 'src/app/model/sortType';
import { ActivatedRoute } from '@angular/router';
import { IBoatReservation } from 'src/app/model/boat/boatReservation';
import { BoatReservationService } from '../boat-owner/services/boat-reservation.service';
import { FishingReservationService } from 'src/app/service/fishingReservation.service';
import { IFishingReservation } from 'src/app/model/fishingReservation';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.css'],
})
export class ReservationsComponent implements OnInit {
  customerId!: number;
  reservationsCottage!: ICottageReservation[];
  openCottages: string = 'yes';
  sortListCottage: ISortType[] = sortReservationTypes;
  cottageSortBy: ISortType = initSortType;
  reservationsCottagePage: number = 0;
  reservationsCottageTotalElements: number = 30;
  paginatorReservationsCottage!: MatPaginator;
  reservationsCottageSearch: boolean = false;

  reservationsBoat!: IBoatReservation[];
  openBoats: string = 'yes';
  sortListBoat: ISortType[] = sortReservationTypes;
  boatSortBy: ISortType = initSortType;
  reservationsBoatPage: number = 0;
  reservationsBoatTotalElements: number = 30;
  paginatorReservationsBoat!: MatPaginator;
  reservationsBoatSearch: boolean = false;

  reservationsFishing!: IFishingReservation[];
  openFishings: string = 'yes';
  sortListFishing: ISortType[] = sortReservationTypes;
  fishingSortBy: ISortType = initSortType;
  reservationsFishingPage: number = 0;
  reservationsFishingTotalElements: number = 30;
  paginatorReservationsFishing!: MatPaginator;
  reservationsFishingSearch: boolean = false;

  reservationsActiveCottage!: ICottageReservation[];
  openActiveCottages: string = 'yes';
  cottageActiveSortBy: ISortType = initSortType;
  reservationsActiveCottagePage: number = 0;
  reservationsActiveCottageTotalElements: number = 30;
  paginatorReservationsActiveCottage!: MatPaginator;
  reservationsCottageActiveSearch: boolean = false;

  reservationsActiveBoat!: IBoatReservation[];
  openActiveBoats: string = 'yes';
  boatActiveSortBy: ISortType = initSortType;
  reservationsActiveBoatPage: number = 0;
  reservationsActiveBoatTotalElements: number = 30;
  paginatorReservationsActiveBoat!: MatPaginator;
  reservationsBoatActiveSearch: boolean = false;

  reservationsActiveFishing!: IFishingReservation[];
  openActiveFishings: string = 'yes';
  fishingActiveSortBy: ISortType = initSortType;
  reservationsActiveFishingPage: number = 0;
  reservationsActiveFishingTotalElements: number = 30;
  paginatorReservationsActiveFishing!: MatPaginator;
  reservationsFishingActiveSearch: boolean = false;

  constructor(
    private _reservationCottageService: CottageReservationService,
    private _reservationBoatService: BoatReservationService,
    private _reservationFishingService: FishingReservationService,
    private _route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this._route.params.subscribe((data) => {
      this.customerId = data.id;
      this.availableReservationCottage(0);
      this.availableReservationBoat(0);
      this.availableReservationFishing(0);
      this.availableReservationActiveCottage(0);
      this.availableReservationActiveBoat(0);
      this.availableReservationActiveFishing(0);
    });
  }
  // past  cottage reservations start
  availableReservationCottage(page: number) {
    this._reservationCottageService
      .getAvailableCottagesReservation(
        this.customerId,
        this.cottageSortBy,
        page
      )
      .subscribe((data: any) => {
        this.reservationsCottage = data.content;
        this.reservationsCottagePage = page;
        this.reservationsCottageTotalElements = data.totalElements;
        this.openCottages = 'no';
      });
  }

  searchReservationsCottage() {
    this.availableReservationCottage(0);
  }

  onChangeReservationsCottagePage(pe: PageEvent) {
    this.reservationsCottagePage = pe.pageIndex;
    this.availableReservationCottage(this.reservationsCottagePage);
  }
  // past cottage reservations end

  // past  boat reservations start
  availableReservationBoat(page: number) {
    this._reservationBoatService
      .getAvailableBoatsReservation(this.customerId, this.boatSortBy, page)
      .subscribe((data: any) => {
        this.reservationsBoat = data.content;
        this.reservationsBoatPage = page;
        this.reservationsBoatTotalElements = data.totalElements;
        this.openBoats = 'no';
      });
  }

  searchReservationsBoat() {
    this.availableReservationBoat(0);
  }

  onChangeReservationsBoatPage(pe: PageEvent) {
    this.reservationsBoatPage = pe.pageIndex;
    this.availableReservationBoat(this.reservationsBoatPage);
  }
  // past boat reservations end

  // past  fishing reservations start
  availableReservationFishing(page: number) {
    this._reservationFishingService
      .getAvailableFishingsReservation(
        this.customerId,
        this.fishingSortBy,
        page
      )
      .subscribe((data: any) => {
        this.reservationsFishing = data.content;
        this.reservationsFishingPage = page;
        this.reservationsFishingTotalElements = data.totalElements;
        this.openBoats = 'no';
      });
  }

  searchReservationsFishing() {
    this.availableReservationFishing(0);
  }

  onChangeReservationsFishingPage(pe: PageEvent) {
    this.reservationsFishingPage = pe.pageIndex;
    this.availableReservationFishing(this.reservationsFishingPage);
  }
  // past fishing reservations end

  //incoming cottage reservations start
  availableReservationActiveCottage(page: number) {
    this._reservationCottageService
      .getAvailableCottagesReservationIncoming(
        this.customerId,
        this.cottageActiveSortBy,
        page
      )
      .subscribe((data: any) => {
        this.reservationsActiveCottage = data.content;
        this.reservationsActiveCottagePage = page;
        this.reservationsActiveCottageTotalElements = data.totalElements;
      });
  }

  searchReservationsActiveCottage() {
    this.availableReservationActiveCottage(0);
  }

  onChangeReservationsActiveCottagePage(pe: PageEvent) {
    this.reservationsActiveCottagePage = pe.pageIndex;
    this.availableReservationActiveCottage(this.reservationsActiveCottagePage);
  }
  //incoming cottage reservations end

  //incoming boat reservations start
  availableReservationActiveBoat(page: number) {
    this._reservationBoatService
      .getAvailableBoatsReservationIncoming(
        this.customerId,
        this.boatActiveSortBy,
        page
      )
      .subscribe((data: any) => {
        this.reservationsActiveBoat = data.content;
        this.reservationsActiveBoatPage = page;
        this.reservationsActiveBoatTotalElements = data.totalElements;
      });
  }

  searchReservationsActiveBoat() {
    this.availableReservationActiveBoat(0);
  }

  onChangeReservationsActiveBoatPage(pe: PageEvent) {
    this.reservationsActiveBoatPage = pe.pageIndex;
    this.availableReservationActiveBoat(this.reservationsActiveBoatPage);
  }
  //incoming boat reservations end

  //incoming fishing reservations start
  availableReservationActiveFishing(page: number) {
    this._reservationFishingService
      .getAvailableFishingsReservationIncoming(
        this.customerId,
        this.fishingActiveSortBy,
        page
      )
      .subscribe((data: any) => {
        this.reservationsActiveFishing = data.content;
        this.reservationsActiveFishingPage = page;
        this.reservationsActiveFishingTotalElements = data.totalElements;
      });
  }

  searchReservationsActiveFishing() {
    this.availableReservationActiveFishing(0);
  }

  onChangeReservationsActiveFishingPage(pe: PageEvent) {
    this.reservationsActiveFishingPage = pe.pageIndex;
    this.availableReservationActiveFishing(this.reservationsActiveFishingPage);
  }
  //incoming fishing reservations end

  cancelFishingsRefresh(event: any) {
    this.availableReservationActiveFishing(0);
  }
  cancelBoatsRefresh(event: any) {
    this.availableReservationActiveBoat(0);
  }
  cancelCottagesRefresh(event: any) {
    this.availableReservationActiveCottage(0);
  }
}
