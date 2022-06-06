import { IComplaint } from 'src/app/model/complaint';
import { IUserDeletionRequest } from './../model/userDeletionRequest';
import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IBusinessOwnerRegistrationRequest } from '../model/businessOwnerRegistrationRequest';
import { IReservationReport } from '../model/reservationReport';
import { IGrade } from '../model/grade';

@Injectable({
  providedIn: 'root',
})
export class RequestsService {
  private registrationRequestsUrl = `${environment.apiUrl}/user`;
  private userDeletionRequestsUrl = `${environment.apiUrl}/userDeletion`;
  public openDialogRegistration$: EventEmitter<IBusinessOwnerRegistrationRequest>;
  public submitedRegistrationRequest$: EventEmitter<IBusinessOwnerRegistrationRequest>;

  public openDialogDeletion$: EventEmitter<IUserDeletionRequest>;
  public submitedDeletionRequest$: EventEmitter<IUserDeletionRequest>;

  public openDialogReport$: EventEmitter<IReservationReport>;
  public submitedDialogReport$: EventEmitter<IReservationReport>;

  public openDialogComplaint$: EventEmitter<IComplaint>;
  public submitedDialogComplaint$: EventEmitter<IComplaint>;

  public openDialogGrade$: EventEmitter<IGrade>;
  public submitedDialogGrade$: EventEmitter<IGrade>;

  constructor(private http: HttpClient) {
    this.openDialogRegistration$ = new EventEmitter();
    this.submitedRegistrationRequest$ = new EventEmitter();
    this.openDialogDeletion$ = new EventEmitter();
    this.submitedDeletionRequest$ = new EventEmitter();
    this.openDialogReport$ = new EventEmitter();
    this.submitedDialogReport$ = new EventEmitter();
    this.openDialogComplaint$ = new EventEmitter();
    this.submitedDialogComplaint$ = new EventEmitter();
    this.openDialogGrade$ = new EventEmitter();
    this.submitedDialogGrade$ = new EventEmitter();
  }

  openDialogRegistration(request: IBusinessOwnerRegistrationRequest) {
    this.openDialogRegistration$.emit(request);
  }

  submitedRegistrationRequest() {
    this.submitedRegistrationRequest$.emit();
  }

  openDialogDeletion(request: IUserDeletionRequest) {
    this.openDialogDeletion$.emit(request);
  }

  submitedDeletionRequest() {
    this.submitedDeletionRequest$.emit();
  }

  openDialogReport(report: IReservationReport) {
    this.openDialogReport$.emit(report);
  }

  submitedReportRequest() {
    this.submitedDialogReport$.emit();
  }

  openDialogComplaint(report: IComplaint) {
    this.openDialogComplaint$.emit(report);
  }

  submitedComplaintRequest() {
    this.submitedDialogComplaint$.emit();
  }

  openDialogGrade(report: IGrade) {
    this.openDialogGrade$.emit(report);
  }

  submitedGradeRequest() {
    this.submitedDialogGrade$.emit();
  }

  getBusinessOwnerRegistrationRequests(): Observable<
    IBusinessOwnerRegistrationRequest[]
  > {
    return this.http
      .get<IBusinessOwnerRegistrationRequest[]>(
        `${this.registrationRequestsUrl}/registrationRequests`
      )
      .pipe(catchError(this.handleError));
  }

  getUserDeletionRequests(): Observable<IUserDeletionRequest[]> {
    return this.http
      .get<IUserDeletionRequest[]>(
        `${this.userDeletionRequestsUrl}/deletionRequests`
      )
      .pipe(catchError(this.handleError));
  }

  acceptRegistrationRequest(
    request: IBusinessOwnerRegistrationRequest
  ): Observable<IBusinessOwnerRegistrationRequest> {
    request.declineReason = '';
    return this.http
      .post<IBusinessOwnerRegistrationRequest>(
        `${this.registrationRequestsUrl}/acceptUser`,
        request
      )
      .pipe(catchError(this.handleError));
  }

  declineRegistrationRequest(
    request: IBusinessOwnerRegistrationRequest
  ): Observable<IBusinessOwnerRegistrationRequest> {
    return this.http
      .post<IBusinessOwnerRegistrationRequest>(
        `${this.registrationRequestsUrl}/declineUser`,
        request
      )
      .pipe(catchError(this.handleError));
  }

  acceptDeletionRequest(
    request: IUserDeletionRequest
  ): Observable<IUserDeletionRequest> {
    return this.http
      .post<IUserDeletionRequest>(
        `${this.userDeletionRequestsUrl}/accept`,
        request
      )
      .pipe(catchError(this.handleError));
  }

  declineDeletionRequest(
    request: IUserDeletionRequest
  ): Observable<IUserDeletionRequest> {
    return this.http
      .post<IUserDeletionRequest>(
        `${this.userDeletionRequestsUrl}/decline`,
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
