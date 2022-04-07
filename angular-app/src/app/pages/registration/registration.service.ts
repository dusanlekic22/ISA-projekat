import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IBusinessOwner } from './business-owner-registration/business-owner';
import { IUser } from './registration/user';

@Injectable({
  providedIn: 'root',
})
export class RegistrationService {
  constructor(private http: HttpClient) {}

  submitForm(user: IUser): Observable<IUser> {
    return this.http.post<any>('http://localhost:8080/auth/signup', user);
  }

  registerFishingTrainer(user: IBusinessOwner): Observable<IBusinessOwner> {
    return this.http.post<any>('http://localhost:8080/fishingTrainer/signup', user);
  }
}
