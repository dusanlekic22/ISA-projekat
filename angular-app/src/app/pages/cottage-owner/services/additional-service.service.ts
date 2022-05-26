import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, catchError, throwError } from 'rxjs';
import { IBoat } from 'src/app/model/boat/boat';
import { ICottage } from 'src/app/model/cottage';
import { environment } from 'src/environments/environment';
import { IAdditionalService } from '../../../model/additionalService';

@Injectable({
  providedIn: 'root'
})
export class AdditionalServiceService {

  constructor(private _http: HttpClient) { }

  getFreeAdditionalServices(): Observable<IAdditionalService[]> {
    return this._http.get<IAdditionalService[]>(environment.apiUrl + '/additionalService/free',).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  getAdditionalServicesByCottageId(cottageId:number): Observable<IAdditionalService[]> {
    return this._http.get<IAdditionalService[]>(environment.apiUrl + `/additionalService/cottage/${cottageId}`,).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  getAdditionalServicesByBoatId(boatId:number): Observable<IAdditionalService[]> {
    return this._http.get<IAdditionalService[]>(environment.apiUrl + `/additionalService/boat/${boatId}`,).pipe(
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

  addAdditionalServiceForBoat(additionalService:IAdditionalService,boat:IBoat): Observable<IAdditionalService> {
    return this._http.post<IAdditionalService>(environment.apiUrl + '/additionalService',{id:additionalService.id,name:additionalService.name,price:additionalService.price,boat:boat}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
}
