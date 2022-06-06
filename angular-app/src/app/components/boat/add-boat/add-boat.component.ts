import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { IAddress } from 'src/app/model/address';
import { IBoat, initBoat } from 'src/app/model/boat/boat';
import { IDateSpan } from 'src/app/model/dateSpan';
import { BoatAdditionalServicesService } from 'src/app/pages/boat-owner/services/boat-additional-services.service';
import { AdditionalServiceService } from 'src/app/pages/cottage-owner/services/additional-service.service';
import { UserService } from 'src/app/service/user.service';
import { IAdditionalService } from '../../../model/additionalService';
import { BoatService } from '../../../pages/boat-owner/services/boat.service';


@Component({
  selector: 'app-add-boat',
  templateUrl: './add-boat.component.html',
  styleUrls: ['./add-boat.component.css'],
})
export class AddBoatComponent implements OnInit {
  boat: IBoat = initBoat;

  startDate!: Date;
  endDate!: Date;
  avaliableDateSpans: IDateSpan[] = [];
  additionalServiceTags: IAdditionalService[] = [];
  minDate!: Date;
  fishingEquipment!:string;
  navigationEquipment!:string;

  ngOnInit(): void {
    this._userService.currentUser.subscribe((user) => {
      this.boat.boatOwner = user;
    });
    this._additionalServiceService
      .getFreeAdditionalServices()
      .subscribe((additionalService) => {
        this.additionalServiceTags = additionalService;
      });
    this.minDate = new Date(Date.now());
  }

  validatingForm: FormGroup;

  constructor(
    private _boatService: BoatService,
    private _additionalServiceService: AdditionalServiceService,
    private _boatAdditionalService: BoatAdditionalServicesService,
    private _userService: UserService,
    private _toastr: ToastrService,
    private _router: Router
  ) {
    this.validatingForm = new FormGroup({
      loginFormModalEmail: new FormControl('', Validators.email),
      loginFormModalPassword: new FormControl('', Validators.required),
    });
  }

  addDateSpan() {
    this.boat.availableReservationDateSpan.push({
      startDate: this.startDate,
      endDate: this.endDate,
    });
  }

  setAddress(address:IAddress){
    this.boat.address = address;
  }

  addBoat() {
    this._boatService.saveBoat(this.boat).subscribe((data) => {
      this._toastr.success('Boat was successfully added.');
      this.additionalServiceTags.forEach((element) => {
        this._boatAdditionalService
          .addAdditionalServiceForBoat(element, data)
          .subscribe((additionalService) => {});
      });
      this._router.navigate(['/boatOwnerHome'])
    },
    (err) => {
      console.log(err);
      this._toastr.error(
        "Couldn't add the boat!"
      );
    });
  }

  addFishingEquipment(){
    this.boat.fishingEquipment.push(this.fishingEquipment);
  }

  addNavigationEquipment(){
    this.boat.navigationEquipment.push(this.navigationEquipment);
  }

  removeTerm(term: IDateSpan) {
    this.boat.availableReservationDateSpan =
      this.boat.availableReservationDateSpan.filter((term) => term != term);
  }

  get loginFormModalEmail() {
    return this.validatingForm.get('loginFormModalEmail');
  }

  get loginFormModalPassword() {
    return this.validatingForm.get('loginFormModalPassword');
  }

  onItemAdded(input: any): void {
    let text = input.display.split(' ');
    this.additionalServiceTags.pop();
    this.additionalServiceTags.push({ id: 0, name: text[0], price: text[1] });
  }
}
