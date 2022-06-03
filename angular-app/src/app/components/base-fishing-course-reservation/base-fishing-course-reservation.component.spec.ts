import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseFishingCourseReservationComponent } from './base-fishing-course-reservation.component';

describe('BaseFishingCourseReservationComponent', () => {
  let component: BaseFishingCourseReservationComponent;
  let fixture: ComponentFixture<BaseFishingCourseReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BaseFishingCourseReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseFishingCourseReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
