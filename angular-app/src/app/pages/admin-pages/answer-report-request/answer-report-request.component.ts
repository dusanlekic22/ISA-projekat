import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'angular-bootstrap-md';
import { emptyBoatReservation } from 'src/app/model/boat/boatReservation';
import {
  IReservationReport,
  ReservationReportStatus,
} from 'src/app/model/reservationReport';
import { RequestsService } from 'src/app/service/requests.service';
import { ReservationReportService } from 'src/app/service/reservationReport.service';
import { emptyCottageReservation } from './../../../model/cottageReservation';
import { emptyFishingReservation } from './../../../model/fishingReservation';

@Component({
  selector: 'app-answer-report-request',
  templateUrl: './answer-report-request.component.html',
  styleUrls: ['./answer-report-request.component.css'],
})
export class AnswerReportRequestComponent implements OnInit {
  @ViewChild('report') reportModal!: ModalDirective;
  request: IReservationReport = {
    id: 0,
    userPenalized: false,
    comment: '',
    reservationReportStatus: ReservationReportStatus.Positive,
    fishingReservation: emptyFishingReservation,
    cottageReservation: emptyCottageReservation,
    boatReservation: emptyBoatReservation,
    answerCustomer: '',
    answerOwner: '',
  };

  constructor(
    private reservationReportService: ReservationReportService,
    private requestsService: RequestsService
  ) {
    requestsService.openDialogReport$.subscribe((report) => {
      if (report != undefined) {
        this.request = report;
        this.show();
      }
    });
  }

  ngOnInit() {}

  show() {
    this.reportModal.show();
  }

  close() {
    this.reportModal.hide();
  }

  approve() {
    this.reservationReportService
      .approveReservationReportRequest(this.request)
      .subscribe(() => {
        this.close();
        this.requestsService.submitedReportRequest();
      });
  }

  decline() {
    this.reservationReportService
      .declineReservationReportRequest(this.request)
      .subscribe(() => {
        this.close();
        this.requestsService.submitedReportRequest();
      });
  }
}
