import { environment } from './../../environments/environment';
import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ICustomer } from '../model/customer';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  private _customerUrl = environment.apiUrl + '/customer';
  constructor(private _http: HttpClient) {}

  getCustomerById(customerId: number): Observable<ICustomer> {
    return this._http.get<ICustomer>(this._customerUrl + `/${customerId}`).pipe(
      tap((data) => console.log('All: ', JSON.stringify(data))),
      catchError(this.handleError)
    );
  }

  private handleError(err: HttpErrorResponse) {
    console.log(err.message);
    return throwError(() => new Error(err.message));
  }
}
