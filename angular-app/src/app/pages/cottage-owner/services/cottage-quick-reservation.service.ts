import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ICottage } from '../cottage-profile/cottage';
import { ICottageQuickReservation } from '../cottage-profile/cottageQuickReservation';

@Injectable({
  providedIn: 'root'
})
export class CottageQuickReservationService {

  constructor(private _http: HttpClient) { }

  addCottageQuickReservation(cottageQuickReservation:ICottageQuickReservation,cottage:ICottage): Observable<ICottageQuickReservation> {
    return this._http.post<ICottageQuickReservation>(environment.apiUrl + '/cottageQuickReservation',
    {id:cottageQuickReservation.id,duration:cottageQuickReservation.duration,
      guestCapacity:cottageQuickReservation.guestCapacity,price:cottageQuickReservation.price,cottage:cottage}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  getCottageQuickReservations(): Observable<ICottageQuickReservation[]> {
    return this._http.get<ICottageQuickReservation[]>(environment.apiUrl + '/cottageQuickReservation',).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  deleteCottageQuickReservations(cottageId:number): Observable<ArrayBuffer> {
    return this._http.delete<ArrayBuffer>(environment.apiUrl + `/cottageQuickReservation/${cottageId}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }
  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
  
}
