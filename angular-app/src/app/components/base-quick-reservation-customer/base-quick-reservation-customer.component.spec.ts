import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseQuickReservationCustomerComponent } from './base-quick-reservation-customer.component';

describe('BaseQuickReservationCustomerComponent', () => {
  let component: BaseQuickReservationCustomerComponent;
  let fixture: ComponentFixture<BaseQuickReservationCustomerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BaseQuickReservationCustomerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseQuickReservationCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
