import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IBoatReservation } from 'src/app/model/boat/boatReservation';

@Component({
  selector: 'app-base-history-boat-reservation',
  templateUrl: './base-history-boat-reservation.component.html',
  styleUrls: ['./base-history-boat-reservation.component.css'],
})
export class BaseHistoryBoatReservationComponent implements OnInit {
  @Input() id!: string;
  @Input() boatReservation!: IBoatReservation;
  @Input() openForReservation!: string;
  @Input() type!: number;

  constructor(public router: Router) {}

  ngOnInit(): void {}
}
