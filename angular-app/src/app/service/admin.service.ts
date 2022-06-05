import { catchError, Observable, throwError } from 'rxjs';
import { IUser } from './../pages/registration/registration/user';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private adminUrl = `${environment.apiUrl}/admin`;

  constructor(private http: HttpClient) {}

  addAdmin(admin: IUser): Observable<IUser> {
    return this.http
      .post<IUser>(this.adminUrl, admin)
      .pipe(catchError(this.handleError));
  }

  handleError(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => {
      return errorMessage;
    });
  }
}
