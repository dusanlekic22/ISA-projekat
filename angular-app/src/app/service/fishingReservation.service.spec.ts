/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { FishingReservationService } from './fishingReservation.service';

describe('Service: FishingReservation', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FishingReservationService]
    });
  });

  it('should ...', inject([FishingReservationService], (service: FishingReservationService) => {
    expect(service).toBeTruthy();
  }));
});
