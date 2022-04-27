import { ThrowStmt } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/service/user.service';
import { IAdditionalService } from '../cottage-profile/additionalService';
import { ICottage } from '../cottage-profile/cottage';
import { CottageService } from '../cottage.service';
import { IUser } from '../registration/registration/user';

@Component({
  selector: 'app-cottage-owner-home',
  templateUrl: './cottage-owner-home.component.html',
  styleUrls: ['../cotage-style.css'],
})
export class CottageOwnerHomeComponent implements OnInit {
  addFormVisible: boolean = false;
  cottage!: ICottage[];
  filteredCottages!: ICottage[];
  cottages!: ICottage[];
  cottageId!: number;
  filter:string = '';
  cottageOwner!:IUser;

  constructor(
    private _router: Router,
    private _cottageService: CottageService,
    private _userService: UserService
  ) {}

  getCottages(ownerId : number): void{
    this._cottageService.getCottagesByCottageOwnerId(ownerId).subscribe((cottages) => {
      this.cottages = cottages;
      this.filteredCottages = this.cottages;
    });
  }

  ngOnInit(): void {
    this._userService.currentUser.subscribe((user) => {
      this.cottageOwner = user;
      this.getCottages(user.id);
    });
  }

  cottageProfile(cottageId: number) {
    this._router.navigateByUrl(`cottageProfile/${cottageId}`);
  }

  addCottageClick(){
    this.addFormVisible=true;
  }

  added(submitted:boolean){
    if(submitted)
    {
      this.getCottages(this.cottageOwner.id);
    }
  }

  deleteCottage(cottageId: number) {
    this._cottageService.deleteCottage(cottageId).subscribe((cottages) => {
      this.getCottages(this.cottageOwner.id);
    });
  }

  searchCottages(filter: string) {
    if(filter!='')
      this.filteredCottages = this.cottages.filter(cottage => cottage.name.toLowerCase().includes(filter.toLowerCase()));
    else 
      this.filteredCottages = this.cottages;
  }
}
