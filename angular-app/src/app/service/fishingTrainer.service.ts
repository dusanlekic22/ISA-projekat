import { IFishingTrainer } from './../model/fishingTrainer';
import { throwError, catchError, Observable } from 'rxjs';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class FishingTrainerService {
  private fishingTrainerUrl = `${environment.apiUrl}/fishingTrainer`;

  constructor(private http: HttpClient) {}

  getFishingTrainer(): Observable<IFishingTrainer> {
    return this.http
      .get<IFishingTrainer>(`${this.fishingTrainerUrl}`)
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
    window.alert(errorMessage);
    return throwError(() => {
      return errorMessage;
    });
  }
}
