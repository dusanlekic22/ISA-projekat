import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddBoatReservationComponent } from './add-boat-reservation.component';

describe('AddBoatReservationComponent', () => {
  let component: AddBoatReservationComponent;
  let fixture: ComponentFixture<AddBoatReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddBoatReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddBoatReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
