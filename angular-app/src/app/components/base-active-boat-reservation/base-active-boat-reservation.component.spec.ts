import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseActiveBoatReservationComponent } from './base-active-boat-reservation.component';

describe('BaseActiveBoatReservationComponent', () => {
  let component: BaseActiveBoatReservationComponent;
  let fixture: ComponentFixture<BaseActiveBoatReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BaseActiveBoatReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseActiveBoatReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
