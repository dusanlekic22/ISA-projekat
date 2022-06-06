import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'angular-bootstrap-md';
import { IBusinessOwnerRegistrationRequest } from 'src/app/model/businessOwnerRegistrationRequest';
import { RequestStatus } from 'src/app/model/requestStatus';
import { RequestsService } from 'src/app/service/requests.service';

@Component({
  selector: 'app-answer-registration-request',
  templateUrl: './answer-registration-request.component.html',
  styleUrls: ['./answer-registration-request.component.css'],
})
export class AnswerRegistrationRequestComponent implements OnInit {
  @ViewChild('reg') addModal!: ModalDirective;
  request: IBusinessOwnerRegistrationRequest = {
    id: 0,
    accepted: RequestStatus.Waiting,
    declineReason: '',
    registrationExplanation: '',
    userEmail: '',
  };

  constructor(private requestsService: RequestsService) {
    requestsService.openDialogRegistration$.subscribe((request) => {
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
    this.requestsService.acceptRegistrationRequest(this.request).subscribe((request) => {
      this.requestsService.submitedRegistrationRequest();
      this.close();
    });
  }

  decline() {
    this.requestsService.declineRegistrationRequest(this.request).subscribe((request) => {
      this.requestsService.submitedRegistrationRequest();
      this.close();
    });
  }
}
