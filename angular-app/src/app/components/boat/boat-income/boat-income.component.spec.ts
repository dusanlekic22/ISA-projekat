import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatIncomeComponent } from './boat-income.component';

describe('BoatIncomeComponent', () => {
  let component: BoatIncomeComponent;
  let fixture: ComponentFixture<BoatIncomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatIncomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatIncomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
