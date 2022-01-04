import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  IToken,
  IUserLogin,
} from 'src/app/pages/registration/registration/user';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private http: HttpClient) {}

  submitForm(user: IUserLogin): Observable<IToken> {
    return this.http.post<any>('http://localhost:8080/auth/login', user);
  }
}
