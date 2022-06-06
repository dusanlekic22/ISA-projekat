import { IUserDeletionRequest } from './../../../model/userDeletionRequest';
import { Component, OnInit } from '@angular/core';
import { RequestsService } from 'src/app/service/requests.service';
import { RequestStatus } from 'src/app/model/requestStatus';

@Component({
  selector: 'app-account-deletion-requests',
  templateUrl: './account-deletion-requests.component.html',
  styleUrls: ['./account-deletion-requests.component.css']
})
export class AccountDeletionRequestsComponent implements OnInit {
  RequestStatus = RequestStatus;
  requests!: IUserDeletionRequest[];

  constructor(private requestsService: RequestsService) {
    this.requestsService.submitedDeletionRequest$.subscribe(() => {
      this.getRequests();
    });
  }

  ngOnInit() {
    this.getRequests();
  }

  getRequests() {
    this.requestsService
      .getUserDeletionRequests()
      .subscribe((deletionRequests) => {
        this.requests = deletionRequests;
      });
  }

  getStatus(request: IUserDeletionRequest): string {
    if (request.accepted == RequestStatus.Waiting) return 'Waitting';
    else if (request.accepted == RequestStatus.Accepted) return 'Accepted';
    return 'Declined';
  }

  answer(requset: IUserDeletionRequest) {
    this.requestsService.openDialogDeletion(requset);
  }
}
