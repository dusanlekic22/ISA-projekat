import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { ICottageOwner } from 'src/app/model/cottageOwner';
import { IDateSpan } from 'src/app/model/dateSpan';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CottageOwnerService {
  private cottageOwnerUrl = `${environment.apiUrl}/cottageOwner`;

  constructor(private http: HttpClient) {}

  getCottageOwner(): Observable<ICottageOwner> {
    return this.http
      .get<ICottageOwner>(`${this.cottageOwnerUrl}`)
      .pipe(catchError(this.handleError));
  }

  editUnavailableTerms(
    id: number,
    dateSpan: IDateSpan
  ): Observable<ICottageOwner> {
    return this.http
      .put<ICottageOwner>(
        `${this.cottageOwnerUrl}/unavailableTerms/${id}`,
        dateSpan
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
