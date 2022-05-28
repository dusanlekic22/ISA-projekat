import { IBusinessOwnerRegistrationRequest } from './../../../model/business-owner-registration-request';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'angular-bootstrap-md';
import { RequestsService } from 'src/app/service/requests.service';

@Component({
  selector: 'app-answer-registration-request',
  templateUrl: './answer-registration-request.component.html',
  styleUrls: ['./answer-registration-request.component.css'],
})
export class AnswerRegistrationRequestComponent implements OnInit {
  @ViewChild('frame') addModal!: ModalDirective;
  request: IBusinessOwnerRegistrationRequest = {
    id: 0,
    accepted: false,
    declineReason: '',
    registrationExplanation: '',
    userEmail: '',
  };

  constructor(private requestsService: RequestsService) {
    requestsService.openDialog$.subscribe((request) => {
      if (request != undefined) {
        this.request = request;
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

  accept() {
    this.requestsService.acceptRequest(this.request).subscribe((request) => {
      this.requestsService.submitedRequest();
      this.close();
    });
  }

  decline() {
    this.requestsService.declineRequest(this.request).subscribe((request) => {
      this.requestsService.submitedRequest();
      this.close();
    });
  }
}
