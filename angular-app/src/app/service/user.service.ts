import { AuthenticationService } from 'src/app/service/authentication.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, ReplaySubject, throwError } from 'rxjs';
import { catchError, distinctUntilChanged, map, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { IUser } from '../pages/registration/registration/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private currentUserSubject = new BehaviorSubject<IUser>({} as IUser);
  public currentUser = this.currentUserSubject
    .asObservable()
    .pipe(distinctUntilChanged());

  constructor(
    private http: HttpClient,
    authenticationService: AuthenticationService
  ) {
    if (authenticationService.isLoggedIn()) {
      this.getCurrentUser().subscribe({
        next: (user) => {
          this.currentUserSubject.next(user);
        },
        error: (error) => {
          console.error('There was an error!', error);
        },
      });
    }
  }

  createDeleteRequest(explanation:string, email:string): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/userDeletion`,{deletionExplanation:explanation,userEmail:email})
    .pipe(tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  getCurrentUser(): Observable<IUser> {
    return this.http.get<any>(`${environment.apiUrl}/user`).pipe(
      map((user: IUser) => {
        this.currentUserSubject.next(user);
        return user;
      })
    );
  }

  updateUser(user: IUser): Observable<IUser> {
    return this.http.put<any>(`${environment.apiUrl}/user/${user.id}`, user);
  }

  purgeUser() {
    this.currentUserSubject.next({} as IUser);
  }

  changePassword(password: string): Observable<any> {
    let user: IUser = this.currentUserSubject.value;
    console.log(user.id)
    return this.http.put<any>(`${environment.apiUrl}/admin/changePassword`, {
      id: user.id,
      password: password,
    });
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
}
