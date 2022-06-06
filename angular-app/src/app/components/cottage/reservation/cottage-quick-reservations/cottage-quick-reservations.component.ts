import { CottageAdditionalServicesService } from './../../../../pages/cottage-owner/services/cottage-additional-services.service';
import { IAdditionalService } from './../../../../model/additionalService';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CottageQuickReservationService } from 'src/app/pages/cottage-owner/services/cottage-quick-reservation.service';
import { ToastrService } from 'ngx-toastr';
import { CottageService } from 'src/app/pages/cottage-owner/services/cottage.service';
import { ICottage } from 'src/app/model/cottage';

@Component({
  selector: 'app-cottage-quick-reservations',
  templateUrl: './cottage-quick-reservations.component.html',
  styleUrls: ['./cottage-quick-reservations.component.css'],
})
export class CottageQuickReservationsComponent implements OnInit {
  @Input() cottage!: ICottage;
  @Input() imageObject!: Array<object>;
  services!: IAdditionalService[];

  constructor(
    private _cottageQuickReservationService: CottageQuickReservationService,
    private _additionalCottageService: CottageAdditionalServicesService,
    private _toastr: ToastrService,
    private _cottageService: CottageService
  ) {}

  ngOnInit(): void {
    this._additionalCottageService
      .getAdditionalServicesByCottageQuickReservationId(this.cottage.id)
      .subscribe((services) => {
        this.services = services;
      });
  }

  deleteQuickReservation(id: number): void {
    this._cottageQuickReservationService
      .deleteCottageQuickReservations(id)
      .subscribe(
        (quickReservation) => {
          this._toastr.success('Reservation was successfully removed.');
          this.getCottage();
          this._cottageQuickReservationService
            .getCottageQuickReservationsByCottageId(this.cottage.id)
            .subscribe((cottageQuickReservation) => {
              this.cottage.cottageQuickReservation = cottageQuickReservation;
            });
        },
        (err) => {
          this._toastr.error('Reservation removal failed');
        }
      );
    this._cottageService.getCottagesByCottageOwnerId;
  }

  private getCottage() {
    this._cottageService
      .getCottageById(this.cottage.id)
      .subscribe((cottage) => {
        this.cottage = cottage;
        this.cottage.cottageImage.forEach((element) => {
          this.imageObject.push({
            image: element.image,
            thumbImage: element.image,
            alt: 'alt of image',
          });
        });
      });
  }

  openQuickReservationForm() {
    // this.addReservationFormOpened = true;
    // this.quickReservationFormElement.nativeElement.scrollIntoView();
  }
}
