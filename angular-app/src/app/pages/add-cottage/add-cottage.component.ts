import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ICottage } from '../cottage-profile/cottage';
import { CottageService } from '../cottage.service';

@Component({
  selector: 'app-add-cottage',
  templateUrl: './add-cottage.component.html',
  styleUrls: ['./add-cottage.component.css']
})
export class AddCottageComponent implements OnInit {

  cottage: ICottage = {
    id: 0,
    name: '',
    address: {
      id: 0,
      city: 'Kraljevo',
      country: 'Srbija',
      latitude: '73',
      longitude: '89',
      street: 'Zmajevacka',
    },
    promoDescription: '',
    bedCount: 0,
    roomCount: 0,
    cottageRules: '',
    cottageImage: [],
    cottageReservation: [],
    cottageQuickReservation: [],
  };

  ngOnInit(): void {
  }

  validatingForm: FormGroup;

  constructor(private _cottageService: CottageService) {
    this.validatingForm = new FormGroup({
      loginFormModalEmail: new FormControl('', Validators.email),
      loginFormModalPassword: new FormControl('', Validators.required)
    });
  }

  addCottage(){
    this._cottageService.saveCottage(this.cottage).subscribe((cottages) => {
    });
  }

  get loginFormModalEmail() {
    return this.validatingForm.get('loginFormModalEmail');
  }

  get loginFormModalPassword() {
    return this.validatingForm.get('loginFormModalPassword');
  }

}
