import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerGradeComponent } from './answer-grade.component';

describe('AnswerGradeComponent', () => {
  let component: AnswerGradeComponent;
  let fixture: ComponentFixture<AnswerGradeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnswerGradeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnswerGradeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
