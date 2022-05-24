import { ICustomer } from './../../../model/customer';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ICottageReservation } from 'src/app/model/cottageReservation';

@Injectable({
  providedIn: 'root'
})
export class CottageReservationService {

  constructor(private _http: HttpClient) { }
  
  addCottageReservation(
    cottageReservation: ICottageReservation
  ): Observable<ICottageReservation> {
    return this._http
      .post<ICottageReservation>(
        environment.apiUrl + '/cottageReservation/owner',
        cottageReservation
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getPassedCottageReservationByCottageId(id : number): Observable<ICottageReservation[]> {
    return this._http.get<ICottageReservation[]>(environment.apiUrl + `/cottageReservation/passed/${id}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  getActiveCottageReservationByCottageId(id : number): Observable<ICottageReservation[]> {
    return this._http.get<ICottageReservation[]>(environment.apiUrl + `/cottageReservation/active/${id}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  getCustomerHasReservationNow(): Observable<ICustomer[]> {
    return this._http.get<ICustomer[]>(environment.apiUrl + `/cottageReservation/customerHasReservationNow`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
}
