import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ILoyaltySettings } from '../model/loyaltySettings';

@Injectable({
  providedIn: 'root',
})
export class LoyaltySettingsService {
  private loyaltySettingsUrl = `${environment.apiUrl}/loyaltySettings`;

  constructor(private http: HttpClient) {}

  getLoyaltySettings(): Observable<ILoyaltySettings> {
    return this.http
      .get<ILoyaltySettings>(`${this.loyaltySettingsUrl}`)
      .pipe(catchError(this.handleError));
  }

  editLoyaltySettings(
    loyaltySettings: ILoyaltySettings
  ): Observable<ILoyaltySettings> {
    return this.http
      .put<ILoyaltySettings>(`${this.loyaltySettingsUrl}`, loyaltySettings)
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
