import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerFishingCourseProfileComponent } from './customer-fishing-course-profile.component';

describe('CustomerFishingCourseProfileComponent', () => {
  let component: CustomerFishingCourseProfileComponent;
  let fixture: ComponentFixture<CustomerFishingCourseProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomerFishingCourseProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerFishingCourseProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
