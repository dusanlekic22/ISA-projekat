import {
  Component,
  OnInit,
} from '@angular/core';
import { RequestsService } from 'src/app/service/requests.service';
import { IBusinessOwnerRegistrationRequest } from 'src/app/model/businessOwnerRegistrationRequest';
import { RequestStatus } from 'src/app/model/requestStatus';

@Component({
  selector: 'app-registration-requests',
  templateUrl: './registration-requests.component.html',
  styleUrls: ['./registration-requests.component.css'],
})
export class RegistrationRequestsComponent implements OnInit {
  RequestStatus = RequestStatus;
  requests!: IBusinessOwnerRegistrationRequest[];

  constructor(private requestsService: RequestsService) {
    this.requestsService.submitedRegistrationRequest$.subscribe(() => {
      this.getRequests();
    });
  }

  ngOnInit() {
    this.getRequests();
  }

  getRequests() {
    this.requestsService
      .getBusinessOwnerRegistrationRequests()
      .subscribe((registrationRequests) => {
        this.requests = registrationRequests;
      });
  }

  getStatus(request: IBusinessOwnerRegistrationRequest): string {
    if (request.accepted == RequestStatus.Waiting) return 'Waitting';
    else if (request.accepted == RequestStatus.Accepted) return 'Accepted';
    return 'Declined';
  }

  answer(requset: IBusinessOwnerRegistrationRequest) {
    this.requestsService.openDialogRegistration(requset);
  }
}
