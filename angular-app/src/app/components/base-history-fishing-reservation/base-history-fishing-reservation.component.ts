import { IFishingReservation } from 'src/app/model/fishingReservation';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-base-history-fishing-reservation',
  templateUrl: './base-history-fishing-reservation.component.html',
  styleUrls: ['./base-history-fishing-reservation.component.css'],
})
export class BaseHistoryFishingReservationComponent implements OnInit {
  @Input() id!: string;
  @Input() fishingReservation!: IFishingReservation;
  @Input() openForReservation!: string;
  @Input() type!: number;

  constructor(public router: Router) {}

  ngOnInit(): void {}
}
