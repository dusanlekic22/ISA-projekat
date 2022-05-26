import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { IBoat } from 'src/app/model/boat/boat';
import { IDateSpan } from 'src/app/model/dateSpan';
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
  boat: IBoat = {
    id: 0,
    name: '',
    address: {
      city: 'Kraljevo',
      country: 'Srbija',
      latitude: '73',
      longitude: '89',
      street: 'Zmajevacka',
    },
    description: '',
    bedCount: 0,
    roomCount: 0,
    pricePerHour: 0,
    type: '',
    length: 0,
    engineNumber: 0,
    topSpeed: 0,
    enginePower: 0,
    cancelCondition: '',
    boatRules: '',
    boatImage: [],
    boatReservation: [],
    boatQuickReservation: [],
    availableReservationDateSpan: [],
    boatOwner: {
      id: 0,
      username: '',
      password: '',
      firstName: '',
      lastName: '',
      email: '',
      phoneNumber: '',
      roles: [],
      address: {
        street: '',
        city: '',
        country: '',
        latitude: '',
        longitude: '',
      },
    },
  };

  @Output() submitted = new EventEmitter<boolean>();
  startDate!: Date;
  endDate!: Date;
  avaliableDateSpans: IDateSpan[] = [];
  additionalServiceTags: IAdditionalService[] = [];
  minDate!: Date;

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
    private _userService: UserService
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

  addBoat(submit: boolean) {
    this._boatService.saveBoat(this.boat).subscribe((data) => {
      this.additionalServiceTags.forEach((element) => {
        this._additionalServiceService
          .addAdditionalServiceForBoat(element, this.boat)
          .subscribe((additionalService) => {});
      });

      this.submitted.emit(submit);
    });
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
