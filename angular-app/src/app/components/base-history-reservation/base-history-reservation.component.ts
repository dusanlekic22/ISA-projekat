import { CottageReservationService } from 'src/app/pages/cottage-owner/services/cottage-reservation.service';
import { ICottageReservation } from './../../model/cottageReservation';
import { ICottage } from './../../model/cottage';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IAddress } from 'src/app/model/address';
import {
  ModalDismissReasons,
  NgbModal,
  NgbModalRef,
} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-base-history-reservation',
  templateUrl: './base-history-reservation.component.html',
  styleUrls: ['./base-history-reservation.component.css'],
})
export class BaseHistoryReservationComponent implements OnInit {
  @Input() id!: string;
  @Input() cottageReservation!: ICottageReservation;
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
    private reservationService: CottageReservationService
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
