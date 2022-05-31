import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerCottageProfileComponent } from './owner-cottage-profile.component';

describe('OwnerCottageProfileComponent', () => {
  let component: OwnerCottageProfileComponent;
  let fixture: ComponentFixture<OwnerCottageProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OwnerCottageProfileComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OwnerCottageProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
