import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatOwnerCalendarComponent } from './boat-owner-calendar.component';

describe('BoatOwnerCalendarComponent', () => {
  let component: BoatOwnerCalendarComponent;
  let fixture: ComponentFixture<BoatOwnerCalendarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatOwnerCalendarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatOwnerCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
