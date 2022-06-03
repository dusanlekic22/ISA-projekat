import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FishingCourseReservationComponent } from './fishing-course-reservation.component';

describe('FishingCourseReservationComponent', () => {
  let component: FishingCourseReservationComponent;
  let fixture: ComponentFixture<FishingCourseReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FishingCourseReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FishingCourseReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
