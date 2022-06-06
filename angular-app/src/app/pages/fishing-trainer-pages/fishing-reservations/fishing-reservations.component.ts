import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IAdditionalService } from 'src/app/model/additionalService';
import { ICustomer } from 'src/app/model/customer';
import { IFishingReservation } from 'src/app/model/fishingReservation';
import { AdditionalServiceService } from '../../cottage-owner/services/additional-service.service';

@Component({
  selector: 'app-fishing-reservations',
  templateUrl: './fishing-reservations.component.html',
  styleUrls: ['./fishing-reservations.component.css']
})
export class FishingReservationsComponent implements OnInit {
  @Input() reservations!: IFishingReservation[];
  @Input() customers! : ICustomer[];
  @Output() customerEmit = new EventEmitter<number>();
  services!: IAdditionalService[];

  constructor(private router: Router,
    private additionalService: AdditionalServiceService,
    private route: ActivatedRoute,) {}

  ngOnInit(): void {
    let fishingId = this.route.snapshot.paramMap.get('id')!;
    this.additionalService
      .getAdditionalServicesByFishingReservationId(parseInt(fishingId))
      .subscribe((services) => {
        this.services = services;
      });
  }

  newReservation(customer: ICustomer) {
    this.customerEmit.emit(customer.id);
  }

  deleteReservation(id: number) {}

  customerInfo(customer: ICustomer) {
    this.router.navigateByUrl(`customerInfo/${customer.id}`);
  }

  isCustomerEligible(customer: ICustomer) {
    return this.customers.some(c => c.id === customer.id);
  }

  openQuickReservationForm() {
    // this.addReservationFormOpened = true;
    // this.quickReservationFormElement.nativeElement.scrollIntoView();
  }
}
