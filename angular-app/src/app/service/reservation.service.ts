import { ICottage } from './../pages/cottage-owner/cottage-profile/cottage';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { catchError, tap } from 'rxjs/operators';
import { IDateTimeSpan } from '../model/date-time-span';

@Injectable({
  providedIn: 'root',
})
export class ReservationService {
  constructor(private http: HttpClient) {}

  getAvailableCottagesByTimeSpan(
    reservationTimespan: IDateTimeSpan
  ): Observable<ICottage[]> {
    return this.http
      .post<any>(
        `${environment.apiUrl}/cottage/availability`,
        reservationTimespan
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }
  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error(err.message));
  }
}
