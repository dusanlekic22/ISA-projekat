import { Component, OnInit } from "@angular/core";
import { IFishingReservationReport, ReservationReportStatus } from "src/app/model/fishingReservationReport";
import { FishingReservationReportService } from "src/app/service/fishing-reservation-report.service";

@Component({
  selector: 'app-report-requests',
  templateUrl: './report-requests.component.html',
  styleUrls: ['./report-requests.component.css'],
})
export class ReportRequestsComponent implements OnInit {
  requests: IFishingReservationReport[] = [];

  constructor(private reportReservationService: FishingReservationReportService) {
    this.reportReservationService.submitedDialogReport$.subscribe(() => {
      this.getRequests();
    });
  }

  ngOnInit(): void {
    this.getRequests();
  }

  getRequests() {
    this.reportReservationService
      .getAllFishingReservationReports()
      .subscribe((request: IFishingReservationReport[]) => {
        this.requests.push(...request);
      });
  }

  getStatus(request: IFishingReservationReport): string {
    if (request.userPenalized == null) return 'Waitting';
    else if (request.userPenalized) return 'Accepted';
    return 'Declined';
  }

  getReportStatus(request: IFishingReservationReport): string {
    if (request.reservationReportStatus == ReservationReportStatus.Negative)
      return 'Negative';
    else if (
      request.reservationReportStatus == ReservationReportStatus.Positive
    )
      return 'Positive';
    return "Didn't show up";
  }

  answer(report: IFishingReservationReport) {
    this.reportReservationService.openDialogReport(report);
  }
}
