import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatAvailableTermsComponent } from './boat-available-terms.component';

describe('BoatAvailableTermsComponent', () => {
  let component: BoatAvailableTermsComponent;
  let fixture: ComponentFixture<BoatAvailableTermsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatAvailableTermsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatAvailableTermsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
