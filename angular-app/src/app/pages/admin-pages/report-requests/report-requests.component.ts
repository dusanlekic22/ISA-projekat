import { Component, OnInit } from "@angular/core";
import { IReservationReport, ReservationReportStatus } from "src/app/model/reservationReport";
import { ReservationReportService } from "src/app/service/reservationReport.service";

@Component({
  selector: 'app-report-requests',
  templateUrl: './report-requests.component.html',
  styleUrls: ['./report-requests.component.css'],
})
export class ReportRequestsComponent implements OnInit {
  requests: IReservationReport[] = [];

  constructor(private reportReservationService: ReservationReportService) {
    this.reportReservationService.submitedDialogReport$.subscribe(() => {
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
    if (request.reservationReportStatus == ReservationReportStatus.Negative)
      return 'Negative';
    else if (
      request.reservationReportStatus == ReservationReportStatus.Positive
    )
      return 'Positive';
    return "Didn't show up";
  }

  answer(report: IReservationReport) {
    this.reportReservationService.openDialogReport(report);
  }
}
