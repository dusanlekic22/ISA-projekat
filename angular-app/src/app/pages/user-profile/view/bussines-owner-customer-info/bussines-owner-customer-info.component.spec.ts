import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BussinesOwnerCustomerInfoComponent } from './bussines-owner-customer-info.component';

describe('BussinesOwnerCustomerInfoComponent', () => {
  let component: BussinesOwnerCustomerInfoComponent;
  let fixture: ComponentFixture<BussinesOwnerCustomerInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BussinesOwnerCustomerInfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BussinesOwnerCustomerInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
