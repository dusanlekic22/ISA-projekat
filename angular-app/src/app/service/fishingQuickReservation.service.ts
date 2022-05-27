import { Observable, throwError, catchError, tap } from 'rxjs';
import { IFishingQuickReservation } from './../model/fishingQuickReservation';
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
