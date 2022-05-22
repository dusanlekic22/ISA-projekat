import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageOwnerProfileComponent } from './cottage-owner-profile.component';

describe('UserProfileComponent', () => {
  let component: CottageOwnerProfileComponent;
  let fixture: ComponentFixture<CottageOwnerProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CottageOwnerProfileComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageOwnerProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
