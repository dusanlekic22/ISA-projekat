import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-base-cottage',
  templateUrl: './base-cottage.component.html',
  styleUrls: ['./base-cottage.component.css'],
})
export class BaseCottageComponent implements OnInit {
  name: string = 'Old Cottage';
  type: string = 'Otvoren';
  constructor() {}

  ngOnInit(): void {}

  open() {}
}
