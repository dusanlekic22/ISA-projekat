import { ICottageReservation } from './../../../model/cottageReservation';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, catchError, throwError } from 'rxjs';
import { IAdditionalService } from 'src/app/model/additionalService';
import { ICottage } from 'src/app/model/cottage';
import { environment } from 'src/environments/environment';
import { ICottageQuickReservation } from 'src/app/model/cottageQuickReservation';

@Injectable({
  providedIn: 'root'
})
export class CottageAdditionalServicesService {

  constructor(private _http: HttpClient) { }

  getAdditionalServicesByCottageId(
    id: number
  ): Observable<IAdditionalService[]> {
    return this._http
      .get<IAdditionalService[]>(
        environment.apiUrl + `/additionalService/cottage/${id}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getAdditionalServicesByCottageReservationId(
    id: number
  ): Observable<IAdditionalService[]> {
    return this._http
      .get<IAdditionalService[]>(
        environment.apiUrl + `/additionalService/cottageReservation/${id}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getAdditionalServicesByCottageQuickReservationId(
    id: number
  ): Observable<IAdditionalService[]> {
    return this._http
      .get<IAdditionalService[]>(
        environment.apiUrl + `/additionalService/cottageQuickReservation/${id}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  addAdditionalServiceForCottage(additionalService:IAdditionalService,cottage:ICottage): Observable<IAdditionalService> {
    return this._http.post<IAdditionalService>(environment.apiUrl + '/additionalService',{id:additionalService.id,name:additionalService.name,price:additionalService.price,cottage:cottage}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  addAdditionalServiceForCottageReservation(additionalService:IAdditionalService,cottageReservation:ICottageReservation): Observable<IAdditionalService> {
    return this._http.post<IAdditionalService>(environment.apiUrl + '/additionalService',{id:additionalService.id,name:additionalService.name,price:additionalService.price,cottageReservation:cottageReservation}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  addAdditionalServiceForCottageQuickReservation(additionalService:IAdditionalService,cottageQuickReservation:ICottageQuickReservation): Observable<IAdditionalService> {
    return this._http.post<IAdditionalService>(environment.apiUrl + '/additionalService',{id:additionalService.id,name:additionalService.name,price:additionalService.price,cottageQuickReservation:cottageQuickReservation}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }

}
