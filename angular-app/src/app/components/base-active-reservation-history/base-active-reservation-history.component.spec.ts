import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseActiveReservationHistoryComponent } from './base-active-reservation-history.component';

describe('BaseActiveReservationHistoryComponent', () => {
  let component: BaseActiveReservationHistoryComponent;
  let fixture: ComponentFixture<BaseActiveReservationHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BaseActiveReservationHistoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseActiveReservationHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
