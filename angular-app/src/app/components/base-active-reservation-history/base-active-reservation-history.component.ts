import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import {
  ModalDismissReasons,
  NgbModal,
  NgbModalRef,
} from '@ng-bootstrap/ng-bootstrap';
import { ICottageReservation } from 'src/app/model/cottageReservation';
import { CottageReservationService } from 'src/app/pages/cottage-owner/services/cottage-reservation.service';

@Component({
  selector: 'app-base-active-reservation-history',
  templateUrl: './base-active-reservation-history.component.html',
  styleUrls: ['./base-active-reservation-history.component.css'],
})
export class BaseActiveReservationHistoryComponent implements OnInit {
  @Output() outputFromChild: EventEmitter<string> = new EventEmitter();
  @Input() id!: string;
  @Input() cottageReservation!: ICottageReservation;
  @Input() openForReservation!: string;
  @Input() type!: number;
  modalReference!: NgbModalRef;
  today: Date = new Date();
  reservationThreeDays: Date = new Date();
  constructor(
    public router: Router,
    private modalService: NgbModal,
    private reservationService: CottageReservationService
  ) {}

  closeResult = '';

  open(content: any) {
    this.modalReference = this.modalService.open(content, {
      ariaLabelledBy: 'modal-basic-title',
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
  cancel() {
    this.reservationService
      .cancelCottageReservation(this.cottageReservation)
      .subscribe(() => {
        this.outputFromChild.emit();
        this.modalReference.close();
      });
  }

  ngOnInit(): void {
    let x = new Date(this.cottageReservation.duration.startDate);
    x.setDate((x.getDate() + 4) % 31);
    this.reservationThreeDays = x;
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
