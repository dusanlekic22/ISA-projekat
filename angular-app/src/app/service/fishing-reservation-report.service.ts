import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IFishingReservationReport } from '../model/fishingReservationReport';

@Injectable({
  providedIn: 'root',
})
export class FishingReservationReportService {
  private fishingReservationReportRequestsUrl = `${environment.apiUrl}/fishingReservationReport`;
  public openDialogReport$: EventEmitter<IFishingReservationReport>;
  public submitedDialogReport$: EventEmitter<IFishingReservationReport>;

  constructor(private http: HttpClient) {
    this.openDialogReport$ = new EventEmitter();
    this.submitedDialogReport$ = new EventEmitter();
  }

  openDialogReport(report: IFishingReservationReport) {
    this.openDialogReport$.emit(report);
  }

  submitedReportRequest() {
    this.submitedDialogReport$.emit();
  }

  createReservationReportRequest(
    request: IFishingReservationReport
  ): Observable<IFishingReservationReport> {
    return this.http
      .post<IFishingReservationReport>(
        `${this.fishingReservationReportRequestsUrl}`,
        request
      )
      .pipe(catchError(this.handleError));
  }

  getAllFishingReservationReports(): Observable<IFishingReservationReport[]> {
    return this.http
      .get<IFishingReservationReport[]>(`${this.fishingReservationReportRequestsUrl}`)
      .pipe(catchError(this.handleError));
  }

  approveReservationReportRequest(
    request: IFishingReservationReport
  ): Observable<IFishingReservationReport> {
    return this.http
      .post<IFishingReservationReport>(
        `${this.fishingReservationReportRequestsUrl}/approve`,
        request
      )
      .pipe(catchError(this.handleError));
  }

  declineReservationReportRequest(
    request: IFishingReservationReport
  ): Observable<IFishingReservationReport> {
    return this.http
      .post<IFishingReservationReport>(
        `${this.fishingReservationReportRequestsUrl}/decline`,
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
