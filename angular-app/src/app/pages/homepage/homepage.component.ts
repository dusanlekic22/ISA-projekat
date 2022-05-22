import { Component, OnInit } from '@angular/core';
import { ICottage } from '../cottage-owner/cottage-profile/cottage';
import { CottageService } from '../cottage-owner/services/cottage.service';

import { MatChip } from '@angular/material/chips';
@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css'],
})
export class HomepageComponent implements OnInit {
  searchCottageName!: string;
  searchCottageBeds!: string;
  cottages!: ICottage[];
  startCottageDate!: Date;
  endCottageDate!: Date;
  optionsCottages: string[] = ['dare', 'leka'];
  chips!: MatChip;
  cottageChips: string[] = [];

  constructor(private _cottageService: CottageService) {}

  ngOnInit(): void {
    this._cottageService.getCottages().subscribe((data) => {
      this.cottages = data;
      console.log('evo data', data);
    });
  }
  toggleSelectionCottage(chip: MatChip, option: string) {
    let x: string[] = [];
    if (chip.toggleSelected()) {
      this.cottageChips.push(option);
    } else {
      this.cottageChips = this.cottageChips.filter((e) => e !== option);
    }
  }
}
