/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { FishingCourseProfileComponent } from './fishing-course-profile.component';

describe('FishingCourseProfileComponent', () => {
  let component: FishingCourseProfileComponent;
  let fixture: ComponentFixture<FishingCourseProfileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FishingCourseProfileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FishingCourseProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
