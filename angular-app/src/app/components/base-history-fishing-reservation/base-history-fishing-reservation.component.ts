import { IFishingReservation } from 'src/app/model/fishingReservation';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  ModalDismissReasons,
  NgbModal,
  NgbModalRef,
} from '@ng-bootstrap/ng-bootstrap';
import { FishingReservationService } from 'src/app/service/fishingReservation.service';
import { emptyReview, IReview } from 'src/app/model/review';
import { emptyComplaint, IComplaint } from 'src/app/model/complaint';
import { ComplaintService } from 'src/app/service/complaint.service';

@Component({
  selector: 'app-base-history-fishing-reservation',
  templateUrl: './base-history-fishing-reservation.component.html',
  styleUrls: ['./base-history-fishing-reservation.component.css'],
})
export class BaseHistoryFishingReservationComponent implements OnInit {
  @Input() id!: string;
  @Input() fishingReservation!: IFishingReservation;
  @Input() openForReservation!: string;
  @Input() type!: number;
  modalReference!: NgbModalRef;
  modalReferenceComplaint!: NgbModalRef;
  complaint!: string;
  review!: string;
  gradeOwner!: number;
  gradeEntity!: number;
  reviewFishing: IReview = emptyReview;
  complaintFishing: IComplaint = emptyComplaint;

  constructor(
    public router: Router,
    private modalService: NgbModal,
    private reservationService: FishingReservationService,
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
  ngOnInit(): void {}

  sendReview() {
    // this.reservationService
    //   .sendCottageReview(this.boatReservation)
    //   .subscribe(() => {
    //   });
    this.modalReference.close();
  }

  sendComplaint() {
    this.complaintFishing.fishingReservation = this.fishingReservation;
    this.complaintFishing.text = this.complaint;
    this.complaintService.sendComplaint(this.complaintFishing).subscribe(() => {
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
