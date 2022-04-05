import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegistrationService } from '../registration.service';
import { IUser } from '../registration/user';

@Component({
  selector: 'app-business-owner-regitration',
  templateUrl: './business-owner-regitration.component.html',
  styleUrls: ['./business-owner-regitration.component.css']
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

  user!: IUser;
  errorMessage!: string;

  constructor(
    private _formBuilder: FormBuilder,
    private _registrationService: RegistrationService
  ) {}

  ngOnInit(): void {
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
    };

    this._registrationService.submitForm(this.user).subscribe({
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
