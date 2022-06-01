import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-grade-star',
  templateUrl: './grade-star.component.html',
  styleUrls: ['./grade-star.component.css'],
})
export class GradeStarComponent implements OnInit {
  @Input() grade!: number;
  fullStar: number = 0;
  emptyStar: number = 0;
  halfStar: number = 0;
  fullstars!: number[];
  emptystars!: number[];
  halfstars!: number[];
  constructor() {}

  ngOnInit(): void {
    if (this.grade !== undefined) {
      if (this.grade % 1 > 0.349 && this.grade % 1 <= 0.749) {
        this.halfStar = 1;
        if (this.grade % 1 > 0.749) {
          this.halfStar = 0;
        }
      }

      if (this.grade % 1 >= 0.5) {
        this.fullStar = Math.abs(this.halfStar - Math.round(this.grade));
      }
      if (this.grade % 1 < 0.5 && this.halfStar === 1) {
        this.fullStar = Math.abs(Math.round(this.grade) + 1 - this.halfStar);
      } else {
        this.fullStar = Math.abs(Math.round(this.grade) - this.halfStar);
      }
      this.emptyStar = Math.abs(5 - this.fullStar - this.halfStar);

      if (this.halfStar !== 0) {
        this.halfstars = [].constructor(this.halfStar);
      }

      if (this.fullStar !== 0) {
        this.fullstars = [].constructor(this.fullStar);
      }
      if (this.emptyStar !== 0) {
        this.emptystars = [].constructor(this.emptyStar);
      }
    }
  }
}
