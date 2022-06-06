import { ComplaintService } from './../../../service/complaint.service';
import { IComplaint, emptyComplaint } from 'src/app/model/complaint';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'angular-bootstrap-md';
import { RequestsService } from 'src/app/service/requests.service';

@Component({
  selector: 'app-answer-complaint',
  templateUrl: './answer-complaint.component.html',
  styleUrls: ['./answer-complaint.component.css'],
})
export class AnswerComplaintComponent implements OnInit {
  @ViewChild('complaint') complaintModal!: ModalDirective;
  request: IComplaint = emptyComplaint;

  constructor(
    private complaintService: ComplaintService,
    private requestsService: RequestsService
  ) {
    requestsService.openDialogComplaint$.subscribe((report) => {
      if (report != undefined) {
        this.request = report;
        this.show();
      }
    });
  }

  ngOnInit(): void {}

  show() {
    this.complaintModal.show();
  }

  close() {
    this.complaintModal.hide();
  }

  getOwner(request: IComplaint) {
    return "";
  }

  getCustomer(request: IComplaint) {
    return "";
  }

  answer() {
    this.complaintService.answer(this.request).subscribe(() => {
      this.close();
      this.requestsService.submitedComplaintRequest();
    });
  }
}
