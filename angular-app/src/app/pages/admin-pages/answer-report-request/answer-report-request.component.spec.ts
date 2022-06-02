import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerReportRequestComponent } from './answer-report-request.component';

describe('AnswerReportRequestComponent', () => {
  let component: AnswerReportRequestComponent;
  let fixture: ComponentFixture<AnswerReportRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnswerReportRequestComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnswerReportRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
