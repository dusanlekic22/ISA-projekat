import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BusinessType } from '../business-type';
import { RegistrationService } from '../registration.service';
import { IBusinessOwner } from './business-owner';

@Component({
  selector: 'app-business-owner-registration',
  templateUrl: './business-owner-registration.component.html',
  styleUrls: ['./business-owner-registration.component.css']
})
export class BusinessOwnerRegitrationComponent implements OnInit {
  firstFormGroup!: FormGroup;
  firstName!: string;
  lastname!: string;
  username!: string;
  email!: string;
  contact!: string;
  password!: string;
  confirmPassword!: string;
  registrationExplanation!: string;

  selectedBusiness!: BusinessType;
  businessTypeKeys!: number[];
  businessTypes = BusinessType;

  user!: IBusinessOwner;
  errorMessage!: string;

  constructor(
    private _formBuilder: FormBuilder,
    private _registrationService: RegistrationService
  ) { }

  ngOnInit(): void {
    this.businessTypeKeys = Object.keys(this.businessTypes)
      .filter((f) => !isNaN(Number(f)))
      .map(Number);
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required],
    });
  }

  register() {
    this.user = {
      username: this.username,
      password: this.password,
      firstName: this.firstName,
      lastName: this.lastname,
      email: this.email,
      phoneNumber: this.contact,
      // address: {
      //   street: 'dr Ivana Ribara',
      //   city: 'Novi Sad',
      // },
      registrationExplanation: this.registrationExplanation
    };
    if (this.selectedBusiness == BusinessType.FishingTrainer) {
      this.registerFishingTrainer();
    }else if (this.selectedBusiness == BusinessType.CottageOwner){
      this.registerCottageOwner();
    }
  }

  registerFishingTrainer() {
    this._registrationService.registerFishingTrainer(this.user).subscribe({
      next: (data) => {
        this.user = data;
        console.log('pozvan' + data);
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
  }

  registerCottageOwner() {
    this._registrationService.registerCottageOwner(this.user).subscribe({
      next: (data) => {
        this.user = data;
        console.log('pozvan' + data);
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
  }
}
