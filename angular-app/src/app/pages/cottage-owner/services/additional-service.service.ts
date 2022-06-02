import { IFishingCourse } from './../../../model/fishingCourse';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IAdditionalService } from '../../../model/additionalService';
import { IFishingQuickReservation } from 'src/app/model/fishingQuickReservation';
import { IFishingReservation } from 'src/app/model/fishingReservation';

@Injectable({
  providedIn: 'root',
})
export class AdditionalServiceService {
  constructor(private _http: HttpClient) {}

  getFreeAdditionalServices(): Observable<IAdditionalService[]> {
    return this._http
      .get<IAdditionalService[]>(environment.apiUrl + '/additionalService/free')
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getAdditionalServicesByFishingCourseId(
    fishingCourseId: number
  ): Observable<IAdditionalService[]> {
    return this._http
      .get<IAdditionalService[]>(
        environment.apiUrl +
          `/additionalService/fishingCourse/${fishingCourseId}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getAdditionalServicesByFishingReservationId(
    id: number
  ): Observable<IAdditionalService[]> {
    return this._http
      .get<IAdditionalService[]>(
        environment.apiUrl + `/additionalService/fishingReservation/${id}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getAdditionalServicesByFishingQuickReservationId(
    id: number
  ): Observable<IAdditionalService[]> {
    return this._http
      .get<IAdditionalService[]>(
        environment.apiUrl + `/additionalService/fishingQuickReservation/${id}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  addAdditionalServiceForFishingCourse(
    additionalService: IAdditionalService,
    fishingCourse: IFishingCourse
  ): Observable<IAdditionalService> {
    return this._http
      .post<IAdditionalService>(environment.apiUrl + '/additionalService', {
        id: additionalService.id,
        name: additionalService.name,
        price: additionalService.price,
        fishingCourse: fishingCourse,
      })
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
    }

    
  addAdditionalServiceForFishingReservation(additionalService:IAdditionalService,fishingReservation:IFishingReservation): Observable<IAdditionalService> {
    return this._http.post<IAdditionalService>(environment.apiUrl + '/additionalService',{id:additionalService.id,name:additionalService.name,price:additionalService.price,fishingReservation:fishingReservation}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  addAdditionalServiceForFishingQuickReservation(additionalService:IAdditionalService,fishingQuickReservation:IFishingQuickReservation): Observable<IAdditionalService> {
    return this._http.post<IAdditionalService>(environment.apiUrl + '/additionalService',{id:additionalService.id,name:additionalService.name,price:additionalService.price,fishingQuickReservation:fishingQuickReservation}).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
}
