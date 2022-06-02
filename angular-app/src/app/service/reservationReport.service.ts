import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IReservationReport } from '../model/reservationReport';

@Injectable({
  providedIn: 'root',
})
export class ReservationReportService {
  private reservationReportRequestsUrl = `${environment.apiUrl}/reservationReport`;
  public openDialogReport$: EventEmitter<IReservationReport>;
  public submitedDialogReport$: EventEmitter<IReservationReport>;

  constructor(private http: HttpClient) {
    this.openDialogReport$ = new EventEmitter();
    this.submitedDialogReport$ = new EventEmitter();
  }

  openDialogReport(report: IReservationReport) {
    this.openDialogReport$.emit(report);
  }

  submitedReportRequest() {
    this.submitedDialogReport$.emit();
  }

  createReservationReportRequest(
    request: IReservationReport
  ): Observable<IReservationReport> {
    return this.http
      .post<IReservationReport>(`${this.reservationReportRequestsUrl}`, request)
      .pipe(catchError(this.handleError));
  }

  getAllReservationReports(): Observable<IReservationReport[]> {
    return this.http
      .get<IReservationReport[]>(`${this.reservationReportRequestsUrl}`)
      .pipe(catchError(this.handleError));
  }

  approveReservationReportRequest(
    request: IReservationReport
  ): Observable<IReservationReport> {
    return this.http
      .post<IReservationReport>(
        `${this.reservationReportRequestsUrl}/approve`,
        request
      )
      .pipe(catchError(this.handleError));
  }

  declineReservationReportRequest(
    request: IReservationReport
  ): Observable<IReservationReport> {
    return this.http
      .post<IReservationReport>(
        `${this.reservationReportRequestsUrl}/decline`,
        request
      )
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
