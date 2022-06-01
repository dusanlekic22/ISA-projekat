import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportRequestsComponent } from './report-requests.component';

describe('ReportRequestsComponent', () => {
  let component: ReportRequestsComponent;
  let fixture: ComponentFixture<ReportRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReportRequestsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
