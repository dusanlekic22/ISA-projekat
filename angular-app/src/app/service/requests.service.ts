import { HttpClient } from '@angular/common/http';
import { EventEmitter, Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IBusinessOwnerRegistrationRequest } from '../model/business-owner-registration-request';

@Injectable({
  providedIn: 'root',
})
export class RequestsService {
  private fishingReservationUrl = `${environment.apiUrl}/user`;
  public openDialog$: EventEmitter<IBusinessOwnerRegistrationRequest>;
  public submitedRequest$: EventEmitter<IBusinessOwnerRegistrationRequest>;

  constructor(private http: HttpClient) {
    this.openDialog$ = new EventEmitter();
    this.submitedRequest$ = new EventEmitter();
  }

  openDialog(request: IBusinessOwnerRegistrationRequest) {
    this.openDialog$.emit(request);
  }

  submitedRequest() {
    this.submitedRequest$.emit();
  }

  getBusinessOwnerRegistrationRequests(): Observable<
    IBusinessOwnerRegistrationRequest[]
  > {
    return this.http
      .get<IBusinessOwnerRegistrationRequest[]>(
        `${this.fishingReservationUrl}/registrationRequests`
      )
      .pipe(catchError(this.handleError));
  }

  acceptRequest(
    request: IBusinessOwnerRegistrationRequest
  ): Observable<IBusinessOwnerRegistrationRequest> {
    request.declineReason = '';
    return this.http
      .post<IBusinessOwnerRegistrationRequest>(
        `${this.fishingReservationUrl}/acceptUser`,
        request
      )
      .pipe(catchError(this.handleError));
  }

  declineRequest(
    request: IBusinessOwnerRegistrationRequest
  ): Observable<IBusinessOwnerRegistrationRequest> {
    return this.http
      .post<IBusinessOwnerRegistrationRequest>(
        `${this.fishingReservationUrl}/declineUser`,
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
