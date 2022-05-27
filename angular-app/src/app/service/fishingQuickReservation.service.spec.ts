/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { FishingQuickReservationService } from './fishingQuickReservation.service';

describe('Service: FishingQuickReservation', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FishingQuickReservationService]
    });
  });

  it('should ...', inject([FishingQuickReservationService], (service: FishingQuickReservationService) => {
    expect(service).toBeTruthy();
  }));
});
