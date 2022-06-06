import { Observable, throwError, catchError, tap } from 'rxjs';
import {
  IFishingQuickReservation,
  IFishingQuickReservationPage,
} from './../model/fishingQuickReservation';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class FishingQuickReservationService {
  private fishingQuickReservationUrl = `${environment.apiUrl}/fishingQuickReservation`;

  constructor(private http: HttpClient) {}

  addFishingQuickReservation(
    fishingQuickReservation: IFishingQuickReservation
  ): Observable<IFishingQuickReservation> {
    return this.http
      .post<IFishingQuickReservation>(
        this.fishingQuickReservationUrl,
        fishingQuickReservation
      )
      .pipe(catchError(this.handleError));
  }

  getFishingQuickReservations(): Observable<IFishingQuickReservation[]> {
    return this.http
      .get<IFishingQuickReservation[]>(this.fishingQuickReservationUrl)
      .pipe(catchError(this.handleError));
  }

  deleteFishingQuickReservations(
    fishingCourseId: number
  ): Observable<ArrayBuffer> {
    return this.http
      .delete<ArrayBuffer>(
        `${this.fishingQuickReservationUrl}/${fishingCourseId}`
      )
      .pipe(catchError(this.handleError));
  }

  getAllByFishingTrainerId(id: number): Observable<IFishingQuickReservation[]> {
    return this.http
      .get<IFishingQuickReservation[]>(
        `${this.fishingQuickReservationUrl}/fishingTrainer/${id}`
      )
      .pipe(catchError(this.handleError));
  }

  getFishingsQuickReservationPagination(
    page: number,
    fishingTrainerId: number
  ): Observable<IFishingQuickReservationPage> {
    return this.http
      .get<any>(
        environment.apiUrl +
          `fishingQuickReservation/pagination/${fishingTrainerId}/?page=` +
          page
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  appoint(reservationId: number, customerId: number): Observable<any> {
    return this.http
      .get<any>(
        environment.apiUrl +
          `/fishingQuickReservation/customer/appoint/${reservationId}/user/${customerId}`
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
