import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCottageQuickReservationComponent } from './add-cottage-quick-reservation.component';

describe('AddCottageQuickReservationComponent', () => {
  let component: AddCottageQuickReservationComponent;
  let fixture: ComponentFixture<AddCottageQuickReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddCottageQuickReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCottageQuickReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
