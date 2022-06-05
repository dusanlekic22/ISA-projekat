import { IComplaint } from 'src/app/model/complaint';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ComplaintService {
  private complaintUrl = `${environment.apiUrl}/complaint`;

  constructor(private http: HttpClient) {}

  sendComplaint(complaint: IComplaint): Observable<any> {
    return this.http
      .post<IComplaint>(`${this.complaintUrl}`, complaint)
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
