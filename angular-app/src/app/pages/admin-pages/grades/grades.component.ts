import { Component, OnInit } from '@angular/core';
import { IGrade } from 'src/app/model/grade';
import { RequestStatus } from 'src/app/model/requestStatus';
import { GradeService } from 'src/app/service/grade.service';
import { RequestsService } from 'src/app/service/requests.service';

@Component({
  selector: 'app-grades',
  templateUrl: './grades.component.html',
  styleUrls: ['./grades.component.css']
})
export class GradesComponent implements OnInit {
  RequestStatus = RequestStatus;
  requests: IGrade[] = [];

  constructor(
    private gradeService: GradeService,
    private requestsService: RequestsService
  ) {
    this.requestsService.submitedDialogGrade$.subscribe(() => {
      this.getRequests();
    });
  }

  ngOnInit(): void {
    this.getRequests();
  }

  getRequests() {
    this.gradeService
      .getAllGrades()
      .subscribe((request: IGrade[]) => {
        this.requests = request;
      });
  }

  getStatus(request: IGrade): string {
    if (request.isAccepted == RequestStatus.Waiting) return 'Waitting';
    else if (request.isAccepted == RequestStatus.Accepted) return 'Accepted';
    return 'Declined';
  }

  answer(report: IGrade) {
    this.requestsService.openDialogGrade(report);
  }
}
