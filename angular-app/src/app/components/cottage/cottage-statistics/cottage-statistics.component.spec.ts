import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageStatisticsComponent } from './cottage-statistics.component';

describe('CottageStatisticsComponent', () => {
  let component: CottageStatisticsComponent;
  let fixture: ComponentFixture<CottageStatisticsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageStatisticsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
