/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { FishingCourseEditComponent } from './fishing-course-edit.component';

describe('FishingCourseEditComponent', () => {
  let component: FishingCourseEditComponent;
  let fixture: ComponentFixture<FishingCourseEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FishingCourseEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FishingCourseEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
