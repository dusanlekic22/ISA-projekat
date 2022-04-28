import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwlCarouselBaseComponent } from './owl-carousel-base.component';

describe('OwlCarouselBaseComponent', () => {
  let component: OwlCarouselBaseComponent;
  let fixture: ComponentFixture<OwlCarouselBaseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OwlCarouselBaseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OwlCarouselBaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
