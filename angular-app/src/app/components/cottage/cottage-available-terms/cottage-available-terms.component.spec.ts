import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageAvailableTermsComponent } from './cottage-available-terms.component';

describe('CottageAvailableTermsComponent', () => {
  let component: CottageAvailableTermsComponent;
  let fixture: ComponentFixture<CottageAvailableTermsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageAvailableTermsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageAvailableTermsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
