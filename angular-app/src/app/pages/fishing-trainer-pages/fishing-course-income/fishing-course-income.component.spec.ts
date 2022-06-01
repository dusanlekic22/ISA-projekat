import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FishingCourseIncomeComponent } from './fishing-course-income.component';

describe('FishingCourseIncomeComponent', () => {
  let component: FishingCourseIncomeComponent;
  let fixture: ComponentFixture<FishingCourseIncomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FishingCourseIncomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FishingCourseIncomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
