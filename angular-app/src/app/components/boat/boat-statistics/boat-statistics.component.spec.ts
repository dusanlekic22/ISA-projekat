import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatStatisticsComponent } from './boat-statistics.component';

describe('BoatStatisticsComponent', () => {
  let component: BoatStatisticsComponent;
  let fixture: ComponentFixture<BoatStatisticsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatStatisticsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
