import { ICottageReservation } from './../../model/cottageReservation';
import { ICottage } from './../../model/cottage';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IAddress } from 'src/app/model/address';

@Component({
  selector: 'app-base-history-reservation',
  templateUrl: './base-history-reservation.component.html',
  styleUrls: ['./base-history-reservation.component.css'],
})
export class BaseHistoryReservationComponent implements OnInit {
  @Input() id!: string;
  @Input() cottageReservation!: ICottageReservation;
  @Input() openForReservation!: string;
  @Input() type!: number;

  constructor(public router: Router) {}

  ngOnInit(): void {}
}
