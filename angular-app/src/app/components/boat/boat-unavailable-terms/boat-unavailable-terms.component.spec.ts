import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatUnavailableTermsComponent } from './boat-unavailable-terms.component';

describe('BoatUnavailableTermsComponent', () => {
  let component: BoatUnavailableTermsComponent;
  let fixture: ComponentFixture<BoatUnavailableTermsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatUnavailableTermsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatUnavailableTermsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
