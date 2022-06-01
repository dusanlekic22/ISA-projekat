import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { IBoatOwner } from 'src/app/model/boat/boatOwner';
import { IDateSpan } from 'src/app/model/dateSpan';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BoatOwnerService {
  private boatOwnerUrl = `${environment.apiUrl}/boatOwner`;

  constructor(private http: HttpClient) {}

  getBoatOwner(): Observable<IBoatOwner> {
    return this.http
      .get<IBoatOwner>(`${this.boatOwnerUrl}`)
      .pipe(catchError(this.handleError));
  }

  editUnavailableTerms(
    id: number,
    dateSpan: IDateSpan
  ): Observable<IBoatOwner> {
    return this.http
      .put<IBoatOwner>(
        `${this.boatOwnerUrl}/unavailableTerms/${id}`,
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
