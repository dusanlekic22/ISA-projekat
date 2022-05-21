import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeCustomerInfoComponent } from './change-customer-info.component';

describe('ChangeCustomerInfoComponent', () => {
  let component: ChangeCustomerInfoComponent;
  let fixture: ComponentFixture<ChangeCustomerInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangeCustomerInfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeCustomerInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
