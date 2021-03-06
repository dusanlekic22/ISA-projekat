import { ICottageQuickReservationPage } from './../../../model/cottageQuickReservation';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, catchError, throwError } from 'rxjs';
import { ICottage } from 'src/app/model/cottage';
import { ICottageQuickReservation } from 'src/app/model/cottageQuickReservation';
import { ICottageReservation } from 'src/app/model/cottageReservation';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CottageQuickReservationService {
  constructor(private _http: HttpClient) {}

  addCottageQuickReservation(
    cottageQuickReservation: ICottageQuickReservation,
    cottage: ICottage
  ): Observable<ICottageQuickReservation> {
    return this._http
      .post<ICottageQuickReservation>(
        environment.apiUrl + '/cottageQuickReservation',
        {
          id: cottageQuickReservation.id,
          duration: cottageQuickReservation.duration,
          guestCapacity: cottageQuickReservation.guestCapacity,
          price: cottageQuickReservation.price,
          cottage: cottage,
        }
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getCottageQuickReservations(): Observable<ICottageQuickReservation[]> {
    return this._http
      .get<ICottageQuickReservation[]>(
        environment.apiUrl + '/cottageQuickReservation'
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getCottageQuickReservationsByCottageId(
    id: number
  ): Observable<ICottageQuickReservation[]> {
    return this._http
      .get<ICottageQuickReservation[]>(
        environment.apiUrl + `/cottageQuickReservation/cottage/${id}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  deleteCottageQuickReservations(cottageId: number): Observable<ArrayBuffer> {
    return this._http
      .delete<ArrayBuffer>(
        environment.apiUrl + `/cottageQuickReservation/${cottageId}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getAllByCottageOwnerId(id: number): Observable<ICottageQuickReservation[]> {
    return this._http
      .get<ICottageQuickReservation[]>(
        environment.apiUrl + `/cottageQuickReservation/cottageOwner/${id}`
      )
      .pipe(catchError(this.handleError));
  }

  getCottagesQuickReservationPagination(
    page: number,
    cottageOwnerId: number
  ): Observable<ICottageQuickReservationPage> {
    return this._http
      .get<ICottageQuickReservationPage>(
        environment.apiUrl +
          `/cottageQuickReservation/pagination/${cottageOwnerId}/?page=` +
          page
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  appoint(reservationId: number, customerId: number): Observable<any> {
    return this._http
      .get<any>(
        environment.apiUrl +
          `/cottageQuickReservation/customer/appoint/${reservationId}/user/${customerId}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
}
