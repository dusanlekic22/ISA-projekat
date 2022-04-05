import { Component, OnInit } from '@angular/core';
import {
  IToken,
  IUserLogin,
} from 'src/app/pages/registration/registration/user';
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
  constructor(private _loginService: LoginService) {}

  ngOnInit(): void {}

  login() {
    this.user = {
      username: this.username,
      password: this.password,
    };
    let enteredUsername = this.username;
    this._loginService.submitForm(this.user).subscribe({
      next: (data) => {
        this.userToken = data;
        console.log('x' + this.userToken.accessToken);
        console.log('username' + enteredUsername);
        localStorage.setItem('username', enteredUsername);
        localStorage.setItem('accessToken', this.userToken.accessToken);
      },
      error: (error) => {
        this.errorMessage = error.message;
        console.error('There was an error!', error);
      },
    });
  }
}
