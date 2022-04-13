import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { IToken, IUser, IUserLogin } from '../pages/registration/registration/user';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private accessToken = localStorage.getItem('jwt');

  constructor(
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
    this.accessToken = null;
    // this.router.navigate(['/login']);
  }

  getToken() {
    return this.accessToken;
  }

  isLoggedIn() {
    return this.accessToken !== undefined && this.accessToken !== null;
  }

}
