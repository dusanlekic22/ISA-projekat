import { ActivatedRoute } from '@angular/router';
import { Component, Input, OnInit } from '@angular/core';
import { BoatService } from 'src/app/pages/boat-owner/services/boat.service';
import { IAdditionalService } from 'src/app/model/additionalService';
import { ToastrService } from 'ngx-toastr';
import { IBoat } from 'src/app/model/boat/boat';
import { AdditionalServiceService } from 'src/app/pages/cottage-owner/services/additional-service.service';
import { IAddress } from 'src/app/model/address';

@Component({
  selector: 'app-boat-edit',
  templateUrl: './boat-edit.component.html',
  styleUrls: ['./boat-edit.component.css'],
})
export class BoatEditComponent implements OnInit {
  @Input() boat!: IBoat;
  additionalServiceTags: IAdditionalService[] = [];

  constructor(
    private _boatService: BoatService,
    private _additionalServiceService: AdditionalServiceService,
    private _toastr: ToastrService,
    private _route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    let boatId = +this._route.snapshot.paramMap.get('boatId')!;
    this._additionalServiceService
      .getAdditionalServicesByBoatId(boatId)
      .subscribe((additionalService) => {
        this.additionalServiceTags = additionalService.filter(
          (additionalService) => additionalService.name != null
        );
      });
  }

  edit(): void {
    this._boatService.editBoatInfo(this.boat).subscribe(
      (boat) => {
        this._toastr.success('Boat information successfully changed.');
        this.boat = boat;
        this.additionalServiceTags.forEach((element) => {
          this._additionalServiceService
            .addAdditionalServiceForBoat(element, this.boat)
            .subscribe((additionalService) => {});
        });
      },
      (error) => {
        this._toastr.error(
          "You can't edit a boat that has active reservations!"
        );
      }
    );
  }

  setAddress(address:IAddress){
    this.boat.address = address;
  }

  onItemAdded(input: any): void {
    let text = input.display.split(' ');
    this.additionalServiceTags.pop();
    this.additionalServiceTags.push({ id: 0, name: text[0], price: text[1] });
  }
}
