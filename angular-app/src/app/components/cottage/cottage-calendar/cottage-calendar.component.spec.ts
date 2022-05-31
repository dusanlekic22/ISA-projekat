import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageCalendarComponent } from './cottage-calendar.component';

describe('CottageCalendarComponent', () => {
  let component: CottageCalendarComponent;
  let fixture: ComponentFixture<CottageCalendarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageCalendarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
