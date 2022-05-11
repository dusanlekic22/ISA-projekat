import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-base-cottage',
  templateUrl: './base-cottage.component.html',
  styleUrls: ['./base-cottage.component.css'],
})
export class BaseCottageComponent implements OnInit {
  @Input() id!: string;
  @Input() name!: string;
  constructor(public router: Router) {}

  ngOnInit(): void {}
}
