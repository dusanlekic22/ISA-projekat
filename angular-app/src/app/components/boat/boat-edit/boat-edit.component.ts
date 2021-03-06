import { ActivatedRoute } from '@angular/router';
import { Component, Input, OnInit } from '@angular/core';
import { BoatService } from 'src/app/pages/boat-owner/services/boat.service';
import { IAdditionalService } from 'src/app/model/additionalService';
import { ToastrService } from 'ngx-toastr';
import { IBoat } from 'src/app/model/boat/boat';
import { AdditionalServiceService } from 'src/app/pages/cottage-owner/services/additional-service.service';
import { IAddress } from 'src/app/model/address';
import { BoatAdditionalServicesService } from 'src/app/pages/boat-owner/services/boat-additional-services.service';

@Component({
  selector: 'app-boat-edit',
  templateUrl: './boat-edit.component.html',
  styleUrls: ['./boat-edit.component.css'],
})
export class BoatEditComponent implements OnInit {
  @Input() boat!: IBoat;
  additionalServiceTags: IAdditionalService[] = [];
  fishingEquipment!:string;
  navigationEquipment!:string;

  constructor(
    private _boatService: BoatService,
    private _additionalServiceService: AdditionalServiceService,
    private _boatAdditionalService: BoatAdditionalServicesService,
    private _toastr: ToastrService,
    private _route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this._boatAdditionalService
      .getAdditionalServicesByBoatId(this.boat.id)
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
          this._boatAdditionalService
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

  addFishingEquipment(){
    this.boat.fishingEquipment.push(this.fishingEquipment);
  }

  addNavigationEquipment(){
    this.boat.navigationEquipment.push(this.navigationEquipment);
  }

  onItemAdded(input: any): void {
    let text = input.display.split(' ');
    this.additionalServiceTags.pop();
    this.additionalServiceTags.push({ id: 0, name: text[0], price: text[1] });
  }
}
