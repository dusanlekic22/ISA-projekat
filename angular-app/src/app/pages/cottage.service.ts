import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { ICottage } from './cottage-profile/cottage';
import { ICottageImage } from './cottage-profile/cottageImage';
import { ICottageQuickReservation } from './cottage-profile/cottageQuickReservation';
import { IAdditionalService } from './cottage-profile/additionalService';
import { ICottageReservation } from './cottage-profile/cottageReservation';

@Injectable({
  providedIn: 'root'
})
export class CottageService {
  constructor(private _http: HttpClient) { }

  getCottageById(cottageId: number): Observable<ICottage> {
    return this._http.get<ICottage>(environment.apiUrl + `/cottage/${cottageId}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  getCottagesByCottageOwnerId(cottageOwnerId: number): Observable<ICottage[]> {
    return this._http.get<ICottage[]>(environment.apiUrl + `/cottage/owner/${cottageOwnerId}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }


  getCottages(): Observable<ICottage[]> {
    return this._http.get<ICottage[]>(environment.apiUrl + `/cottage`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  saveCottage(cottage:ICottage): Observable<ICottage> {
    return this._http.post<ICottage>(environment.apiUrl + '/cottage',cottage).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  editCottage(cottage:ICottage): Observable<ICottage> {
    return this._http.put<ICottage>(environment.apiUrl + '/cottage',cottage).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  editCottageInfo(cottage:ICottage): Observable<ICottage> {
    return this._http.put<ICottage>(environment.apiUrl + '/cottage/info',cottage).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  deleteCottage(cottageId:number): Observable<ArrayBuffer> {
    return this._http.delete<ArrayBuffer>(environment.apiUrl + `/cottage/${cottageId}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  addCottageImage(id:number,image:string,cottage:ICottage): Observable<ICottageImage> {
    return this._http.post<ICottageImage>(environment.apiUrl + '/cottageImage',{id:id,image:image,cottage:cottage}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  addCottageQuickReservation(cottageQuickReservation:ICottageQuickReservation,cottage:ICottage): Observable<ICottageQuickReservation> {
    return this._http.post<ICottageQuickReservation>(environment.apiUrl + '/cottageQuickReservation',
    {id:cottageQuickReservation.id,duration:cottageQuickReservation.duration,validSpan:cottageQuickReservation.validSpan,
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

  addAdditionalService(additionalService:IAdditionalService,cottage:ICottage): Observable<IAdditionalService> {
    return this._http.post<IAdditionalService>(environment.apiUrl + '/additionalService',{id:additionalService.id,name:additionalService.name,price:additionalService.price,cottage:cottage}).pipe(
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

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
}
