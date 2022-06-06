import { Component, OnInit, Input } from "@angular/core";
import { RequestStatus } from "src/app/model/requestStatus";
import { IReservationReport, ReservationReportStatus } from "src/app/model/reservationReport";
import { ReservationReportService } from "src/app/service/reservationReport.service";

@Component({
  selector: 'app-owner-reservation-report',
  templateUrl: './owner-reservation-report.component.html',
  styleUrls: ['./owner-reservation-report.component.css'],
})
export class OwnerReservationReportComponent implements OnInit {
  @Input() reservation: any;
  report: IReservationReport = {
    id: 0,
    userPenalized: RequestStatus.Waiting,
    comment: '',
    reservationReportStatus: ReservationReportStatus.Positive,
    fishingReservation: null,
    cottageReservation: null,
    boatReservation: null,
    answerOwner: '',
    answerCustomer: '',
  };
  reportTypeKeys!: number[];
  reportTypes = ReservationReportStatus;

  constructor(private reservationReportService: ReservationReportService) {}

  ngOnInit(): void {
    this.reportTypeKeys = Object.keys(this.reportTypes)
      .filter((f) => !isNaN(Number(f)))
      .map(Number);
  }

  submit(): void {
    if (this.reservation.hasOwnProperty('fishingCourse')) {
      this.report.fishingReservation = this.reservation;
    } else if (this.reservation.hasOwnProperty('cottage')) {
      this.report.cottageReservation = this.reservation;
    } else if (this.reservation.hasOwnProperty('boat')) {
      this.report.boatReservation = this.reservation;
    }
    this.reservationReportService
      .createReservationReportRequest(this.report)
      .subscribe();
  }
}
