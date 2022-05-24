import { ICottage } from './../../../../pages/cottage-owner/cottage-profile/cottage';
import { Component, Input, OnInit } from '@angular/core';
import { CottageQuickReservationService } from 'src/app/pages/cottage-owner/services/cottage-quick-reservation.service';
import { ToastrService } from 'ngx-toastr';
import { CottageService } from 'src/app/pages/cottage-owner/services/cottage.service';

@Component({
  selector: 'app-cottage-quick-reservations',
  templateUrl: './cottage-quick-reservations.component.html',
  styleUrls: ['../../../../pages/cottage-owner/cottage-style.css']
})
export class CottageQuickReservationsComponent implements OnInit {

  @Input() cottage! : ICottage ;
  @Input() imageObject!: Array<object>;

  constructor(
    private _cottageQuickReservationService: CottageQuickReservationService,
    private _toastr: ToastrService,
    private _cottageService: CottageService,) { }

  ngOnInit(): void {
  }

  deleteQuickReservation(id: number): void {
    this._cottageQuickReservationService
      .deleteCottageQuickReservations(id)
      .subscribe(
        (quickReservation) => {
          this._toastr.success('Reservation was successfully removed.');
          this.getCottage();
          this._cottageQuickReservationService
            .getCottageQuickReservations()
            .subscribe((cottageQuickReservation) => {
              this.cottage.cottageQuickReservation = cottageQuickReservation;
            });
        },
        (err) => {
          this._toastr.error('Reservation removal failed');
        }
      );
  }

  private getCottage() {
    this._cottageService.getCottageById(this.cottage.id).subscribe((cottage) => {
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
