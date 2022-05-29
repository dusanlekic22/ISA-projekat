import { TestBed } from '@angular/core/testing';

import { BoatAdditionalServicesService } from './boat-additional-services.service';

describe('BoatAdditionalServicesService', () => {
  let service: BoatAdditionalServicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BoatAdditionalServicesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
