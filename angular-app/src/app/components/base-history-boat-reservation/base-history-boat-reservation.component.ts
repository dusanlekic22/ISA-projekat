import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  ModalDismissReasons,
  NgbModal,
  NgbModalRef,
} from '@ng-bootstrap/ng-bootstrap';
import { IBoatReservation } from 'src/app/model/boat/boatReservation';
import { BoatReservationService } from 'src/app/pages/boat-owner/services/boat-reservation.service';

@Component({
  selector: 'app-base-history-boat-reservation',
  templateUrl: './base-history-boat-reservation.component.html',
  styleUrls: ['./base-history-boat-reservation.component.css'],
})
export class BaseHistoryBoatReservationComponent implements OnInit {
  @Input() id!: string;
  @Input() boatReservation!: IBoatReservation;
  @Input() openForReservation!: string;
  @Input() type!: number;
  modalReference!: NgbModalRef;
  modalReferenceComplaint!: NgbModalRef;
  complaint!: string;
  review!: string;
  gradeOwner!: number;
  gradeEntity!: number;

  constructor(
    public router: Router,
    private modalService: NgbModal,
    private reservationService: BoatReservationService
  ) {}

  closeResult = '';

  open(content: any) {
    this.modalReference = this.modalService.open(content, {
      ariaLabelledBy: 'modal-basic-title review',
    });
    this.modalReference.result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      }
    );
  }

  openComplaint(content: any) {
    this.modalReferenceComplaint = this.modalService.open(content, {
      ariaLabelledBy: 'modal-basic-title complaint',
    });
    this.modalReferenceComplaint.result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      }
    );
  }
  ngOnInit(): void {}

  sendReview() {
    // this.reservationService
    //   .sendCottageReview(this.boatReservation)
    //   .subscribe(() => {
    //   });
    this.modalReference.close();
  }

  sendComplaint() {
    // this.reservationService
    //   .sendCottageReview(this.boatReservation)
    //   .subscribe(() => {
    //   });
    this.modalReferenceComplaint.close();
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
