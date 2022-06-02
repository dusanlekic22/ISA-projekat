import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { BoatQuickReservationService } from 'src/app/pages/boat-owner/services/boat-quick-reservation.service';
import { ToastrService } from 'ngx-toastr';
import { BoatService } from 'src/app/pages/boat-owner/services/boat.service';
import { IBoat } from 'src/app/model/boat/boat';
import { ActivatedRoute, Router } from '@angular/router';
import { IAdditionalService } from 'src/app/model/additionalService';
import { BoatAdditionalServicesService } from 'src/app/pages/boat-owner/services/boat-additional-services.service';

@Component({
  selector: 'app-boat-quick-reservations',
  templateUrl: './boat-quick-reservations.component.html',
  styleUrls: ['./boat-quick-reservations.component.css'],
})
export class BoatQuickReservationsComponent implements OnInit {
  @Input() boat!: IBoat;
  @Input() imageObject!: Array<object>;
  services!: IAdditionalService[];

  constructor(
    private _route: ActivatedRoute,
    private _additionalBoatService: BoatAdditionalServicesService,
    private _boatQuickReservationService: BoatQuickReservationService,
    private _toastr: ToastrService,
    private _boatService: BoatService
  ) {}

  ngOnInit(): void {
    let boatId = this._route.snapshot.paramMap.get('boatId')!;
    this._additionalBoatService
      .getAdditionalServicesByBoatQuickReservationId(parseInt(boatId))
      .subscribe((services) => {
        this.services = services;
      });
  }

  deleteQuickReservation(id: number): void {
    this._boatQuickReservationService.deleteBoatQuickReservations(id).subscribe(
      (quickReservation) => {
        this._toastr.success('Reservation was successfully removed.');
        this.getBoat();
        this._boatQuickReservationService
          .getBoatQuickReservations()
          .subscribe((boatQuickReservation) => {
            this.boat.boatQuickReservation = boatQuickReservation;
          });
      },
      (err) => {
        this._toastr.error('Reservation removal failed');
      }
    );
    this._boatService.getBoatsByBoatOwnerId;
  }

  private getBoat() {
    this._boatService.getBoatById(this.boat.id).subscribe((boat) => {
      this.boat = boat;
      this.boat.boatImage.forEach((element) => {
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
