import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseActiveFishingReservationComponent } from './base-active-fishing-reservation.component';

describe('BaseActiveFishingReservationComponent', () => {
  let component: BaseActiveFishingReservationComponent;
  let fixture: ComponentFixture<BaseActiveFishingReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BaseActiveFishingReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseActiveFishingReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
