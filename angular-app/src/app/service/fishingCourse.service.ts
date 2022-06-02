import { tap, catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { IFishingCourse, IFishingCoursePage } from './../model/fishingCourse';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError, Observable } from 'rxjs';
import { IFishingImage } from '../model/fishingImage';
import { ISortType } from '../model/sortType';

@Injectable({
  providedIn: 'root',
})
export class FishingCourseService {
  private fishingCourseUrl = `${environment.apiUrl}/fishingCourse`;
  private fishingImageUrl = `${environment.apiUrl}/fishingImage`;

  constructor(private http: HttpClient) {}

  getFishingCourses(): Observable<IFishingCourse[]> {
    return this.http
      .get<IFishingCourse[]>(`${this.fishingCourseUrl}`)
      .pipe(catchError(this.handleError));
  }

  getFishingCoursesPagination(
    page: number,
    sorts: ISortType[]
  ): Observable<IFishingCoursePage> {
    return this.http
      .post<IFishingCoursePage>(
        `${this.fishingCourseUrl}/pagination?page=${page}`,
        sorts
      )
      .pipe(
        tap((data) => console.log('All: ', JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getFishingCourseById(FishingCourseId: number): Observable<IFishingCourse> {
    return this.http
      .get<IFishingCourse>(`${this.fishingCourseUrl}/${FishingCourseId}`)
      .pipe(catchError(this.handleError));
  }

  saveFishingCourse(fishingCourse: IFishingCourse): Observable<IFishingCourse> {
    return this.http
      .post<IFishingCourse>(`${this.fishingCourseUrl}`, fishingCourse)
      .pipe(catchError(this.handleError));
  }

  updateFishingCourse(
    fishingCourse: IFishingCourse
  ): Observable<IFishingCourse> {
    return this.http
      .put<IFishingCourse>(`${this.fishingCourseUrl}/info`, fishingCourse)
      .pipe(catchError(this.handleError));
  }

  deleteFishingCourse(fishingCourseId: number): Observable<IFishingCourse> {
    return this.http
      .delete<IFishingCourse>(`${this.fishingCourseUrl}/${fishingCourseId}`)
      .pipe(catchError(this.handleError));
  }

  addFishingCourseImage(image: IFishingImage): Observable<IFishingImage> {
    return this.http
      .post<IFishingImage>(`${this.fishingImageUrl}`, image)
      .pipe(catchError(this.handleError));
  }

  searchFishingCoursesByName(name: string): Observable<IFishingCourse[]> {
    return this.http
      .get<IFishingCourse[]>(`${this.fishingCourseUrl}/search?name=${name}`)
      .pipe(catchError(this.handleError));
  }

  getFishingTrainerCourses(
    fishingTrainerId: number
  ): Observable<IFishingCourse[]> {
    return this.http
      .get<IFishingCourse[]>(
        `${this.fishingCourseUrl}/owner/${fishingTrainerId}`
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
