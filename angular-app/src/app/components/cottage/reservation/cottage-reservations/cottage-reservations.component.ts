import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ICustomer } from 'src/app/model/customer';
import { ActivatedRoute, Router } from '@angular/router';
import { ICottageReservation } from 'src/app/model/cottageReservation';
import { CottageAdditionalServicesService } from 'src/app/pages/cottage-owner/services/cottage-additional-services.service';
import { IAdditionalService } from 'src/app/model/additionalService';

@Component({
  selector: 'app-cottage-reservations',
  templateUrl: './cottage-reservations.component.html',
  styleUrls: ['./cottage-reservations.component.css'],
})
export class CottageReservationsComponent implements OnInit {
  @Input() reservations!: ICottageReservation[];
  @Input() customers!: ICustomer[];
  @Output() customerEmit = new EventEmitter<number>();
  services!: IAdditionalService[];

  constructor(
    private _route: ActivatedRoute,
    private _router: Router,
    private _additionalCottageService: CottageAdditionalServicesService
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

  deleteReservation(id: number) {}

  customerInfo(customer: ICustomer) {
    this._router.navigateByUrl(`customer/${customer.id}`);
  }

  isCustomerEligible(customer: ICustomer) {
    return this.customers.some((c) => c.id === customer.id);
  }

  openQuickReservationForm() {
    // this.addReservationFormOpened = true;
    // this.quickReservationFormElement.nativeElement.scrollIntoView();
  }
}
