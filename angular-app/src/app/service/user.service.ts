import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, ReplaySubject } from 'rxjs';
import { distinctUntilChanged, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { IUser } from '../pages/registration/registration/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private currentUserSubject = new BehaviorSubject<IUser>({} as IUser);
  public currentUser = this.currentUserSubject.asObservable().pipe(distinctUntilChanged());

  constructor(
    private http: HttpClient
  ) {
    var currentUser = localStorage.getItem('currentUser')
    if (currentUser) {
      this.currentUserSubject = new BehaviorSubject<IUser>(JSON.parse(currentUser));
      this.currentUser = this.currentUserSubject.asObservable().pipe(distinctUntilChanged());
    }
  }

  public get userValue(): IUser {
    return this.currentUserSubject.value;
  }

  getCurrentUser(): Observable<IUser> {
    return this.http.get<any>(`${environment.apiUrl}/user`)
      .pipe(map((user: IUser) => {
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
        return user;
      }));
  }

}
