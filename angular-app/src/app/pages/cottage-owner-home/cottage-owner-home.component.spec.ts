import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageOwnerHomeComponent } from './cottage-owner-home.component';

describe('CottageOwnerHomeComponent', () => {
  let component: CottageOwnerHomeComponent;
  let fixture: ComponentFixture<CottageOwnerHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageOwnerHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageOwnerHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
