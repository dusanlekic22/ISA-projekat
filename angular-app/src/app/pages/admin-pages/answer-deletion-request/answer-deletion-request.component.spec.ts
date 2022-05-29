/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { AnswerDeletionRequestComponent } from './answer-deletion-request.component';

describe('AnswerDeletionRequestComponent', () => {
  let component: AnswerDeletionRequestComponent;
  let fixture: ComponentFixture<AnswerDeletionRequestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnswerDeletionRequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnswerDeletionRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
