import {
  Component,
  OnInit,
} from '@angular/core';
import { RequestsService } from 'src/app/service/requests.service';
import { IBusinessOwnerRegistrationRequest } from 'src/app/model/businessOwnerRegistrationRequest';

@Component({
  selector: 'app-registration-requests',
  templateUrl: './registration-requests.component.html',
  styleUrls: ['./registration-requests.component.css'],
})
export class RegistrationRequestsComponent implements OnInit {
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
    if (request.accepted == null) return 'Waitting';
    else if (request.accepted) return 'Accepted';
    return 'Declined';
  }

  answer(requset: IBusinessOwnerRegistrationRequest) {
    this.requestsService.openDialogRegistration(requset);
  }
}
