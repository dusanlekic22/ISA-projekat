import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseBoatReservationComponent } from './base-boat-reservation.component';

describe('BaseBoatReservationComponent', () => {
  let component: BaseBoatReservationComponent;
  let fixture: ComponentFixture<BaseBoatReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BaseBoatReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseBoatReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
