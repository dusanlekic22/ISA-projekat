import { TestBed } from '@angular/core/testing';

import { BoatQuickReservationService } from './boat-quick-reservation.service';

describe('BoatQuickReservationService', () => {
  let service: BoatQuickReservationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BoatQuickReservationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
