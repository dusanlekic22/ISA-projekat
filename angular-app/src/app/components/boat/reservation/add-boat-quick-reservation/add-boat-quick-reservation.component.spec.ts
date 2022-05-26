import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddBoatQuickReservationComponent } from './add-boat-quick-reservation.component';

describe('AddBoatQuickReservationComponent', () => {
  let component: AddBoatQuickReservationComponent;
  let fixture: ComponentFixture<AddBoatQuickReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddBoatQuickReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddBoatQuickReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
