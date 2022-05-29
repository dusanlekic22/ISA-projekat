import { TestBed } from '@angular/core/testing';

import { CottageAdditionalServicesService } from './cottage-additional-services.service';

describe('CottageAdditionalServicesService', () => {
  let service: CottageAdditionalServicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CottageAdditionalServicesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
