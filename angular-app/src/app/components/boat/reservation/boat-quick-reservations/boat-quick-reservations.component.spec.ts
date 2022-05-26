import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatQuickReservationsComponent } from './boat-quick-reservations.component';

describe('BoatQuickReservationsComponent', () => {
  let component: BoatQuickReservationsComponent;
  let fixture: ComponentFixture<BoatQuickReservationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatQuickReservationsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatQuickReservationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
