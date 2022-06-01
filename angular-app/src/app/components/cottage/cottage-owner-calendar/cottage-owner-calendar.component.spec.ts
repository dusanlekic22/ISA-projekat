import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageOwnerCalendarComponent } from './cottage-owner-calendar.component';

describe('CottageOwnerCalendarComponent', () => {
  let component: CottageOwnerCalendarComponent;
  let fixture: ComponentFixture<CottageOwnerCalendarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageOwnerCalendarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageOwnerCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
