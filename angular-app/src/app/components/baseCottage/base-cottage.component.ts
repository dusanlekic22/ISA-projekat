import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { IAddress } from 'src/app/model/address';
@Component({
  selector: 'app-base-cottage',
  templateUrl: './base-cottage.component.html',
  styleUrls: ['./base-cottage.component.css'],
})
export class BaseCottageComponent implements OnInit {
  @Input() id!: string;
  @Input() name!: string;
  @Input() price!: string;
  @Input() openForReservation!: string;
  @Input() address!: IAddress;
  @Input() startDate!: Date;
  @Input() endDate!: Date;
  @Input() promoDesc!: string;
  @Input() grade!: number;
  constructor(public router: Router) {}

  ngOnInit(): void {}

  book() {}
}
