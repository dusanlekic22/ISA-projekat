import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerBoatProfileComponent } from './owner-boat-profile.component';

describe('OwnerBoatProfileComponent', () => {
  let component: OwnerBoatProfileComponent;
  let fixture: ComponentFixture<OwnerBoatProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OwnerBoatProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OwnerBoatProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
