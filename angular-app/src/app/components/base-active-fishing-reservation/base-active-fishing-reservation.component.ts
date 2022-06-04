import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IFishingReservation } from 'src/app/model/fishingReservation';

@Component({
  selector: 'app-base-active-fishing-reservation',
  templateUrl: './base-active-fishing-reservation.component.html',
  styleUrls: ['./base-active-fishing-reservation.component.css'],
})
export class BaseActiveFishingReservationComponent implements OnInit {
  @Input() id!: string;
  @Input() fishingReservation!: IFishingReservation;
  @Input() openForReservation!: string;
  @Input() type!: number;

  constructor(public router: Router) {}

  ngOnInit(): void {}
}
