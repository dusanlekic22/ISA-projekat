import { ISortType } from './../../../model/sortType';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { ICottage, ICottagePage } from 'src/app/model/cottage';
import { ICottageImage } from 'src/app/model/cottageImage';
import { IDateSpan } from 'src/app/model/dateSpan';
import { IReservationCount } from 'src/app/model/reservationCount';
import { IIncome } from 'src/app/model/income';

@Injectable({
  providedIn: 'root',
})
export class CottageService {
  constructor(private _http: HttpClient) {}

  getCottageById(cottageId: number): Observable<ICottage> {
    return this._http
      .get<ICottage>(environment.apiUrl + `/cottage/${cottageId}`)
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getCottagesByCottageOwnerId(cottageOwnerId: number): Observable<ICottage[]> {
    return this._http
      .get<ICottage[]>(environment.apiUrl + `/cottage/owner/${cottageOwnerId}`)
      .pipe(
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

  getCottagesPagination(
    page: number,
    sorts: ISortType[]
  ): Observable<ICottagePage> {
    return this._http
      .post<ICottagePage>(
        environment.apiUrl + `/cottage/pagination?page=` + page,
        sorts
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getAvailableCottagesSubscription(
    customerId: number,
    page: number
  ): Observable<ICottagePage> {
    return this._http
      .get<ICottagePage>(
        environment.apiUrl + `/cottage/subscription/${customerId}?page=` + page
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  saveCottage(cottage: ICottage): Observable<ICottage> {
    return this._http
      .post<ICottage>(environment.apiUrl + '/cottage', cottage)
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  editCottage(cottage: ICottage): Observable<ICottage> {
    return this._http
      .put<ICottage>(environment.apiUrl + '/cottage', cottage)
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  editCottageInfo(cottage: ICottage): Observable<ICottage> {
    return this._http
      .put<ICottage>(environment.apiUrl + '/cottage/info', cottage)
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  editAvailableTerms(
    cottageId: number,
    dateSpan: IDateSpan
  ): Observable<ICottage> {
    return this._http
      .put<ICottage>(
        environment.apiUrl + `/cottage/availableTerms/${cottageId}`,
        dateSpan
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  editUnavailableTerms(
    cottageId: number,
    dateSpan: IDateSpan
  ): Observable<ICottage> {
    return this._http
      .put<ICottage>(
        environment.apiUrl + `/cottage/unavailableTerms/${cottageId}`,
        dateSpan
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  deleteCottage(cottageId: number): Observable<ArrayBuffer> {
    return this._http
      .delete<ArrayBuffer>(environment.apiUrl + `/cottage/${cottageId}`)
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  addCottageImage(
    id: number,
    image: string,
    cottage: ICottage
  ): Observable<ICottageImage> {
    return this._http
      .post<ICottageImage>(environment.apiUrl + '/cottageImage', {
        id: id,
        image: image,
        cottage: cottage,
      })
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getCottageReservationYearlyById(
    cottageId: number
  ): Observable<IReservationCount> {
    return this._http
      .get<IReservationCount>(
        environment.apiUrl + `/cottage/${cottageId}/yearlyCount`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getCottageReservationMonthlyById(
    cottageId: number
  ): Observable<IReservationCount> {
    return this._http
      .get<IReservationCount>(
        environment.apiUrl + `/cottage/${cottageId}/monthlyCount`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getCottageReservationWeeklyById(
    cottageId: number
  ): Observable<IReservationCount> {
    return this._http
      .get<IReservationCount>(
        environment.apiUrl + `/cottage/${cottageId}/weeklyCount`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getCottageIncomeYearlyById(
    cottageId: number,
    duration: IDateSpan
  ): Observable<IIncome> {
    return this._http
      .post<IIncome>(
        environment.apiUrl + `/cottage/${cottageId}/yearlyIncome`,
        duration
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getCottageIncomeMonthlyById(
    cottageId: number,
    duration: IDateSpan
  ): Observable<IIncome> {
    return this._http
      .post<IIncome>(
        environment.apiUrl + `/cottage/${cottageId}/monthlyIncome`,
        duration
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getCottageIncomeDailyById(
    cottageId: number,
    duration: IDateSpan
  ): Observable<IIncome> {
    return this._http
      .post<IIncome>(
        environment.apiUrl + `/cottage/${cottageId}/dailyIncome`,
        duration
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  subscription(customerId: number, cottageId: number): Observable<ICottage> {
    return this._http
      .get<any>(
        environment.apiUrl + `/cottage/${cottageId}/subscribe/${customerId}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  unsubscription(customerId: number, cottageId: number): Observable<ICottage> {
    return this._http
      .get<any>(
        environment.apiUrl + `/cottage/${cottageId}/unsubscribe/${customerId}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
}
