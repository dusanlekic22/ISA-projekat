import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatOwnerHomeComponent } from './boat-owner-home.component';

describe('BoatOwnerHomeComponent', () => {
  let component: BoatOwnerHomeComponent;
  let fixture: ComponentFixture<BoatOwnerHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatOwnerHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatOwnerHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
