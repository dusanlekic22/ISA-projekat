import { TestBed } from '@angular/core/testing';

import { LoyaltySettingsService } from './loyalty-settings.service';

describe('LoyaltySettingsService', () => {
  let service: LoyaltySettingsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoyaltySettingsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
