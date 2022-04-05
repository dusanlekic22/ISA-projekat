import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseRegistrationComponent } from './choose-registration.component';

describe('ChooseRegistrationComponent', () => {
  let component: ChooseRegistrationComponent;
  let fixture: ComponentFixture<ChooseRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChooseRegistrationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
