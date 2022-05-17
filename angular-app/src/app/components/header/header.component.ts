import { FormControl, FormGroup, Validators } from '@angular/forms';
import { IRole } from './../../pages/registration/registration/user';
import { Role } from './../../model/role.enum';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import {
  IDirective,
  IUser,
  IUserLogin,
} from 'src/app/pages/registration/registration/user';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { UserService } from 'src/app/service/user.service';
import { ReferenceFilter } from '@angular/compiler';
import { ModalDirective } from 'angular-bootstrap-md';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  userLogin!: IUserLogin;
  username!: string;
  password!: string;
  errorMessage!: string;
  regLink: string = '/chooseRegistration';
  loggedInUser!: IUser;
  @ViewChild('frame') basicModal!: ModalDirective;

  changePasswordForm: FormGroup;
  changePassword!: string;
  confirmChangePassword!: string;

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService
  ) {
    this.changePasswordForm = new FormGroup({
      formModalPassword: new FormControl('', Validators.required),
      formModalConfirmPassword: new FormControl('', Validators.required),
    });
  }

  ngOnInit(): void {
    if (this.isLoggedIn()) {
      this.getUser();
    }
  }

  get formModalPassword() {
    return this.changePasswordForm.get('formModalPassword');
  }

  get formModalConfirmPassword() {
    return this.changePasswordForm.get('formModalConfirmPassword');
  }

  login() {
    this.userLogin = {
      username: this.username,
      password: this.password,
    };
    this.authenticationService.login(this.userLogin).subscribe({
      next: (res) => {
        this.getUser();
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
  }

  logout() {
    this.userService.purgeUser();
    this.authenticationService.logout();
  }

  getUser() {
    this.userService.getCurrentUser().subscribe({
      next: (user) => {
        this.loggedInUser = user;
        if (this.isUserAdmin()) {
          this.checkFirstTimeLoginIn();
        }
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
  }

  isUserAdmin(): boolean {
    return this.loggedInUser.roles.some((role) => role.name === 'ROLE_ADMIN');
  }

  checkFirstTimeLoginIn(): void {
    this.authenticationService.isAdminFirstTimeLoggedIn().subscribe({
      next: (isFirstTimeLoggedIn) => {
        if (isFirstTimeLoggedIn) this.openModal();
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
  }

  openModal(): void {
    this.basicModal.show();
  }

  isLoggedIn(): boolean {
    return this.authenticationService.isLoggedIn();
  }

  submitChangePassword(): void {
    this.userService.changePassword(this.password).subscribe({
      next: () => {
        this.closeModal();
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
  }

  closeModal(): void {
    this.basicModal.hide();
  }
}
