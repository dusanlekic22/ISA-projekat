import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  ModalDismissReasons,
  NgbModal,
  NgbModalRef,
} from '@ng-bootstrap/ng-bootstrap';
import { IBoatReservation } from 'src/app/model/boat/boatReservation';
import { emptyComplaint, IComplaint } from 'src/app/model/complaint';
import { emptyReview, IReview } from 'src/app/model/review';
import { BoatReservationService } from 'src/app/pages/boat-owner/services/boat-reservation.service';
import { ComplaintService } from 'src/app/service/complaint.service';

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
  reviewBoat: IReview = emptyReview;
  complaintBoat: IComplaint = emptyComplaint;
  customerId!: number;

  constructor(
    public router: Router,
    private modalService: NgbModal,
    private _route: ActivatedRoute,
    private reservationService: BoatReservationService,
    private complaintService: ComplaintService
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
  ngOnInit(): void {
    this._route.params.subscribe((data) => {
      this.customerId = data.id;
    });
  }

  sendReview() {
    if (this.gradeEntity !== undefined && this.gradeOwner !== undefined) {
      this.reviewBoat.boatReservation = this.boatReservation;
      this.reviewBoat.customerId = this.customerId;
      this.reviewBoat.entityGrade = this.gradeEntity;
      this.reviewBoat.ownerGrade = this.gradeOwner;
      this.reviewBoat.text = this.review;
      this.reservationService.sendBoatReview(this.reviewBoat).subscribe(() => {
        this.modalReference.close();
      });
    }
  }

  sendComplaint() {
    this.complaintBoat.boatReservation = this.boatReservation;
    this.complaintBoat.text = this.complaint;
    this.complaintService.sendComplaint(this.complaintBoat).subscribe(() => {
      this.modalReferenceComplaint.close();
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
