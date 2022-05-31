import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FishingTrainerProfileComponent } from './fishing-trainer-profile.component';

describe('FishingTrainerProfileComponent', () => {
  let component: FishingTrainerProfileComponent;
  let fixture: ComponentFixture<FishingTrainerProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FishingTrainerProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FishingTrainerProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
