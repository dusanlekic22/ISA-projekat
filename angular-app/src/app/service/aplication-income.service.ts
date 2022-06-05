import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IDateSpan } from '../model/dateSpan';
import { IIncome } from '../model/income';

@Injectable({
  providedIn: 'root',
})
export class AplicationIncomeService {
  private aplicationIncomeUrl = `${environment.apiUrl}/aplicationIncome`;

  constructor(private http: HttpClient) {}

  getIncomeYearly(duration: IDateSpan): Observable<IIncome> {
    return this.http
      .post<IIncome>(`${this.aplicationIncomeUrl}/yearlyIncome`, duration)
      .pipe(catchError(this.handleError));
  }

  getIncomeMonthly(duration: IDateSpan): Observable<IIncome> {
    return this.http
      .post<IIncome>(`${this.aplicationIncomeUrl}/monthlyIncome`, duration)
      .pipe(catchError(this.handleError));
  }

  getIncomeWeeklyDaily(duration: IDateSpan): Observable<IIncome> {
    return this.http
      .post<IIncome>(`${this.aplicationIncomeUrl}/dailyIncome`, duration)
      .pipe(catchError(this.handleError));
  }

  handleError(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => {
      return errorMessage;
    });
  }
}
