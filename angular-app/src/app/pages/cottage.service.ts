import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { ICottage } from './cottage-profile/cottage';
import { ICottageImage } from './cottage-profile/cottageImage';
import { ICottageQuickReservation } from './cottage-profile/cottageQuickReservation';
import { IAdditionalService } from './cottage-profile/additionalService';

@Injectable({
  providedIn: 'root'
})
export class CottageService {
  private _cottageUrl = 'http://localhost:8080/cottage';
  private _additionalServiceUrl = 'http://localhost:8080/additionalService';
  constructor(private _http: HttpClient) { }

  getCottageById(cottageId: number): Observable<ICottage> {
    return this._http.get<ICottage>(this._cottageUrl + `/${cottageId}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  getCottagesByCottageOwnerId(cottageOwnerId: number): Observable<ICottage[]> {
    return this._http.get<ICottage[]>(this._cottageUrl + `/owner/${cottageOwnerId}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }


  getCottages(): Observable<ICottage[]> {
    return this._http.get<ICottage[]>(this._cottageUrl).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  saveCottage(cottage:ICottage): Observable<ICottage> {
    return this._http.post<ICottage>(this._cottageUrl+'/save',cottage).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  editCottage(cottage:ICottage): Observable<ICottage> {
    return this._http.post<ICottage>(this._cottageUrl+'/update',cottage).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  deleteCottage(cottageId:number): Observable<ArrayBuffer> {
    return this._http.delete<ArrayBuffer>(this._cottageUrl+ `/${cottageId}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  addCottageImage(id:number,image:string,cottage:ICottage): Observable<ICottageImage> {
    return this._http.post<ICottageImage>(this._cottageUrl+'Image',{id:id,image:image,cottage:cottage}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  addCottageQuickReservation(cottageQuickReservation:ICottageQuickReservation,cottage:ICottage): Observable<ICottageQuickReservation> {
    return this._http.post<ICottageQuickReservation>(this._cottageUrl+'QuickReservation',
    {id:cottageQuickReservation.id,dateSpan:cottageQuickReservation.dateSpan,
      guestCapacity:cottageQuickReservation.guestCapacity,price:cottageQuickReservation.price,cottage:cottage}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  getCottageQuickReservations(): Observable<ICottageQuickReservation[]> {
    return this._http.get<ICottageQuickReservation[]>(this._cottageUrl+'QuickReservation',).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  getFreeAdditionalServices(): Observable<IAdditionalService[]> {
    return this._http.get<IAdditionalService[]>(this._additionalServiceUrl+'/free',).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  addAdditionalService(additionalService:IAdditionalService,cottage:ICottage): Observable<IAdditionalService> {
    return this._http.post<IAdditionalService>(this._additionalServiceUrl,{id:additionalService.id,name:additionalService.name,price:additionalService.price,cottage:cottage}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(err.message);
  }
}
