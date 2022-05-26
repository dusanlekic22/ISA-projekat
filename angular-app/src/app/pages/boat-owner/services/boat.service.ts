
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { IDateSpan } from 'src/app/model/dateSpan';
import { IBoat } from 'src/app/model/boat/boat';
import { IBoatImage } from 'src/app/model/boat/boatImage';

@Injectable({
  providedIn: 'root'
})
export class BoatService {
  constructor(private _http: HttpClient) { }

  getBoatById(boatId: number): Observable<IBoat> {
    return this._http.get<IBoat>(environment.apiUrl + `/boat/${boatId}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  getBoatsByBoatOwnerId(boatOwnerId: number): Observable<IBoat[]> {
    return this._http.get<IBoat[]>(environment.apiUrl + `/boat/owner/${boatOwnerId}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }


  getBoats(): Observable<IBoat[]> {
    return this._http.get<IBoat[]>(environment.apiUrl + `/boat`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  saveBoat(boat:IBoat): Observable<IBoat> {
    return this._http.post<IBoat>(environment.apiUrl + '/boat',boat).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  editBoat(boat:IBoat): Observable<IBoat> {
    return this._http.put<IBoat>(environment.apiUrl + '/boat',boat).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  editBoatInfo(boat:IBoat): Observable<IBoat> {
    return this._http.put<IBoat>(environment.apiUrl + '/boat/info',boat).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  editAvailableTerms(boatId:number,dateSpan:IDateSpan): Observable<IBoat> {
    return this._http.put<IBoat>(environment.apiUrl + `/boat/availableTerms/${boatId}`,dateSpan).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  deleteBoat(boatId:number): Observable<ArrayBuffer> {
    return this._http.delete<ArrayBuffer>(environment.apiUrl + `/boat/${boatId}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  addBoatImage(id:number,image:string,boat:IBoat): Observable<IBoatImage> {
    return this._http.post<IBoatImage>(environment.apiUrl + '/boatImage',{id:id,image:image,boat:boat}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
}
