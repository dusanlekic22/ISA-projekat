import { IUserDeletionRequest } from './../../../model/userDeletionRequest';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDirective } from 'angular-bootstrap-md';
import { RequestsService } from 'src/app/service/requests.service';

@Component({
  selector: 'app-answer-deletion-request',
  templateUrl: './answer-deletion-request.component.html',
  styleUrls: ['./answer-deletion-request.component.css'],
})
export class AnswerDeletionRequestComponent implements OnInit {
  @ViewChild('frame') addModal!: ModalDirective;
  request: IUserDeletionRequest = {
    id: 0,
    accepted: false,
    deletionExplanation: '',
    userEmail: '',
    answer: '',
  };

  constructor(private requestsService: RequestsService) {
    requestsService.openDialogDeletion$.subscribe((request) => {
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
    this.requestsService.acceptDeletionRequest(this.request).subscribe((request) => {
      this.requestsService.submitedDeletionRequest();
      this.close();
    });
  }

  decline() {
    this.requestsService.declineDeletionRequest(this.request).subscribe((request) => {
      this.requestsService.submitedDeletionRequest();
      this.close();
    });
  }
}
