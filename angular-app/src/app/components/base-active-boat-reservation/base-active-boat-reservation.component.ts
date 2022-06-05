import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  ModalDismissReasons,
  NgbModal,
  NgbModalRef,
} from '@ng-bootstrap/ng-bootstrap';
import { IBoatReservation } from 'src/app/model/boat/boatReservation';
import { BoatReservationService } from 'src/app/pages/boat-owner/services/boat-reservation.service';
import { ReservationService } from 'src/app/service/reservation.service';

@Component({
  selector: 'app-base-active-boat-reservation',
  templateUrl: './base-active-boat-reservation.component.html',
  styleUrls: ['./base-active-boat-reservation.component.css'],
})
export class BaseActiveBoatReservationComponent implements OnInit {
  @Input() id!: string;
  @Input() boatReservation!: IBoatReservation;
  @Input() openForReservation!: string;
  @Input() type!: number;
  modalReference!: NgbModalRef;
  today: Date = new Date();
  reservationThreeDays: Date = new Date();

  constructor(
    public router: Router,
    private modalService: NgbModal,
    private reservationService: BoatReservationService
  ) {}

  closeResult = '';

  open(content: any) {
    this.modalService
      .open(content, { ariaLabelledBy: 'modal-basic-title' })
      .result.then(
        (result) => {
          this.closeResult = `Closed with: ${result}`;
        },
        (reason) => {
          this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        }
      );
  }
  ngOnInit(): void {
    let x = new Date(this.boatReservation.duration.startDate);
    x.setDate((x.getDate() + 4) % 31);
    this.reservationThreeDays = x;
  }

  cancel() {
    this.reservationService
      .cancelBoatReservation(this.boatReservation)
      .subscribe(() => {
        this.modalReference.close();
      });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }
}
