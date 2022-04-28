import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerCottageProfileComponent } from './customer-cottage-profile.component';

describe('CustomerCottageProfileComponent', () => {
  let component: CustomerCottageProfileComponent;
  let fixture: ComponentFixture<CustomerCottageProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomerCottageProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerCottageProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
