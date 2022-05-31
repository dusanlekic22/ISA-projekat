import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FishingCourseStatisticsComponent } from './fishing-course-statistics.component';

describe('FishingCourseStatisticsComponent', () => {
  let component: FishingCourseStatisticsComponent;
  let fixture: ComponentFixture<FishingCourseStatisticsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FishingCourseStatisticsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FishingCourseStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
