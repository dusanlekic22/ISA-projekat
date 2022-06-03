import {
  IFishingTrainer,
  IFishingTrainerPage,
} from './../model/fishingTrainer';
import { throwError, catchError, Observable, tap } from 'rxjs';
import { environment } from './../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IDateSpan } from '../model/dateSpan';
import { ISortType } from '../model/sortType';

@Injectable({
  providedIn: 'root',
})
export class FishingTrainerService {
  private fishingTrainerUrl = `${environment.apiUrl}/fishingTrainer`;

  constructor(private http: HttpClient) {}

  getAllFishingTrainers(): Observable<IFishingTrainer[]> {
    return this.http
      .get<IFishingTrainer[]>(`${this.fishingTrainerUrl}/all`)
      .pipe(catchError(this.handleError));
  }

  getFishingTrainer(): Observable<IFishingTrainer> {
    return this.http
      .get<IFishingTrainer>(`${this.fishingTrainerUrl}`)
      .pipe(catchError(this.handleError));
  }

  getFishingTrainersPagination(
    page: number,
    sorts: ISortType[]
  ): Observable<IFishingTrainerPage> {
    return this.http
      .post<IFishingTrainerPage>(
        environment.apiUrl + `/fishingTrainer/pagination?page=` + page,
        sorts
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  editAvailableTerms(
    id: number,
    dateSpan: IDateSpan
  ): Observable<IFishingTrainer> {
    return this.http
      .put<IFishingTrainer>(
        `${this.fishingTrainerUrl}/availableTerms/${id}`,
        dateSpan
      )
      .pipe(catchError(this.handleError));
  }

  editUnavailableTerms(
    id: number,
    dateSpan: IDateSpan
  ): Observable<IFishingTrainer> {
    return this.http
      .put<IFishingTrainer>(
        `${this.fishingTrainerUrl}/unavailableTerms/${id}`,
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
