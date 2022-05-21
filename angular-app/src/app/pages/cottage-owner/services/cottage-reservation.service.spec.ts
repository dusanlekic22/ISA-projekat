import { TestBed } from '@angular/core/testing';

import { CottageReservationService } from './cottage-reservation.service';

describe('CottageReservationService', () => {
  let service: CottageReservationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CottageReservationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
