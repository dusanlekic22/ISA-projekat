import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IGrade } from '../model/grade';

@Injectable({
  providedIn: 'root',
})
export class GradeService {
  private gradeRequestsUrl = `${environment.apiUrl}/grade`;

  constructor(private http: HttpClient) {}

  getAllGrades(): Observable<IGrade[]> {
    return this.http
      .get<IGrade[]>(`${this.gradeRequestsUrl}`)
      .pipe(catchError(this.handleError));
  }

  approveGradeRequest(request: IGrade): Observable<IGrade> {
    return this.http
      .post<IGrade>(`${this.gradeRequestsUrl}/approve`, request)
      .pipe(catchError(this.handleError));
  }

  declineGradeRequest(request: IGrade): Observable<IGrade> {
    return this.http
      .post<IGrade>(`${this.gradeRequestsUrl}/decline`, request)
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
