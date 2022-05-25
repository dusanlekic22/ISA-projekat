/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { FishingTrainerService } from './fishingTrainer.service';

describe('Service: FishingTrainer', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FishingTrainerService]
    });
  });

  it('should ...', inject([FishingTrainerService], (service: FishingTrainerService) => {
    expect(service).toBeTruthy();
  }));
});
