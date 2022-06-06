import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnswerComplaintComponent } from './answer-complaint.component';

describe('AnswerComplaintComponent', () => {
  let component: AnswerComplaintComponent;
  let fixture: ComponentFixture<AnswerComplaintComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnswerComplaintComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnswerComplaintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
