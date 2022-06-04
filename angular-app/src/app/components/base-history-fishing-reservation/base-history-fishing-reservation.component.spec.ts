import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseHistoryFishingReservationComponent } from './base-history-fishing-reservation.component';

describe('BaseHistoryFishingReservationComponent', () => {
  let component: BaseHistoryFishingReservationComponent;
  let fixture: ComponentFixture<BaseHistoryFishingReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BaseHistoryFishingReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseHistoryFishingReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
