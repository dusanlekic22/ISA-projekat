import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageQuickReservationsComponent } from './cottage-quick-reservations.component';

describe('CottageQuickReservationsComponent', () => {
  let component: CottageQuickReservationsComponent;
  let fixture: ComponentFixture<CottageQuickReservationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageQuickReservationsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageQuickReservationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
