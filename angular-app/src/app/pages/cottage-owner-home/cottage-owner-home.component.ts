import { ThrowStmt } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ICottage } from '../cottage-profile/cottage';
import { CottageService } from '../cottage.service';

@Component({
  selector: 'app-cottage-owner-home',
  templateUrl: './cottage-owner-home.component.html',
  styleUrls: ['../cotage-style.css'],
})
export class CottageOwnerHomeComponent implements OnInit {
  filteredCottages!: ICottage[];
  cottages!: ICottage[];
  cottageId!: number;
  filter:string = '';

  constructor(
    private _router: Router,
    private _cottageService: CottageService
  ) {}

  getCottages(): void{
    this._cottageService.getCottages().subscribe((cottages) => {
      this.cottages = cottages;
      this.filteredCottages = this.cottages;
    });
  }

  ngOnInit(): void {
   this.getCottages();
  }

  cottageProfile(cottageId: number) {
    this._router.navigateByUrl(`cottageProfile/${cottageId}`);
  }

  deleteCottage(cottageId: number) {
    this._cottageService.deleteCottage(cottageId).subscribe((cottages) => {
      this.getCottages();
    });
  }

  searchCottages(filter: string) {
    if(filter!='')
      this.filteredCottages = this.cottages.filter(cottage => cottage.name.toLowerCase().includes(filter.toLowerCase()));
    else 
      this.filteredCottages = this.cottages;
  }
}
