import { emptyGrade } from './../../../model/grade';
import { emptyCustomer } from 'src/app/model/customer';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'angular-bootstrap-md';
import { IGrade } from 'src/app/model/grade';
import { GradeService } from 'src/app/service/grade.service';
import { RequestsService } from 'src/app/service/requests.service';

@Component({
  selector: 'app-answer-grade',
  templateUrl: './answer-grade.component.html',
  styleUrls: ['./answer-grade.component.css'],
})
export class AnswerGradeComponent implements OnInit {
  @ViewChild('grade') gradeModal!: ModalDirective;
  request: IGrade = emptyGrade;

  constructor(
    private gradeService: GradeService,
    private requestsService: RequestsService
  ) {
    requestsService.openDialogGrade$.subscribe((grade) => {
      if (grade != undefined) {
        this.request = grade;
        this.show();
      }
    });
  }

  ngOnInit() {}

  show() {
    this.gradeModal.show();
  }

  close() {
    this.gradeModal.hide();
  }

  approve() {
    this.gradeService.approveGradeRequest(this.request).subscribe(() => {
      this.close();
      this.requestsService.submitedGradeRequest();
    });
  }

  decline() {
    this.gradeService.declineGradeRequest(this.request).subscribe(() => {
      this.close();
      this.requestsService.submitedGradeRequest();
    });
  }
}
