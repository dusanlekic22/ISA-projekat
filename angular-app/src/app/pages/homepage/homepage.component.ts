import { Component, OnInit } from '@angular/core';
import { ICottage } from '../cottage-owner/cottage-profile/cottage';
import { CottageService } from '../cottage-owner/services/cottage.service';
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
