import { ThrowStmt } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/service/user.service';
import { IUser } from '../../registration/registration/user';
import { BoatService } from '../services/boat.service';
import { ToastrService } from 'ngx-toastr';
import { IBoat } from 'src/app/model/boat/boat';

@Component({
  selector: 'app-boat-owner-home',
  templateUrl: './boat-owner-home.component.html',
  styleUrls: ['./boat-owner-home.component.css'],
})
export class BoatOwnerHomeComponent implements OnInit {
  addFormVisible: boolean = false;
  boat!: IBoat[];
  filteredBoats!: IBoat[];
  boats!: IBoat[];
  boatId!: number;
  filter:string = '';
  boatOwner!:IUser;

  constructor(
    private _router: Router,
    private _boatService: BoatService,
    private _userService: UserService,
    private _toastr: ToastrService
  ) {}

  getBoats(ownerId : number): void{
    this._boatService.getBoatsByBoatOwnerId(ownerId).subscribe((boats) => {
      this.boats = boats;
      this.filteredBoats = this.boats;
    });
  }

  ngOnInit(): void {
    this._userService.currentUser.subscribe((user) => {
      this.boatOwner = user;
      this.getBoats(user.id);
    });
  }

  boatProfile(boatId: number) {
    this._router.navigateByUrl(`boatProfile/${boatId}`);
  }

  addBoatClick(){
    this.addFormVisible=true;
  }

  added(submitted:boolean){
    if(submitted)
    {
      this.getBoats(this.boatOwner.id);
    }
  }

  deleteBoat(boatId: number) {
    this._boatService.deleteBoat(boatId).subscribe((boats) => {
      this._toastr.success('Boat successfully removed.');
      this.getBoats(this.boatOwner.id);
    },
    (error) => {
      this._toastr.error(
        "You can't delete a boat that has active reservations!"
      );
    });
  }

  searchBoats(filter: string) {
    if(filter!='')
      this.filteredBoats = this.boats.filter(boat => boat.name.toLowerCase().includes(filter.toLowerCase()));
    else 
      this.filteredBoats = this.boats;
  }
}
