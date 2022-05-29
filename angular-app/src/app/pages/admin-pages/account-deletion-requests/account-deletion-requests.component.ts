import { IUserDeletionRequest } from './../../../model/userDeletionRequest';
import { Component, OnInit } from '@angular/core';
import { RequestsService } from 'src/app/service/requests.service';

@Component({
  selector: 'app-account-deletion-requests',
  templateUrl: './account-deletion-requests.component.html',
  styleUrls: ['./account-deletion-requests.component.css']
})
export class AccountDeletionRequestsComponent implements OnInit {
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
    if (request.accepted == null) return 'Waitting';
    else if (request.accepted) return 'Accepted';
    return 'Declined';
  }

  answer(requset: IUserDeletionRequest) {
    this.requestsService.openDialogDeletion(requset);
  }
}
