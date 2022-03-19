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

  cottages!: ICottage[];
  cottageId!: number;

  constructor( private _router: Router,
    private _cottageService: CottageService) {}

  ngOnInit(): void {
    this._cottageService.getCottages().subscribe(
      cottages => this.cottages = cottages
    );
  }

  cottageProfile(cottageId:number){
    this._router.navigateByUrl(`cottageProfile/${cottageId}`);
  }
}
