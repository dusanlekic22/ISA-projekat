import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseHistoryReservationComponent } from './base-history-reservation.component';

describe('BaseHistoryReservationComponent', () => {
  let component: BaseHistoryReservationComponent;
  let fixture: ComponentFixture<BaseHistoryReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BaseHistoryReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseHistoryReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
