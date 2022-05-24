import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageEditComponent } from './cottage-edit.component';

describe('CottageEditComponent', () => {
  let component: CottageEditComponent;
  let fixture: ComponentFixture<CottageEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageEditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
