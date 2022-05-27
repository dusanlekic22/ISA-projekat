import { environment } from './../../environments/environment';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, catchError, throwError } from 'rxjs';
import { ICustomer } from '../model/customer';
import { IFishingReservation } from '../model/fishingReservation';

@Injectable({
  providedIn: 'root',
})
export class FishingReservationService {
  private fishingReservationUrl = `${environment.apiUrl}/fishingReservation`;

  constructor(private http: HttpClient) {}

  addFishingReservation(
    fishingReservation: IFishingReservation
  ): Observable<IFishingReservation> {
    return this.http
      .post<IFishingReservation>(
        `${this.fishingReservationUrl}/owner`,
        fishingReservation
      )
      .pipe(catchError(this.handleError));
  }

  getPassedFishingReservationByFishingCourseId(
    id: number
  ): Observable<IFishingReservation[]> {
    return this.http
      .get<IFishingReservation[]>(`${this.fishingReservationUrl}/passed/${id}`)
      .pipe(catchError(this.handleError));
  }

  getActiveFishingReservationByFishingCourseId(
    id: number
  ): Observable<IFishingReservation[]> {
    return this.http
      .get<IFishingReservation[]>(`${this.fishingReservationUrl}/active/${id}`)
      .pipe(catchError(this.handleError));
  }

  getCustomerHasReservationNow(): Observable<ICustomer[]> {
    return this.http
      .get<ICustomer[]>(
        `${this.fishingReservationUrl}/customerHasReservationNow`
      )
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
