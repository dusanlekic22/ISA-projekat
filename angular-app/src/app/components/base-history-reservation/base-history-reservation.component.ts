import { IReview, emptyReview } from './../../model/review';
import { CottageReservationService } from 'src/app/pages/cottage-owner/services/cottage-reservation.service';
import { ICottageReservation } from './../../model/cottageReservation';
import { ICottage } from './../../model/cottage';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IAddress } from 'src/app/model/address';
import {
  ModalDismissReasons,
  NgbModal,
  NgbModalRef,
} from '@ng-bootstrap/ng-bootstrap';
import { emptyComplaint, IComplaint } from 'src/app/model/complaint';
import { ComplaintService } from 'src/app/service/complaint.service';
import { emptyGrade, IGrade } from 'src/app/model/grade';

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
  reviewCottage: IGrade = emptyGrade;
  complaintCottage: IComplaint = emptyComplaint;
  customerId!: number;

  constructor(
    public router: Router,
    private _route: ActivatedRoute,
    private modalService: NgbModal,
    private reservationService: CottageReservationService,
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
    this.reviewCottage.review = this.review;
    this.reviewCottage.cottage = this.cottageReservation.cottage;
    this.reviewCottage.value = this.gradeEntity;
    this.reviewCottage.user.id = this.customerId;
    this.reservationService
      .sendCottageReview(this.reviewCottage)
      .subscribe(() => {});
    this.reviewCottage.value = this.gradeOwner;
    this.reservationService
      .sendCottageOwnerReview(this.reviewCottage)
      .subscribe(() => {});
    this.modalReference.close();
  }

  sendComplaint() {
    this.complaintCottage.cottageReservation = this.cottageReservation;
    this.complaintCottage.text = this.complaint;
    this.complaintService.sendComplaint(this.complaintCottage).subscribe(() => {
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
