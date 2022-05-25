import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseReservationComponent } from './base-reservation.component';

describe('BaseReservationComponent', () => {
  let component: BaseReservationComponent;
  let fixture: ComponentFixture<BaseReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BaseReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
