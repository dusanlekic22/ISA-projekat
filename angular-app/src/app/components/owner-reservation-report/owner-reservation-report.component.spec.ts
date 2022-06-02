import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerReservationReportComponent } from './owner-reservation-report.component';

describe('OwnerReservationReportComponent', () => {
  let component: OwnerReservationReportComponent;
  let fixture: ComponentFixture<OwnerReservationReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OwnerReservationReportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OwnerReservationReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
