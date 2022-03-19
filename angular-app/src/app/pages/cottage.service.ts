import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { ICottage } from './cottage-profile/cottage';

@Injectable({
  providedIn: 'root'
})
export class CottageService {
  private _cottageUrl = 'http://localhost:8080/cottage';
  constructor(private _http: HttpClient) { }

  getCottageById(cottageId: number): Observable<ICottage> {
    return this._http.get<ICottage>(this._cottageUrl + `/${cottageId}`).pipe(
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

  editCottage(cottage:ICottage): Observable<ICottage> {
    return this._http.post<ICottage>(this._cottageUrl,cottage).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(err.message);
  }
}
