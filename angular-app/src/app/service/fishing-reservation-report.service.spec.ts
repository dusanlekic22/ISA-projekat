import { TestBed } from '@angular/core/testing';
import { FishingReservationReportService } from './fishing-reservation-report.service';

describe('ReportReservationService', () => {
  let service: FishingReservationReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FishingReservationReportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
