import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseHistoryBoatReservationComponent } from './base-history-boat-reservation.component';

describe('BaseHistoryBoatReservationComponent', () => {
  let component: BaseHistoryBoatReservationComponent;
  let fixture: ComponentFixture<BaseHistoryBoatReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BaseHistoryBoatReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseHistoryBoatReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
