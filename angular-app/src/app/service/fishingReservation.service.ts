import { environment } from './../../environments/environment';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, catchError, throwError } from 'rxjs';
import { ICustomer } from '../model/customer';
import { IFishingReservation } from '../model/fishingReservation';
import { IFishingCourse } from '../model/fishingCourse';
import { ISortType } from '../model/sortType';

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

  addFishingReservationCustomer(
    fishingCourseReservation: IFishingReservation
  ): Observable<IFishingReservation> {
    return this.http
      .post<IFishingReservation>(
        `${this.fishingReservationUrl}/customer`,
        fishingCourseReservation
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
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

  getPassedFishingReservationByFishingCourseByFishingTrainerId(
    id: number
  ): Observable<IFishingReservation[]> {
    return this.http
      .get<IFishingReservation[]>(
        `${this.fishingReservationUrl}/passed/trainer/${id}`
      )
      .pipe(catchError(this.handleError));
  }

  getActiveFishingReservationByFishingCourseByFishingTrainerId(
    id: number
  ): Observable<IFishingReservation[]> {
    return this.http
      .get<IFishingReservation[]>(
        `${this.fishingReservationUrl}/active/trainer/${id}`
      )
      .pipe(catchError(this.handleError));
  }

  getCustomerHasReservationNow(id:number): Observable<ICustomer[]> {
    return this.http
      .get<ICustomer[]>(
        `${this.fishingReservationUrl}/customerHasReservationNow/fishingCourse/${id}`
      )
      .pipe(catchError(this.handleError));
  }

  getAvailableFishingsReservation(
    customerId: number,
    sortBy: ISortType,
    page: number
  ): Observable<IFishingReservation[]> {
    return this.http
      .post<IFishingReservation[]>(
        environment.apiUrl +
          `/fishingReservation/customer/${customerId}?page=${page}`,
        sortBy
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }
  getAvailableFishingsReservationIncoming(
    customerId: number,
    sortBy: ISortType,
    page: number
  ): Observable<IFishingReservation[]> {
    return this.http
      .post<IFishingReservation[]>(
        environment.apiUrl +
          `/fishingReservation/incoming/customer/${customerId}?page=${page}`,
        sortBy
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
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
