import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatOwnerProfileComponent } from './boat-owner-profile.component';

describe('BoatOwnerProfileComponent', () => {
  let component: BoatOwnerProfileComponent;
  let fixture: ComponentFixture<BoatOwnerProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatOwnerProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatOwnerProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
