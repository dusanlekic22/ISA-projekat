import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageIncomeComponent } from './cottage-income.component';

describe('CottageIncomeComponent', () => {
  let component: CottageIncomeComponent;
  let fixture: ComponentFixture<CottageIncomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageIncomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageIncomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
