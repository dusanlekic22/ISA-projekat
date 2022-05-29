import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, catchError, throwError } from 'rxjs';
import { IAdditionalService } from 'src/app/model/additionalService';
import { IBoat } from 'src/app/model/boat/boat';
import { IBoatQuickReservation } from 'src/app/model/boat/boatQuickReservation';
import { IBoatReservation } from 'src/app/model/boat/boatReservation';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BoatAdditionalServicesService {

  constructor(private _http: HttpClient) { }

  getAdditionalServicesByBoatId(
    id: number
  ): Observable<IAdditionalService[]> {
    return this._http
      .get<IAdditionalService[]>(
        environment.apiUrl + `/additionalService/boat/${id}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getAdditionalServicesByBoatReservationId(
    id: number
  ): Observable<IAdditionalService[]> {
    return this._http
      .get<IAdditionalService[]>(
        environment.apiUrl + `/additionalService/boatReservation/${id}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getAdditionalServicesByBoatQuickReservationId(
    id: number
  ): Observable<IAdditionalService[]> {
    return this._http
      .get<IAdditionalService[]>(
        environment.apiUrl + `/additionalService/boatQuickReservation/${id}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  addAdditionalServiceForBoat(additionalService:IAdditionalService,boat:IBoat): Observable<IAdditionalService> {
    return this._http.post<IAdditionalService>(environment.apiUrl + '/additionalService',{id:additionalService.id,name:additionalService.name,price:additionalService.price,boat:boat}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  addAdditionalServiceForBoatReservation(additionalService:IAdditionalService,boatReservation:IBoatReservation): Observable<IAdditionalService> {
    return this._http.post<IAdditionalService>(environment.apiUrl + '/additionalService',{id:additionalService.id,name:additionalService.name,price:additionalService.price,boatReservation:boatReservation}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  addAdditionalServiceForBoatQuickReservation(additionalService:IAdditionalService,boatQuickReservation:IBoatQuickReservation): Observable<IAdditionalService> {
    return this._http.post<IAdditionalService>(environment.apiUrl + '/additionalService',{id:additionalService.id,name:additionalService.name,price:additionalService.price,boatQuickReservation:boatQuickReservation}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
}
