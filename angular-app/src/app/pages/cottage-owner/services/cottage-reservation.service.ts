import { IReview } from 'src/app/model/review';
import { ISortType } from 'src/app/model/sortType';
import { ICustomer } from './../../../model/customer';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ICottageReservation } from 'src/app/model/cottageReservation';
import { ICottage } from 'src/app/model/cottage';
import { IGrade } from 'src/app/model/grade';

@Injectable({
  providedIn: 'root',
})
export class CottageReservationService {
  constructor(private _http: HttpClient) {}

  addCottageReservation(
    cottageReservation: ICottageReservation,
    cottage: ICottage
  ): Observable<ICottageReservation> {
    return this._http
      .post<ICottageReservation>(
        environment.apiUrl + '/cottageReservation/owner',
        {
          id: cottageReservation.id,
          duration: cottageReservation.duration,
          guestCapacity: cottageReservation.guestCapacity,
          price: cottageReservation.price,
          customer: cottageReservation.customer,
          confirmed: cottageReservation.confirmed,
          cottage: cottage,
        }
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  addCottageReservationCustomer(
    cottageReservation: ICottageReservation,
    cottage: ICottage
  ): Observable<ICottageReservation> {
    return this._http
      .post<ICottageReservation>(
        environment.apiUrl + '/cottageReservation/customer',
        {
          id: cottageReservation.id,
          duration: cottageReservation.duration,
          guestCapacity: cottageReservation.guestCapacity,
          price: cottageReservation.price,
          customer: cottageReservation.customer,
          confirmed: cottageReservation.confirmed,
          cottage: cottage,
        }
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getPassedCottageReservationByCottageId(
    id: number
  ): Observable<ICottageReservation[]> {
    return this._http
      .get<ICottageReservation[]>(
        environment.apiUrl + `/cottageReservation/passed/${id}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getActiveCottageReservationByCottageId(
    id: number
  ): Observable<ICottageReservation[]> {
    return this._http
      .get<ICottageReservation[]>(
        environment.apiUrl + `/cottageReservation/active/${id}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getAvailableCottagesReservation(
    customerId: number,
    sortBy: ISortType,
    page: number
  ): Observable<ICottageReservation[]> {
    return this._http
      .post<ICottageReservation[]>(
        environment.apiUrl +
          `/cottageReservation/customer/${customerId}?page=${page}`,
        sortBy
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }
  getAvailableCottagesReservationIncoming(
    customerId: number,
    sortBy: ISortType,
    page: number
  ): Observable<ICottageReservation[]> {
    return this._http
      .post<ICottageReservation[]>(
        environment.apiUrl +
          `/cottageReservation/incoming/customer/${customerId}?page=${page}`,
        sortBy
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getCustomerHasReservationNow(id: number): Observable<ICustomer[]> {
    return this._http
      .get<ICustomer[]>(
        environment.apiUrl +
          `/cottageReservation/customerHasReservationNow/cottage/${id}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  deleteCottageReservation(cottageId: number): Observable<ArrayBuffer> {
    return this._http
      .delete<ArrayBuffer>(
        environment.apiUrl + `/cottageReservation/${cottageId}`
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getActiveCottageReservationByCottageByCottageOwnerId(
    id: number
  ): Observable<ICottageReservation[]> {
    return this._http
      .get<ICottageReservation[]>(
        environment.apiUrl + `/cottageReservation/active/owner/${id}`
      )
      .pipe(catchError(this.handleError));
  }

  getPassedCottageReservationByCottageByCottageOwnerId(
    id: number
  ): Observable<ICottageReservation[]> {
    return this._http
      .get<ICottageReservation[]>(
        environment.apiUrl + `/cottageReservation/passed/owner/${id}`
      )
      .pipe(catchError(this.handleError));
  }

  sendCottageReview(gradeDTO: IGrade): Observable<ICottage> {
    return this._http
      .post<any>(
        environment.apiUrl + `/cottage/${gradeDTO.cottage?.id}/grade`,
        gradeDTO
      )
      .pipe(catchError(this.handleError));
  }
  sendCottageOwnerReview(gradeDTO: IGrade): Observable<ICottage> {
    return this._http
      .post<any>(
        environment.apiUrl + `/cottageOwner/${gradeDTO.cottageOwner?.id}/grade`,
        gradeDTO
      )
      .pipe(catchError(this.handleError));
  }

  cancelCottageReservation(
    cottageReservation: ICottageReservation
  ): Observable<ICottageReservation> {
    return this._http
      .post<ICottageReservation>(
        environment.apiUrl +
          `/cottageReservation/${cottageReservation.id}/cancel`,
        cottageReservation
      )
      .pipe(catchError(this.handleError));
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error('Error'));
  }
}
