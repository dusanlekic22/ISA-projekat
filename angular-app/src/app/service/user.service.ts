import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, ReplaySubject } from 'rxjs';
import { distinctUntilChanged, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { IUser } from '../pages/registration/registration/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {

  loggedUser!: IUser;

  constructor(private http: HttpClient) {}

  getCurrentUser(): Observable<IUser> {
    return this.http.get<any>(`${environment.apiUrl}/user`).pipe(
      map((user: IUser) => {
        this.loggedUser = user;
        return user;
      })
    );
  }

  logout() {
    this.loggedUser = {} as IUser;
  }

}
