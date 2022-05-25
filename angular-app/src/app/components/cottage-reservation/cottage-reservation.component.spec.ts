import { ComponentFixture, TestBed } from '@angular/core/testing';

<<<<<<< HEAD:angular-app/src/app/components/cottage/reservation/add-reservation/add-reservation.component.spec.ts
import { AddReservationComponent } from './add-reservation.component';

describe('AddReservationComponent', () => {
  let component: AddReservationComponent;
  let fixture: ComponentFixture<AddReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddReservationComponent ]
=======
import { CottageReservationComponent } from './cottage-reservation.component';

describe('CottageReservationComponent', () => {
  let component: CottageReservationComponent;
  let fixture: ComponentFixture<CottageReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageReservationComponent ]
>>>>>>> feature_customer_reservation_availabilty:angular-app/src/app/components/cottage-reservation/cottage-reservation.component.spec.ts
    })
    .compileComponents();
  });

  beforeEach(() => {
<<<<<<< HEAD:angular-app/src/app/components/cottage/reservation/add-reservation/add-reservation.component.spec.ts
    fixture = TestBed.createComponent(AddReservationComponent);
=======
    fixture = TestBed.createComponent(CottageReservationComponent);
>>>>>>> feature_customer_reservation_availabilty:angular-app/src/app/components/cottage-reservation/cottage-reservation.component.spec.ts
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
