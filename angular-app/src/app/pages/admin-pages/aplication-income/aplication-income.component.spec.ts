import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AplicationIncomeComponent } from './aplication-income.component';

describe('AplicationIncomeComponent', () => {
  let component: AplicationIncomeComponent;
  let fixture: ComponentFixture<AplicationIncomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AplicationIncomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AplicationIncomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
