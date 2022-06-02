import { Component, OnInit } from '@angular/core';
import '../../../../assets/jquery/jquery.min';
import '../../../../assets/owlcarousel/owl.carousel.js';

@Component({
  selector: 'app-customer-boat-profile',
  templateUrl: './customer-boat-profile.component.html',
  styleUrls: ['./customer-boat-profile.component.css'],
})
export class CustomerBoatProfileComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {
    window.scrollTo(0, 0);
  }
}
