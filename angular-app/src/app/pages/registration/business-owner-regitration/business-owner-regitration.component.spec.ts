import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BusinessOwnerRegitrationComponent } from './business-owner-regitration.component';

describe('BusinessOwnerRegitrationComponent', () => {
  let component: BusinessOwnerRegitrationComponent;
  let fixture: ComponentFixture<BusinessOwnerRegitrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BusinessOwnerRegitrationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BusinessOwnerRegitrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
