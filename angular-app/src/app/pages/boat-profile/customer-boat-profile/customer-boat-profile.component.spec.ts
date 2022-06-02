import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerBoatProfileComponent } from './customer-boat-profile.component';

describe('CustomerBoatProfileComponent', () => {
  let component: CustomerBoatProfileComponent;
  let fixture: ComponentFixture<CustomerBoatProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomerBoatProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerBoatProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
