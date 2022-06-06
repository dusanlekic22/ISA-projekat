import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-complaints',
  templateUrl: './complaints.component.html',
  styleUrls: ['./complaints.component.css']
})
export class ComplaintsComponent implements OnInit {
  requests: any[] = [];

  constructor() { }

  ngOnInit(): void {
  }

  getOwner(request: any) {

  }

  getCustomer(request: any) {

  }

  answer(request: any) {

  }
}
