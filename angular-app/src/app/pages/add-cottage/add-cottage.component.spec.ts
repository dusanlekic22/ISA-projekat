import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCottageComponent } from './add-cottage.component';

describe('AddCottageComponent', () => {
  let component: AddCottageComponent;
  let fixture: ComponentFixture<AddCottageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddCottageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCottageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
