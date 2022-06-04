import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IBoatReservation } from 'src/app/model/boat/boatReservation';

@Component({
  selector: 'app-base-active-boat-reservation',
  templateUrl: './base-active-boat-reservation.component.html',
  styleUrls: ['./base-active-boat-reservation.component.css'],
})
export class BaseActiveBoatReservationComponent implements OnInit {
  @Input() id!: string;
  @Input() boatReservation!: IBoatReservation;
  @Input() openForReservation!: string;
  @Input() type!: number;

  constructor(public router: Router) {}

  ngOnInit(): void {}
}
