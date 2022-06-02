import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerFishingTrainerProfileComponent } from './customer-fishing-trainer-profile.component';

describe('CustomerFishingTrainerProfileComponent', () => {
  let component: CustomerFishingTrainerProfileComponent;
  let fixture: ComponentFixture<CustomerFishingTrainerProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomerFishingTrainerProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerFishingTrainerProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
