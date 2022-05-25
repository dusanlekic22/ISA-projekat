/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { AddFishingCourseComponent } from './add-fishing-course.component';

describe('AddFishingCourseComponent', () => {
  let component: AddFishingCourseComponent;
  let fixture: ComponentFixture<AddFishingCourseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddFishingCourseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddFishingCourseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
