import { CottageReservationService } from 'src/app/pages/cottage-owner/services/cottage-reservation.service';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ICustomer } from 'src/app/model/customer';
import { ActivatedRoute, Router } from '@angular/router';
import { ICottageReservation } from 'src/app/model/cottageReservation';
import { CottageAdditionalServicesService } from 'src/app/pages/cottage-owner/services/cottage-additional-services.service';
import { IAdditionalService } from 'src/app/model/additionalService';
import { ToastrService } from 'ngx-toastr';
import { CottageService } from 'src/app/pages/cottage-owner/services/cottage.service';

@Component({
  selector: 'app-cottage-reservations',
  templateUrl: './cottage-reservations.component.html',
  styleUrls: ['./cottage-reservations.component.css'],
})
export class CottageReservationsComponent implements OnInit {
  @Input() reservations!: ICottageReservation[];
  @Input() customers!: ICustomer[];
  @Output() customerEmit = new EventEmitter<number>();
  @Output() deleted = new EventEmitter<boolean>();
  services!: IAdditionalService[];

  constructor(
    private _cottageReservationService: CottageReservationService,
    private _route: ActivatedRoute,
    private _router: Router,
    private _toastr: ToastrService,
    private _additionalCottageService: CottageAdditionalServicesService,
    private _cottageService: CottageService,
  ) {}

  ngOnInit(): void {
    let cottageId = this._route.snapshot.paramMap.get('cottageId')!;
    this._additionalCottageService
      .getAdditionalServicesByCottageReservationId(parseInt(cottageId))
      .subscribe((services) => {
        this.services = services;
      });
  }

  newReservation(customer: ICustomer) {
    this.customerEmit.emit(customer.id);
  }

  deleteReservation(id: number) {
    this._cottageReservationService
      .deleteCottageReservation(id)
      .subscribe(
        (reservation) => {
          this._toastr.success('Reservation was successfully removed.');
          this.deleted.emit();
        },
        (err) => {
          this._toastr.error('Reservation removal failed');
        }
      );
  }

  customerInfo(customer: ICustomer) {
    this._router.navigateByUrl(`customerInfo/${customer.id}`);
  }

  isCustomerEligible(customer: ICustomer) {
    return this.customers.some((c) => c.id === customer.id);
  }

  openQuickReservationForm() {
    // this.addReservationFormOpened = true;
    // this.quickReservationFormElement.nativeElement.scrollIntoView();
  }
}
