import { Role } from './../../model/role.enum';
import { Component, OnInit } from '@angular/core';
import {
  IUser,
  IUserLogin,
} from 'src/app/pages/registration/registration/user';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';

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

  constructor(
    private authenticationService: AuthenticationService,
    private userService: UserService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    if (this.isLoggedIn()) {
      this.getUser();
    }
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
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
  }

  isLoggedIn(): boolean {
    return this.authenticationService.isLoggedIn();
  }
}
