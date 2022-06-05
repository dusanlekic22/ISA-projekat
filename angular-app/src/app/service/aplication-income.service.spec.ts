import { TestBed } from '@angular/core/testing';

import { AplicationIncomeService } from './aplication-income.service';

describe('AplicationIncomeService', () => {
  let service: AplicationIncomeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AplicationIncomeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
