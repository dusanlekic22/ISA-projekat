import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { ICottage } from '../cottage-profile/cottage';
import { ICottageImage } from '../cottage-profile/cottageImage';
import { IAdditionalService } from '../cottage-profile/additionalService';
import { ICottageReservation } from '../cottage-profile/cottageReservation';
import { environment } from 'src/environments/environment';

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

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
}
