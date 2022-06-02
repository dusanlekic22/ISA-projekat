import { TestBed } from '@angular/core/testing';
import { ReservationReportService } from './reservationReport.service';

describe('ReportReservationService', () => {
  let service: ReservationReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReservationReportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
