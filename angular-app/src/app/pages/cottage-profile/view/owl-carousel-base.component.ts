import { Component, OnInit } from '@angular/core';
import { OwlOptions } from 'ngx-owl-carousel-o';
import * as $ from 'jquery';

@Component({
  selector: 'app-owl-carousel-base',
  templateUrl: './owl-carousel-base.component.html',
  styleUrls: ['./owl-carousel-base.component.css']
})
export class OwlCarouselBaseComponent implements OnInit {

 customOptions: OwlOptions = {
    loop: true,
    mouseDrag: false,
    touchDrag: false,
    pullDrag: false,
    dots: false,
    navSpeed: 700,
    navText:["<div class='nav-btn prev-slide carouselP'></div>","<div class='nav-btn next-slide'></div>"],
    responsive: {
      0: {
        items: 1 
      },
      
    },
    nav: false,
 }

  constructor() { }

  ngOnInit(): void { }


}
