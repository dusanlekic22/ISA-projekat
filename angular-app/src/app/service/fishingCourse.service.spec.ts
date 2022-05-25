/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { FishingCourseService } from './fishingCourse.service';

describe('Service: FishingCourse', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FishingCourseService]
    });
  });

  it('should ...', inject([FishingCourseService], (service: FishingCourseService) => {
    expect(service).toBeTruthy();
  }));
});
