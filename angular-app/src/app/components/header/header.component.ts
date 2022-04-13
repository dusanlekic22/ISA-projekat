import { Component, OnInit } from '@angular/core';
import {
  IToken,
  IUser,
  IUserLogin,
} from 'src/app/pages/registration/registration/user';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { UserService } from 'src/app/service/user.service';
import { LoginService } from './login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  user!: IUserLogin;
  userToken!: IToken;
  username!: string;
  password!: string;
  errorMessage!: string;
  regLink: string = '/chooseRegistration';
  loggedInUser!: IUser;

  constructor(private _loginService: LoginService,
    private authenticationService: AuthenticationService,
    private userService: UserService) { }

  ngOnInit(): void { 
    this.getUser();
  }

  login() {
    this.user = {
      username: this.username,
      password: this.password,
    };
    let enteredUsername = this.username;
    this.authenticationService.login(this.user).subscribe({
      next: (data) => {
        console.log(data);
        this.getUser()
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
    // this._loginService.submitForm(this.user).subscribe({
    //   next: (data) => {
    //     this.userToken = data;
    //     console.log('x' + this.userToken.accessToken);
    //     console.log('username' + enteredUsername);
    //     localStorage.setItem('username', enteredUsername);
    //     localStorage.setItem('accessToken', this.userToken.accessToken);
    //   },
    //   error: (error) => {
    //     this.errorMessage = error.message;
    //     console.error('There was an error!', error);
    //   },
    // });
  }

  getUser() {
    this.userService.getCurrentUser().subscribe({
      next: (data) => {
        console.log(data);
        this.loggedInUser = data;
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
  }
  
}
