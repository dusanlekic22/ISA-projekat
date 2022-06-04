import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ICottageReservation } from 'src/app/model/cottageReservation';

@Component({
  selector: 'app-base-active-reservation-history',
  templateUrl: './base-active-reservation-history.component.html',
  styleUrls: ['./base-active-reservation-history.component.css'],
})
export class BaseActiveReservationHistoryComponent implements OnInit {
  @Input() id!: string;
  @Input() cottageReservation!: ICottageReservation;
  @Input() openForReservation!: string;
  @Input() type!: number;

  constructor(public router: Router) {}

  ngOnInit(): void {}
}
