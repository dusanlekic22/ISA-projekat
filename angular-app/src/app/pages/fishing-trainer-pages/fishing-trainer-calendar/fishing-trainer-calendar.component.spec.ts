import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FishingTrainerCalendarComponent } from './fishing-trainer-calendar.component';

describe('FishingTrainerCalendarComponent', () => {
  let component: FishingTrainerCalendarComponent;
  let fixture: ComponentFixture<FishingTrainerCalendarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FishingTrainerCalendarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FishingTrainerCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
