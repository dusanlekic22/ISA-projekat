import { ThrowStmt } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/service/user.service';
import { IUser } from '../../registration/registration/user';
import { CottageService } from '../services/cottage.service';
import { ToastrService } from 'ngx-toastr';
import { ICottage } from 'src/app/model/cottage';

@Component({
  selector: 'app-cottage-owner-home',
  templateUrl: './cottage-owner-home.component.html',
  styleUrls: ['./cottage-owner-home.component.css'],
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
    private _userService: UserService,
    private _toastr: ToastrService
  ) {}

  getCottages(ownerId : number): void{
    this._cottageService.getCottagesByCottageOwnerId(ownerId).subscribe((cottages) => {
      this.cottages = cottages.filter((e) => e.deleted == false);
      this.filteredCottages = this.cottages.filter((e) => e.deleted == false);
    });
  }

  ngOnInit(): void {
    this._userService.currentUser.subscribe((user) => {
      if(user.id!=undefined){
        this.cottageOwner = user;
        this.getCottages(user.id);}
    });
  }

  cottageProfile(cottageId: number) {
    this._router.navigateByUrl(`cottageProfile/${cottageId}`);
  }

  addCottage(){
    this._router.navigateByUrl('newCottage');
  }

  added(submitted:boolean){
    if(submitted)
    {
      this.getCottages(this.cottageOwner.id);
    }
  }

  deleteCottage(cottageId: number) {
    this._cottageService.deleteCottage(cottageId).subscribe((cottages) => {
      this._toastr.success('Cottage successfully removed.');
      this.getCottages(this.cottageOwner.id);
    },
    (error) => {
      this._toastr.error(
        "You can't delete a cottage that has active reservations!"
      );
    });
  }

  searchCottages(filter: string) {
    if(filter!='')
      this.filteredCottages = this.cottages.filter(cottage => cottage.name.toLowerCase().includes(filter.toLowerCase()));
    else
      this.filteredCottages = this.cottages;
  }
}
