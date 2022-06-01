import { IAddress, emptyAddress } from './../../../model/address';
import { RegistrationService } from '../../registration/registration.service';
import { Component, Input, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import {
  FormGroup,
  FormControl,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { IUser } from '../../registration/registration/user';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { NumberLiteralType } from 'typescript';

@Component({
  selector: 'app-change-user-info',
  templateUrl: './change-user-info.component.html',
  styleUrls: ['./change-user-info.component.css'],
})
export class ChangeUserInfoComponent implements OnInit {
  @Input() user!: IUser;
  firstFormGroup!: FormGroup;
  confirmPassword!: string;
  location!: string;
  errorMessage!: string;
  @Input() userId!: number;

  constructor(
    private _formBuilder: FormBuilder,
    private _userService: UserService,
    private _registrationService: RegistrationService,
    private _route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    if (this.user.address === null) {
      this.user.address = emptyAddress;
    }
  }

  setAddress(address: IAddress) {
    this.user.address = address;
  }

  update() {
    this._userService.updateUser(this.user).subscribe({
      next: (data) => {
        this.user = data;
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
  }
}
