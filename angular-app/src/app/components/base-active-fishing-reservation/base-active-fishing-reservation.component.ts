import { ReservationService } from './../../service/reservation.service';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import {
  ModalDismissReasons,
  NgbModal,
  NgbModalRef,
} from '@ng-bootstrap/ng-bootstrap';
import { IFishingReservation } from 'src/app/model/fishingReservation';
import { FishingReservationService } from 'src/app/service/fishingReservation.service';

@Component({
  selector: 'app-base-active-fishing-reservation',
  templateUrl: './base-active-fishing-reservation.component.html',
  styleUrls: ['./base-active-fishing-reservation.component.css'],
})
export class BaseActiveFishingReservationComponent implements OnInit {
  @Output() outputFromChild: EventEmitter<string> = new EventEmitter();
  @Input() id!: string;
  @Input() fishingReservation!: IFishingReservation;
  @Input() openForReservation!: string;
  @Input() type!: number;
  modalReference!: NgbModalRef;
  today: Date = new Date();
  reservationThreeDays!: Date;
  constructor(
    public router: Router,
    private modalService: NgbModal,
    private reservationService: FishingReservationService
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
  ngOnInit(): void {
    let x = new Date(this.fishingReservation.duration.startDate);
    x.setDate((x.getDate() + 4) % 31);
    this.reservationThreeDays = x;
  }

  cancel() {
    this.reservationService
      .cancelFishingReservation(this.fishingReservation)
      .subscribe(() => {
        this.outputFromChild.emit();
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
