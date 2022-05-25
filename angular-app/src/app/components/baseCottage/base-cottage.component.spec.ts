import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseCottageComponent } from './base-cottage.component';

describe('BaseCottageComponent', () => {
  let component: BaseCottageComponent;
  let fixture: ComponentFixture<BaseCottageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BaseCottageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseCottageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
