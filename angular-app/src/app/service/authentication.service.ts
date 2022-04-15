import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { IToken, IUserLogin } from '../pages/registration/registration/user';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private accessToken = localStorage.getItem('jwt');

  constructor(
    private router: Router,
    private http: HttpClient,
  ) {
  }

  login(user: IUserLogin) {
    return this.http.post<any>(`${environment.apiUrl}/auth/login`, user)
      .pipe(map((res: IToken) => {
        localStorage.setItem("jwt", res.accessToken)
        this.accessToken = res.accessToken;
      }));
  }

  logout() {
    localStorage.removeItem('jwt');
    localStorage.removeItem('currentUser');
    this.accessToken = null;
    this.router.navigate(['']);
  }

  getToken() {
    return this.accessToken;
  }

  isLoggedIn() {
    return this.accessToken !== undefined && this.accessToken !== null;
  }

}
