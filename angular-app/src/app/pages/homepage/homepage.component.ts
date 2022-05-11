import { Component, OnInit } from '@angular/core';
import { CottageOwnerHomeComponent } from '../cottage-owner-home/cottage-owner-home.component';
import { CottageService } from '../cottage.service';
import { ICottage } from '../cottage-profile/cottage';
@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent implements OnInit {
  searchCottageName!: string;
  searchCottageBeds!: string;
  cottages!: ICottage[];
  constructor(  private _cottageService: CottageService) {}

  ngOnInit(): void {
    this._cottageService.getCottages().subscribe((data) => {
      this.cottages = data;
      console.log("evo data", data);
    })
  }
}
