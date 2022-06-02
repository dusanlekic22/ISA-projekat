import { Component, OnInit } from '@angular/core';
import {
  IReservationReport,
  ReservationReportStatus,
} from 'src/app/model/reservationReport';
import { RequestsService } from 'src/app/service/requests.service';
import { ReservationReportService } from 'src/app/service/reservationReport.service';

@Component({
  selector: 'app-report-requests',
  templateUrl: './report-requests.component.html',
  styleUrls: ['./report-requests.component.css'],
})
export class ReportRequestsComponent implements OnInit {
  requests: IReservationReport[] = [];

  constructor(
    private reportReservationService: ReservationReportService,
    private requestsService: RequestsService
  ) {
    this.requestsService.submitedDialogReport$.subscribe(() => {
      this.getRequests();
    });
  }

  ngOnInit(): void {
    this.getRequests();
  }

  getRequests() {
    this.reportReservationService
      .getAllReservationReports()
      .subscribe((request: IReservationReport[]) => {
        this.requests = request;
      });
  }

  getStatus(request: IReservationReport): string {
    if (request.userPenalized == null) return 'Waitting';
    else if (request.userPenalized) return 'Accepted';
    return 'Declined';
  }

  getReportStatus(request: IReservationReport): string {
    if (String(request.reservationReportStatus) == 'Negative')
      return 'Negative';
    else if (String(request.reservationReportStatus) == 'Positive')
      return 'Positive';
    return "Didn't show up";
  }

  answer(report: IReservationReport) {
    this.requestsService.openDialogReport(report);
  }
}
