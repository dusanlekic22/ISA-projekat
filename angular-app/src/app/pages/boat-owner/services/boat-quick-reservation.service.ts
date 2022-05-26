import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, catchError, throwError } from 'rxjs';
import { IBoat } from 'src/app/model/boat/boat';
import { IBoatQuickReservation } from 'src/app/model/boat/boatQuickReservation';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BoatQuickReservationService {

  constructor(private _http: HttpClient) { }

  addBoatQuickReservation(boatQuickReservation:IBoatQuickReservation,boat:IBoat): Observable<IBoatQuickReservation> {
    return this._http.post<IBoatQuickReservation>(environment.apiUrl + '/boatQuickReservation',
    {id:boatQuickReservation.id,duration:boatQuickReservation.duration,
      guestCapacity:boatQuickReservation.guestCapacity,price:boatQuickReservation.price,boat:boat}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  getBoatQuickReservations(): Observable<IBoatQuickReservation[]> {
    return this._http.get<IBoatQuickReservation[]>(environment.apiUrl + '/boatQuickReservation',).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  deleteBoatQuickReservations(boatId:number): Observable<ArrayBuffer> {
    return this._http.delete<ArrayBuffer>(environment.apiUrl + `/boatQuickReservation/${boatId}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }
  
  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
  
}
