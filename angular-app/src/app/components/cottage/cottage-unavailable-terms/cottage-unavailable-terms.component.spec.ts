import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageUnavailableTermsComponent } from './cottage-unavailable-terms.component';

describe('CottageUnavailableTermsComponent', () => {
  let component: CottageUnavailableTermsComponent;
  let fixture: ComponentFixture<CottageUnavailableTermsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageUnavailableTermsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageUnavailableTermsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
