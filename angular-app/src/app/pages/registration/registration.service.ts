import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUser } from './registration/user';

@Injectable({
  providedIn: 'root',
})
export class RegistrationService {
  constructor(private http: HttpClient) {}

  submitForm(user: IUser): Observable<IUser> {
    return this.http.post<any>('http://localhost:8080/auth/signup', user);
  }
}
