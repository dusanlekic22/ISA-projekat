import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GradeStarComponent } from './grade-star.component';

describe('GradeStarComponent', () => {
  let component: GradeStarComponent;
  let fixture: ComponentFixture<GradeStarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GradeStarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GradeStarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
