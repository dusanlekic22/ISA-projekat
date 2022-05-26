import { TestBed } from '@angular/core/testing';

import { BoatReservationService } from './boat-reservation.service';

describe('BoatReservationService', () => {
  let service: BoatReservationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BoatReservationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
