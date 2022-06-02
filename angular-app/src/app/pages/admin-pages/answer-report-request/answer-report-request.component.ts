import { Component, OnInit, ViewChild } from "@angular/core";
import { ModalDirective } from "angular-bootstrap-md";
import { emptyBoatReservation } from "src/app/model/boat/boatReservation";
import { IReservationReport, ReservationReportStatus } from "src/app/model/reservationReport";
import { ReservationReportService } from "src/app/service/reservationReport.service";
import { emptyCottageReservation } from "./../../../model/cottageReservation";
import { emptyFishingReservation } from "./../../../model/fishingReservation";

@Component({
  selector: 'app-answer-report-request',
  templateUrl: './answer-report-request.component.html',
  styleUrls: ['./answer-report-request.component.css'],
})
export class AnswerReportRequestComponent implements OnInit {
  @ViewChild('frame') addModal!: ModalDirective;
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

  // Treba ce!!!!!!!!
  // reportTypeKeys!: number[];
  // reportTypes = ReservationReportStatus;
  // <select class="form-select" [(ngModel)]="request.reservationReportStatus" *ngIf="reportTypeKeys">
  //   <option *ngFor="let reportTypeKey of reportTypeKeys" [value]="reportTypeKey">{{ reportTypes[reportTypeKey] }}</option>
  // </select>
  // this.reportTypeKeys = Object.keys(this.reportTypes)
  //   .filter((f) => !isNaN(Number(f)))
  //   .map(Number);

  constructor(private reservationReportService: ReservationReportService) {
    reservationReportService.submitedDialogReport$.subscribe((report) => {
      if (report != undefined) {
        this.request = report;
        this.show();
      }
    });
  }

  ngOnInit() {}

  show() {
    this.addModal.show();
  }

  close() {
    this.addModal.hide();
  }

  approve() {
    this.reservationReportService
      .approveReservationReportRequest(this.request)
      .subscribe(() => {
        this.close();
        this.reservationReportService.submitedReportRequest();
      });
  }

  decline() {
    this.reservationReportService
      .declineReservationReportRequest(this.request)
      .subscribe(() => {
        this.close();
        this.reservationReportService.submitedReportRequest();
      });
  }
}
