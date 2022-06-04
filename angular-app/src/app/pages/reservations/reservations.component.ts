import { CottageReservationService } from 'src/app/pages/cottage-owner/services/cottage-reservation.service';
import { Component, OnInit } from '@angular/core';
import { MatChip } from '@angular/material/chips';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { ICottageReservation } from 'src/app/model/cottageReservation';
import { IDateSpan } from 'src/app/model/dateSpan';
import { ISortType, sortReservationTypes } from 'src/app/model/sortType';
import { ActivatedRoute } from '@angular/router';

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
  cottageSortBy: ISortType = {
    text: '',
    direction: '',
    field: '',
  };
  reservationsCottagePage: number = 0;
  reservationsCottageTotalElements: number = 30;
  paginatorReservationsCottage!: MatPaginator;
  reservationsCottageSearch: boolean = false;

  reservationsActiveCottage!: ICottageReservation[];
  openActiveCottages: string = 'yes';
  cottageActiveSortBy: ISortType = {
    text: '',
    direction: '',
    field: '',
  };
  reservationsActiveCottagePage: number = 0;
  reservationsActiveCottageTotalElements: number = 30;
  paginatorReservationsActiveCottage!: MatPaginator;
  reservationsCottageActiveSearch: boolean = false;

  constructor(
    private _reservationCottageService: CottageReservationService,
    private _route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this._route.params.subscribe((data) => {
      this.customerId = data.id;
      this.availableReservationCottage(0);
      this.availableReservationActiveCottage(0);
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
}
