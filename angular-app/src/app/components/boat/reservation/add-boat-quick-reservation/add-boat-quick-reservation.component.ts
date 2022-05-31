import { UserService } from './../../../../service/user.service';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { BoatQuickReservationService } from 'src/app/pages/boat-owner/services/boat-quick-reservation.service';
import { ToastrService } from 'ngx-toastr';
import { BoatService } from 'src/app/pages/boat-owner/services/boat.service';
import { ActivatedRoute, Route } from '@angular/router';
import { IBoat, initBoat } from 'src/app/model/boat/boat';
import { IBoatQuickReservation } from 'src/app/model/boat/boatQuickReservation';
import { MatChip } from '@angular/material/chips';
import { IAdditionalService } from 'src/app/model/additionalService';
import { BoatAdditionalServicesService } from 'src/app/pages/boat-owner/services/boat-additional-services.service';

@Component({
  selector: 'app-add-boat-quick-reservation',
  templateUrl: './add-boat-quick-reservation.component.html',
  styleUrls: ['./add-boat-quick-reservation.component.css'],
})
export class AddBoatQuickReservationComponent implements OnInit {
  boat!: IBoat;
  @Input() boatQuickReservation: IBoatQuickReservation = {
    id: 0,
    duration: {
      startDate: new Date(),
      endDate: new Date(),
    },
    guestCapacity: 0,
    price: 0,
    boat: initBoat
  };
  minDate!: string;
  @Output() submitted = new EventEmitter<boolean>();
  boats!: IBoat[];
  chips!: MatChip;
  boatChips: string[] = [];
  boatServices: IAdditionalService[] = [];
  reservationServices: IAdditionalService[] = [];

  constructor(
    private _boatQuickReservationService: BoatQuickReservationService,
    private _toastr: ToastrService,
    private _boatService: BoatService,
    private _userService: UserService,
    private _route: ActivatedRoute,
    private _boatAdditionalService: BoatAdditionalServicesService
  ) {}

  ngOnInit(): void {
    this.minDate = this.date();
    let boatId = this._route.snapshot.paramMap.get('boatId');
    this._userService.currentUser.subscribe((user) => {
      this._boatService
        .getBoatsByBoatOwnerId(user.id)
        .subscribe((boats) => {
          this.boats = boats;
          this.boat= this.boats.filter(c=>c.id==parseInt(boatId!))[0];
        });
    });
    if(boatId!=undefined){
      this._boatAdditionalService
      .getAdditionalServicesByBoatId(parseInt(boatId))
      .subscribe((tags) => {
        tags.forEach((t) => {
          this.boatServices.push(t);
        });
      });
    }
  }

  getChips() {
    this._boatAdditionalService
      .getAdditionalServicesByBoatId(this.boat.id)
      .subscribe((tags) => {
        tags.forEach((t) => {
          this.boatServices.push(t);
        });
      });
  }

  toggleSelectionBoat(chip: MatChip, option: IAdditionalService) {
    if (chip.toggleSelected()) {
      this.reservationServices.push({id:0,name:option.name,price:option.price});
    } else {
      this.reservationServices = this.reservationServices.filter(
        (e) => e !== option
      );
    }
  }

  addQuickReservation(): void {
    this._boatQuickReservationService
      .addBoatQuickReservation(this.boatQuickReservation, this.boat)
      .subscribe(
        (quickReservation) => {
          //this.addReservationFormOpened = false;
          this._toastr.success('Reservation was successfully added.');
          this.reservationServices.forEach((tag) => {
            this._boatAdditionalService
              .addAdditionalServiceForBoatQuickReservation(tag, quickReservation)
              .subscribe((service) => {});
          });
          this.submitted.emit();
        },
        (err) => {
          this._toastr.error(
            'Reservation term overlaps with another.',
            'Try a different date!'
          );
        }
      );
  }

  date() {
    let min = new Date();
    let month = '';
    let day = '';
    if (min.getMonth() < 10) {
      month = '0' + (min.getMonth() + 1).toString();
    } else {
      month = (min.getMonth() + 1).toString();
    }
    if (min.getDate() < 10) {
      day = '0' + min.getDate().toString();
    } else {
      day = min.getDate().toString();
    }
    let x = min.getFullYear().toString() + '-' + month + '-' + day + 'T00:00';
    console.log(x);
    return x;
  }
}
