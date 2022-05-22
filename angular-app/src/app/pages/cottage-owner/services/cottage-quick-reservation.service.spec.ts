import { TestBed } from '@angular/core/testing';

import { CottageQuickReservationService } from './cottage-quick-reservation.service';

describe('CottageQuickReservationService', () => {
  let service: CottageQuickReservationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CottageQuickReservationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
