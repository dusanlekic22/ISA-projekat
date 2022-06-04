import { ICustomer } from './../../../model/customer';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IBoat } from 'src/app/model/boat/boat';
import { IBoatReservation } from 'src/app/model/boat/boatReservation';
import { ISortType } from 'src/app/model/sortType';

@Injectable({
  providedIn: 'root',
})
export class BoatReservationService {
  constructor(private _http: HttpClient) {}

  addBoatReservation(
    boatReservation: IBoatReservation,
    boat: IBoat
  ): Observable<IBoatReservation> {
    return this._http
      .post<IBoatReservation>(environment.apiUrl + '/boatReservation/owner', {
        id: boatReservation.id,
        duration: boatReservation.duration,
        guestCapacity: boatReservation.guestCapacity,
        price: boatReservation.price,
        customer: boatReservation.customer,
        confirmed: boatReservation.confirmed,
        boat: boat,
      })
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  addBoatReservationCustomer(
    boatReservation: IBoatReservation,
    boat: IBoat
  ): Observable<IBoatReservation> {
    return this._http
      .post<IBoatReservation>(
        environment.apiUrl + '/boatReservation/customer',
        {
          id: boatReservation.id,
          duration: boatReservation.duration,
          guestCapacity: boatReservation.guestCapacity,
          price: boatReservation.price,
          customer: boatReservation.customer,
          confirmed: boatReservation.confirmed,
          boat: boat,
        }
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getPassedBoatReservationByBoatId(id: number): Observable<IBoatReservation[]> {
    return this._http
      .get<IBoatReservation[]>(
        environment.apiUrl + `/boatReservation/passed/${id}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getActiveBoatReservationByBoatId(id: number): Observable<IBoatReservation[]> {
    return this._http
      .get<IBoatReservation[]>(
        environment.apiUrl + `/boatReservation/active/${id}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getCustomerHasReservationNow(id:number): Observable<ICustomer[]> {
    return this._http
      .get<ICustomer[]>(
        environment.apiUrl + `/boatReservation/customerHasReservationNow/boat/${id}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getActiveBoatReservationByBoatByBoatOwnerId(
    id: number
  ): Observable<IBoatReservation[]> {
    return this._http
      .get<IBoatReservation[]>(
        environment.apiUrl + `/boatReservation/active/owner/${id}`
      )
      .pipe(catchError(this.handleError));
  }

  getPassedBoatReservationByBoatByBoatOwnerId(
    id: number
  ): Observable<IBoatReservation[]> {
    return this._http
      .get<IBoatReservation[]>(
        environment.apiUrl + `/boatReservation/passed/owner/${id}`
      )
      .pipe(catchError(this.handleError));
  }

  getAvailableBoatsReservation(
    customerId: number,
    sortBy: ISortType,
    page: number
  ): Observable<IBoatReservation[]> {
    return this._http
      .post<IBoatReservation[]>(
        environment.apiUrl +
          `/boatReservation/customer/${customerId}?page=${page}`,
        sortBy
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }
  getAvailableBoatsReservationIncoming(
    customerId: number,
    sortBy: ISortType,
    page: number
  ): Observable<IBoatReservation[]> {
    return this._http
      .post<IBoatReservation[]>(
        environment.apiUrl +
          `/boatReservation/incoming/customer/${customerId}?page=${page}`,
        sortBy
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
